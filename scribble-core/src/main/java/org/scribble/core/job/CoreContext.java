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
package org.scribble.core.job;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.lang.local.LProtocol;
import org.scribble.core.model.endpoint.AutGraphParser;
import org.scribble.core.model.endpoint.EGraph;
import org.scribble.core.model.global.SGraph;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.LProtoName;
import org.scribble.core.type.name.ModuleName;
import org.scribble.core.type.name.Role;
import org.scribble.core.visit.global.InlinedProjector;
import org.scribble.util.ScribException;
import org.scribble.util.ScribUtil;

// Global "static" context information for a Job -- single instance per Job, should not be shared between Jobs
// Mutable: projections, graphs, etc are added mutably later -- replaceModule also mutable setter -- "users" get this from the Job and expect to setter mutate "in place"
public class CoreContext
{
	private final Core core;

	// Keys are full names
	// CHECKME: not currently used by core? -- core fully independent of modules, etc., because full disamb already done? (by imed translation)
	//private final Map<ModuleName, ModuleContext> modcs;

	// "Directly" translated global protos, i.e., separate proto decls without any inlining/unfolding/etc
	// Protos retain original decl role list (and args)
  // Keys are full names (though GProtocol already includes full name)
	private final Map<GProtoName, GProtocol> imeds;

	// Protos have pruned role decls -- CHECKME: prune args?
  // Keys are full names (though GProtocol already includes full name)
	private final Map<GProtoName, GProtocol> inlined = new HashMap<>();

  // Projected from inlined; keys are full names
	private final Map<LProtoName, LProtocol> iprojs = new HashMap<>();  // CHECKME: rename projis?
	
	// Projected from intermediates
	// LProtocolName is the full local protocol name (module name is the prefix)
	// LProtocolName key is LProtocol value fullname (i.e., redundant)
	private final Map<LProtoName, LProtocol> projs = new HashMap<>();

	// Built from projected inlined
	private final Map<LProtoName, EGraph> fEGraphs = new HashMap<>();
	private final Map<LProtoName, EGraph> uEGraphs = new HashMap<>();
	private final Map<LProtoName, EGraph> mEGraphs = new HashMap<>();  
			// Toolchain currently depends on single instance of each graph (state id equality), e.g. cannot re-build or re-minimise, would not be the same graph instance
			// FIXME: currently only minimising "fair" graph, need to consider minimisation orthogonally to fairness -- NO: minimising (of fair) is for API gen only, unfair-transform does not use minimisation (regardless of user flag) for WF

	private final Map<GProtoName, SGraph> fSGraphs = new HashMap<>();
	private final Map<GProtoName, SGraph> uSGraphs = new HashMap<>();
	
	protected CoreContext(Core core, //Map<ModuleName, ModuleContext> modcs,
			Set<GProtocol> imeds)
	{
		this.core = core;
		//this.modcs = Collections.unmodifiableMap(modcs);
		this.imeds = imeds.stream()
				.collect(Collectors.toMap(x -> x.fullname, x -> x));
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
		//return Collections.unmodifiableSet(this.parsed.keySet());
		return this.imeds.keySet().stream().map(x -> x.getPrefix())
				.collect(Collectors.toSet());
	}

	public Set<ModuleName> getProjectedFullModuleNames()
	{
		return this.projs.keySet().stream().map(x -> x.getPrefix())
				.collect(Collectors.toSet());
	}
	
	public GProtocol getIntermediate(GProtoName fullname)
	{
		return this.imeds.get(fullname);
	}

	public Set<GProtocol> getIntermediates()
	{
		return this.imeds.values().stream().collect(Collectors.toSet());
	}
	
	public void addInlined(GProtoName fullname, GProtocol g)
	{
		this.inlined.put(fullname, g);
	}
	
	public GProtocol getInlined(GProtoName fullname)
	{
		return this.inlined.get(fullname);
	}

	public Set<GProtocol> getInlineds()
	{
		return this.inlined.values().stream().collect(Collectors.toSet());
	}
	
  // Projected from inlined
	public void addProjectedInlined(LProtoName fullname, LProtocol l)
	{
		this.iprojs.put(fullname, l);
	}
	
	public LProtocol getProjectedInlined(GProtoName fullname, Role self)
	{
		LProtoName p = InlinedProjector.getFullProjectionName(fullname, self);
		return getProjectedInlined(p);
	}

	public LProtocol getProjectedInlined(LProtoName fullname)
	{
		return this.iprojs.get(fullname);
	}
	
	public Map<LProtoName, LProtocol> getProjectedInlineds()
	{
		return Collections.unmodifiableMap(this.iprojs);
	}
	
	// Projected from intermediate
	public void addProjection(LProtocol p)
	{
		/*LProtocolName lpn = (LProtocolName) mod.getProtoDeclChildren().get(0)
				.getFullMemberName(mod);
		this.projected.put(lpn, mod);*/
		this.projs.put(p.fullname, p);
	}
	
	public //Module 
			LProtocol getProjection(GProtoName fullname, Role role)
			throws ScribException
	{
		return getProjection(InlinedProjector.getFullProjectionName(fullname, role));
	}

	public //Module 
			LProtocol getProjection(LProtoName fullname)
			//throws ScribbleException
	{
		/*Module proj = this.projected.get(fullname);
		if (proj == null)*/
		/*if (!this.projected.containsKey(fullname))
		{
			throw new ScribbleException(
					"Projection not found: " + fullname);
					// E.g. disamb/enabling error before projection passes (e.g. CommandLine -fsm arg)
					//CHECKME: should not occur any more?
		}*/
		return this.projs.get(fullname);
	}
	
