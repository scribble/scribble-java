/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.main;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.model.endpoint.AutParser;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.global.SGraph;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;
import org.scribble.visit.context.EGraphBuilder;
import org.scribble.visit.context.Projector;

// Global "static" context information for a Job -- single instance per Job, should not be shared between Jobs
// Mutable: projections, graphs, etc are added mutably later -- replaceModule also mutable setter -- "users" get this from the Job and expect to setter mutate "in place"
public class JobContext
{
	private final Job job;

	public final ModuleName main;
	
	// ModuleName keys are full module names -- currently the modules read from file, distinguished from the generated projection modules
	private final Map<ModuleName, Module> parsed;// = new HashMap<>();
	
	// LProtocolName is the full local protocol name (module name is the prefix)
	private final Map<LProtocolName, Module> projected = new HashMap<>();

	private final Map<LProtocolName, EGraph> fairEGraphs = new HashMap<>();
	private final Map<GProtocolName, SGraph> fairSGraphs = new HashMap<>();
	
	private final Map<LProtocolName, EGraph> unfairEGraphs = new HashMap<>();
	private final Map<GProtocolName, SGraph> unfairSGraphs = new HashMap<>();
	
	private final Map<LProtocolName, EGraph> minimisedEGraphs = new HashMap<>();  // Toolchain currently depends on single instance of each graph (state id equality), e.g. cannot re-build or re-minimise, would not be the same graph instance
			// FIXME: currently only minimising "fair" graph, need to consider minimisation orthogonally to fairness -- NO: minimising (of fair) is for API gen only, unfair-transform does not use minimisation (regardless of user flag) for WF
	
	protected JobContext(Job job, Map<ModuleName, Module> parsed, ModuleName main)
	{
		this.job = job;

		this.parsed = new HashMap<ModuleName, Module>(parsed);
		this.main = main;
	}

	public Module getMainModule()
	{
		return getModule(this.main);
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
	
	public Module getProjection(GProtocolName fullname, Role role) throws ScribbleException
	{
		Module proj = this.projected.get(Projector.projectFullProtocolName(fullname, role));
		if (proj == null)
		{
			throw new ScribbleException("Projection not found: " + fullname + ", " + role);  // E.g. disamb/enabling error before projection passes (e.g. CommandLine -fsm arg)
				// FIXME: should not occur any more
		}
		return proj;
	}
	
	protected void addEGraph(LProtocolName fullname, EGraph graph)
	{
		this.fairEGraphs.put(fullname, graph);
	}
	
	public EGraph getEGraph(GProtocolName fullname, Role role) throws ScribbleException
	{
		LProtocolName fulllpn = Projector.projectFullProtocolName(fullname, role);
		// Moved form LProtocolDecl
		EGraph graph = this.fairEGraphs.get(fulllpn);
		if (graph == null)
		{
			Module proj = getProjection(fullname, role);  // Projected module contains a single protocol
			EGraphBuilder builder = new EGraphBuilder(this.job);  // Obtains an EGraphBuilderUtil from Job
			proj.accept(builder);
			graph = builder.util.finalise();
			addEGraph(fulllpn, graph);
		}
		return graph;
	}
	
	protected void addUnfairEGraph(LProtocolName fullname, EGraph graph)
	{
		this.unfairEGraphs.put(fullname, graph);
	}
	
	public EGraph getUnfairEGraph(GProtocolName fullname, Role role) throws ScribbleException
	{
		LProtocolName fulllpn = Projector.projectFullProtocolName(fullname, role);

		EGraph unfair = this.unfairEGraphs.get(fulllpn);
		if (unfair == null)
		{
			unfair = getEGraph(fullname, role).init.unfairTransform().toGraph();
			addUnfairEGraph(fulllpn, unfair);
		}
		return unfair;
	}

	protected void addSGraph(GProtocolName fullname, SGraph graph)
	{
		this.fairSGraphs.put(fullname, graph);
	}
	
	public SGraph getSGraph(GProtocolName fullname) throws ScribbleException
	{
		SGraph graph = this.fairSGraphs.get(fullname);
		if (graph == null)
		{
			GProtocolDecl gpd = (GProtocolDecl) getModule(fullname.getPrefix()).getProtocolDecl(fullname.getSimpleName());
			Map<Role, EGraph> egraphs = getEGraphsForSGraphBuilding(fullname, gpd, true);
			boolean explicit = gpd.modifiers.contains(GProtocolDecl.Modifiers.EXPLICIT);
			//graph = SGraph.buildSGraph(egraphs, explicit, this.job, fullname);
			graph = this.job.buildSGraph(fullname, egraphs, explicit);
			addSGraph(fullname, graph);
		}
		return graph;
	}

	private Map<Role, EGraph> getEGraphsForSGraphBuilding(GProtocolName fullname, GProtocolDecl gpd, boolean fair) throws ScribbleException
	{
		Map<Role, EGraph> egraphs = new HashMap<>();
		for (Role self : gpd.header.roledecls.getRoles())
		{
			egraphs.put(self, fair ? getEGraph(fullname, self) : getUnfairEGraph(fullname, self));
		}
		return egraphs;
	}

	protected void addUnfairSGraph(GProtocolName fullname, SGraph graph)
	{
		this.unfairSGraphs.put(fullname, graph);
	}

	public SGraph getUnfairSGraph(GProtocolName fullname) throws ScribbleException
	{
		SGraph graph = this.unfairSGraphs.get(fullname);
		if (graph == null)
		{
			GProtocolDecl gpd = (GProtocolDecl) getModule(fullname.getPrefix()).getProtocolDecl(fullname.getSimpleName());
			Map<Role, EGraph> egraphs = getEGraphsForSGraphBuilding(fullname, gpd, false);
			boolean explicit = gpd.modifiers.contains(GProtocolDecl.Modifiers.EXPLICIT);
			//graph = SGraph.buildSGraph(this.job, fullname, this.job.createInitialSConfig(job, egraphs, explicit));
			graph = this.job.buildSGraph(fullname, egraphs, explicit);
			addUnfairSGraph(fullname, graph);
		}
		return graph;
	}
	
	protected void addMinimisedEGraph(LProtocolName fullname, EGraph graph)
	{
		this.minimisedEGraphs.put(fullname, graph);
	}
	
	public EGraph getMinimisedEGraph(GProtocolName fullname, Role role) throws ScribbleException
	{
		LProtocolName fulllpn = Projector.projectFullProtocolName(fullname, role);

		EGraph minimised = this.minimisedEGraphs.get(fulllpn);
		if (minimised == null)
		{
			String aut = runAut(getEGraph(fullname, role).init.toAut(), fulllpn + ".aut");
			minimised = new AutParser().parse(this.job.ef, aut);
			addMinimisedEGraph(fulllpn, minimised);
		}
		return minimised;
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
