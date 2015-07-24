package org.scribble.visit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.model.local.EndpointGraph;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.Role;

// Global "static" context information for a Job
// Mutable: projections are added mutably later -- replaceModule also mutable setter
public class JobContext
{
	public final ModuleName main;
	
	// ModuleName keys are full module names -- currently the modules read from file, distinguished from the generated projection modules
	private final Map<ModuleName, Module> parsed;// = new HashMap<>();
	
	// LProtocolName is the full local protocol name (module name is the prefix)
	private final Map<LProtocolName, Module> projected = new HashMap<>();

	private final Map<LProtocolName, EndpointGraph> graphs = new HashMap<>();
	
	public JobContext(Map<ModuleName, Module> parsed, ModuleName main)
	{
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
	
	public void addEndpointGraph(LProtocolName lpn, EndpointGraph graph)
	{
		this.graphs.put(lpn, graph);
	}
	
	public EndpointGraph getEndpointGraph(GProtocolName fullname, Role role)
	{
		return this.graphs.get(Projector.projectFullProtocolName(fullname, role));
	}
	
	public Module getMainModule()
	{
		return getModule(this.main);
	}
}