	protected void addEGraph(LProtoName fullname, EGraph graph)
	{
		this.fEGraphs.put(fullname, graph);
	}
	
	// N.B. graphs built from inlined (not unfolded)
	public EGraph getEGraph(GProtoName fullname, Role role)
			throws ScribException
	{
		LProtoName fulllpn = InlinedProjector.getFullProjectionName(fullname,
				role);
		// Moved form LProtocolDecl
		EGraph graph = this.fEGraphs.get(fulllpn);
		if (graph == null)
		{
			/*Module proj = getProjection(fullname, role);  // Projected module contains a single protocol
			EGraphBuilder builder = new EGraphBuilder(this.job);  // Obtains an EGraphBuilderUtil from Job
			proj.accept(builder);
			graph = builder.util.finalise();
			addEGraph(fulllpn, graph);*/
			throw new RuntimeException("Shouldn't get in here: ");
		}
		return graph;
	}
	
	protected void addUnfairEGraph(LProtoName fullname, EGraph graph)
	{
		this.uEGraphs.put(fullname, graph);
	}
	
	public EGraph getUnfairEGraph(GProtoName fullname, Role role)
			throws ScribException
	{
		LProtoName projFullname = InlinedProjector.getFullProjectionName(fullname,
				role);

		EGraph unfair = this.uEGraphs.get(projFullname);
		if (unfair == null)
		{
			unfair = getEGraph(fullname, role).init
					.unfairTransform(this.core.config.mf).toGraph();
			addUnfairEGraph(projFullname, unfair);
		}

		//System.out.println("\nunfair " + projFullname + ":\n" + unfair.toDot());  // TODO: verbose printing for unfair EGraphs
		
		return unfair;
	}

	protected void addSGraph(GProtoName fullname, SGraph graph)
	{
		this.fSGraphs.put(fullname, graph);
	}
	
	public SGraph getSGraph(GProtoName fullname) throws ScribException
	{
		SGraph graph = this.fSGraphs.get(fullname);
		if (graph == null)
		{
			/*GProtocolDecl gpd = (GProtocolDecl) getModule(fullname.getPrefix())
					.getProtocolDeclChild(fullname.getSimpleName());*/
			Map<Role, EGraph> egraphs = 
					getEGraphsForSGraphBuilding(fullname, //gpd,
							true);
			boolean explicit = //gpd.isExplicit();
					this.imeds.get(fullname).isExplicit();
					//graph = SGraph.buildSGraph(egraphs, explicit, this.job, fullname);
			graph = this.core.buildSGraph(fullname, egraphs, explicit);
			addSGraph(fullname, graph);
		}
		return graph;
	}

	private Map<Role, EGraph> getEGraphsForSGraphBuilding(GProtoName fullname,
			//GProtocolDecl gpd, 
			boolean fair) throws ScribException
	{
		Map<Role, EGraph> egraphs = new HashMap<>();
		//for (Role self : gpd.getHeaderChild().getRoleDeclListChild().getRoles())
		for (Role self : this.imeds.get(fullname).roles)
		{
			egraphs.put(self, fair 
					? getEGraph(fullname, self) 
					: getUnfairEGraph(fullname, self));
		}
		return egraphs;
	}

	protected void addUnfairSGraph(GProtoName fullname, SGraph graph)
	{
		this.uSGraphs.put(fullname, graph);
	}

	public SGraph getUnfairSGraph(GProtoName fullname) throws ScribException
	{
		SGraph graph = this.uSGraphs.get(fullname);
		if (graph == null)
		{
			/*GProtocolDecl gpd = (GProtocolDecl) getModule(fullname.getPrefix())
					.getProtocolDeclChild(fullname.getSimpleName());*/
			Map<Role, EGraph> egraphs = getEGraphsForSGraphBuilding(fullname, //gpd, 
					false);
			boolean explicit = //gpd.isExplicit();
					this.imeds.get(fullname).isExplicit();
			//graph = SGraph.buildSGraph(this.job, fullname, this.job.createInitialSConfig(job, egraphs, explicit));
			graph = this.core.buildSGraph(fullname, egraphs, explicit);
			addUnfairSGraph(fullname, graph);
		}
		return graph;
	}
	
	protected void addMinimisedEGraph(LProtoName fullname, EGraph graph)
	{
		this.mEGraphs.put(fullname, graph);
	}
	
	public EGraph getMinimisedEGraph(GProtoName fullname, Role role)
			throws ScribException
	{
		LProtoName fulllpn = InlinedProjector.getFullProjectionName(fullname,
				role);

		EGraph minimised = this.mEGraphs.get(fulllpn);
		if (minimised == null)
		{
			String aut = runAut(getEGraph(fullname, role).init.toAut(),
					fulllpn + ".aut");
			minimised = new AutGraphParser(this.core).parse(aut);
			addMinimisedEGraph(fulllpn, minimised);
		}
		return minimised;
	}
	
	/*public ModuleContext getModuleContext(ModuleName fullname)
	{
		return this.modcs.get(fullname);
	}*/

	// TODO: relocate
	// Duplicated from CommandLine.runDot
	// Minimises the FSM up to bisimulation
	// N.B. ltsconvert will typically re-number the states
	private static String runAut(String fsm, String aut) throws ScribException
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
			String[] res = ScribUtil.runProcess("ltsconvert", "-ebisim", "-iaut",
					"-oaut", tmpName);
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
