package org.scribble.visit;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.AutParser;
import org.scribble.model.local.EndpointGraph;
import org.scribble.model.wf.WFState;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;

// Global "static" context information for a Job -- single instance per Job and should never be shared
// Mutable: projections, graphs, etc are added mutably later -- replaceModule also mutable setter
public class JobContext
{
	private final Job job;

	public final ModuleName main;
	
	// ModuleName keys are full module names -- currently the modules read from file, distinguished from the generated projection modules
	private final Map<ModuleName, Module> parsed;// = new HashMap<>();
	
	// LProtocolName is the full local protocol name (module name is the prefix)
	private final Map<LProtocolName, Module> projected = new HashMap<>();

	private final Map<LProtocolName, EndpointGraph> graphs = new HashMap<>();
	//private final Map<GProtocolName, GModel> gmodels = new HashMap<>();
	private final Map<GProtocolName, WFState> gmodels = new HashMap<>();
	
	private final Map<LProtocolName, EndpointGraph> unfair = new HashMap<>();
	private final Map<LProtocolName, EndpointGraph> minimised = new HashMap<>();  // Toolchain currently depends on single instance of each graph (state id equality), e.g. cannot re-build or re-minimise, would not be the same graph instance
			// FIXME: minimised "fair" graph only, need to consider minimisation orthogonally to fairness -- NO: unfair-transform without minimisation is only for WF, minimised original only for API gen
	
	protected JobContext(Job job, Map<ModuleName, Module> parsed, ModuleName main)
	{
		this.job = job;

		this.parsed = new HashMap<ModuleName, Module>(parsed);
		this.main = main;
	}
	
	// Used by Job for pass running, includes projections (e.g. for reachability checking)
	// Safer to get module names and require user to re-fetch the module by the getter each time (after replacing), to make sure the latest is used
	public Set<ModuleName> getFullModuleNames()
	{
		Set<ModuleName> modnames = new HashSet<>();
		modnames.addAll(getParsedFullModuleNames());
		modnames.addAll(getProjectedFullModuleNames());
		return modnames;
	}

	public Set<ModuleName> getParsedFullModuleNames()
	{
		Set<ModuleName> modnames = new HashSet<>();
		modnames.addAll(this.parsed.keySet());
		return modnames;
	}

	public Set<ModuleName> getProjectedFullModuleNames()
	{
		return this.projected.keySet().stream().map((lpn) -> lpn.getPrefix()).collect(Collectors.toSet());
	}

	/*public boolean hasModule(ModuleName fullname)
	{
		return isParsedModule(fullname) || isProjectedModule(fullname);
	}*/
	
	private boolean isParsedModule(ModuleName fullname)
	{
		return this.parsed.containsKey(fullname);
	}
	
	private boolean isProjectedModule(ModuleName fullname)
	{
		//return this.projected.keySet().stream().filter((lpn) -> lpn.getPrefix().equals(fullname)).count() > 0;
		return getProjectedFullModuleNames().contains(fullname);
	}

	public Module getModule(ModuleName fullname)
	{
		if (isParsedModule(fullname))
		{
			return this.parsed.get(fullname);
		}
		else if (isProjectedModule(fullname))
		{
			return this.projected.get(
					this.projected.keySet().stream().filter((lpn) -> lpn.getPrefix().equals(fullname)).collect(Collectors.toList()).get(0));
		}
		else
		{
			throw new RuntimeException("Unknown module: " + fullname);
		}
	}

	protected void replaceModule(Module module)
	{
		ModuleName fullname = module.getFullModuleName(); 
		if (isParsedModule(fullname))
		{
			this.parsed.put(fullname, module);
		}
		else if (isProjectedModule(fullname))
		{
			addProjection(module);
		}
		else
		{
			throw new RuntimeException("Unknown module: " + fullname);
		}
	}
	
	// Make context immutable? (will need to assign updated context back to Job) -- will also need to do for Module replacing
	public void addProjections(Map<GProtocolName, Map<Role, Module>> projections)
	{
		for (GProtocolName gpn : projections.keySet())
		{
			Map<Role, Module> mods = projections.get(gpn);
			for (Role role : mods.keySet())
			{
				addProjection(mods.get(role));
			}
		}

		/*// Doesn't work for external subprotocols now that Projector doesn't record Module-specific dependencies itself
		try
		{
			ContextBuilder builder = new ContextBuilder(this.job);
			for (ProtocolName lpn : this.projections.keySet())
			{
				Module mod = this.projections.get(lpn);
				mod = (Module) mod.accept(builder);
				replaceModule(mod);
			}
		}
		catch (ScribbleException e)
		{
			throw new RuntimeException("Shouldn't get in here: " + e);
		}*/
	}

	private void addProjection(Module mod)
	{
		LProtocolName lpn = (LProtocolName) mod.getProtocolDecls().get(0).getFullMemberName(mod);
		this.projected.put(lpn, mod);
	}
	
	public Module getProjection(GProtocolName fullname, Role role)
	{
		return this.projected.get(Projector.projectFullProtocolName(fullname, role));
	}
	
	//public void addGlobalModel(GProtocolName fullname, GModel model)
	public void addGlobalModel(GProtocolName fullname, WFState model)
	{
		this.gmodels.put(fullname, model);
	}
	
	//public GModel getGlobalModel(GProtocolName fullname)
	public WFState getGlobalModel(GProtocolName fullname)
	{
		return this.gmodels.get(fullname);
	}
	
	protected void addEndpointGraph(LProtocolName fullname, EndpointGraph graph)
	{
		this.graphs.put(fullname, graph);
	}
	
	public EndpointGraph getEndpointGraph(GProtocolName fullname, Role role) throws ScribbleException
	{
		////return this.graphs.get(Projector.projectFullProtocolName(fullname, role));
		//return getEndpointGraph(Projector.projectFullProtocolName(fullname, role));

		LProtocolName fulllpn = Projector.projectFullProtocolName(fullname, role);
		// Moved form LProtocolDecl
		EndpointGraph graph = this.graphs.get(fulllpn);
		if (graph == null)
		{
			Module proj = getProjection(fullname, role);  // Projected module contains a single protocol
			EndpointGraphBuilder builder = new EndpointGraphBuilder(this.job);
			proj.accept(builder);
			graph = builder.builder.finalise();  // Projected module contains a single protocol
			addEndpointGraph(fulllpn, graph);
		}
		return graph;
	}

  // Full projected name
	protected EndpointGraph getEndpointGraph(LProtocolName fullname)
	{
		EndpointGraph graph = this.graphs.get(fullname);
		if (graph == null)
		{
			throw new RuntimeException("FIXME: " + fullname);
		}
		return graph;
	}
	
	protected void addMinimisedEndpointGraph(LProtocolName fullname, EndpointGraph graph)
	{
		this.minimised.put(fullname, graph);
	}
	
	public EndpointGraph getMinimisedEndpointGraph(GProtocolName fullname, Role role)
	{
		return getMinimisedEndpointGraph(Projector.projectFullProtocolName(fullname, role));
	}

  // Full projected name
	protected EndpointGraph getMinimisedEndpointGraph(LProtocolName fullname)
	{
		EndpointGraph minimised = this.minimised.get(fullname);
		if (minimised == null)
		{
			try
			{
				String aut = runAut(getEndpointGraph(fullname).init.toAut(), fullname + ".aut");
				minimised = new AutParser().parse(aut);
				addMinimisedEndpointGraph(fullname, minimised);
			}
			catch (ScribbleException e)
			{
				throw new RuntimeException(e);
			}
		}
		return minimised;
	}
	
	protected void addUnfairEndpointGraph(LProtocolName fullname, EndpointGraph graph)
	{
		this.unfair.put(fullname, graph);
	}
	
	public EndpointGraph getUnfairEndpointGraph(GProtocolName fullname, Role role)
	{
		return getUnfairEndpointGraph(Projector.projectFullProtocolName(fullname, role));
	}

  // Full projected name
	protected EndpointGraph getUnfairEndpointGraph(LProtocolName fullname)
	{
		EndpointGraph unfair = this.unfair.get(fullname);
		if (unfair == null)
		{
			unfair = getEndpointGraph(fullname).init.unfairTransform().toGraph();
			addUnfairEndpointGraph(fullname, unfair);
		}
		return unfair;
	}

	public Module getMainModule()
	{
		return getModule(this.main);
	}

	// Duplicated from CommandLine.runDot
	// Minimises the FSM up to bisimulation
	// N.B. ltsconvert will typically re-number the states
	private static String runAut(String fsm, String aut) throws ScribbleException
	{
		String tmpName = aut + ".tmp";
		File tmp = new File(tmpName);
		if (tmp.exists())  // Factor out with CommandLine.runDot (file exists check)
		{
			throw new RuntimeException("Cannot overwrite: " + tmpName);
		}
		try
		{
			ScribUtil.writeToFile(tmpName, fsm);
			String[] res = ScribUtil.runProcess("ltsconvert", "-ebisim", "-iaut", "-oaut", tmpName);
			if (!res[1].isEmpty())
			{
				throw new RuntimeException(res[1]);
			}
			return res[0];
		}
		finally
		{
			tmp.delete();
		}
	}
}
