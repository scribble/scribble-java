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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.lang.local.LProjection;
import org.scribble.core.model.endpoint.AutGraphParser;
import org.scribble.core.model.endpoint.EGraph;
import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.global.SGraph;
import org.scribble.core.model.global.SGraphBuilder;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.LProtoName;
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.Role;
import org.scribble.core.visit.global.GTypeInliner;
import org.scribble.core.visit.global.GTypeUnfolder;
import org.scribble.core.visit.global.InlinedProjector;
import org.scribble.util.Pair;
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
  // Keys are full names (though GProtocol already includes full name) -- parameterised name mainly tracks back to Do.proto not being "specialised"
	private final Map<ProtoName<Global>, GProtocol> imeds;

	// N.B. protos have pruned role decls -- CHECKME: prune args?
	// Mods are preserved
  // Keys are full names
	private final Map<ProtoName<Global>, GProtocol> inlined = new HashMap<>();
	private final Map<ProtoName<Global>, GProtocol> unfs = new HashMap<>();

	// CHECKME: rename projis?
	private final Map<ProtoName<Local>, LProjection> iprojs = new HashMap<>();  // Projected from inlined; keys are full names
	
	// CHECKME: refactor to Job ?
	// Projected from intermediates
	// N.B. unlike iprojs, initial projections not pruned/fixed at all -- do-arg pruning, do-pruning, ext-choice-subj fixing all done incrementally
	// LProtocolName is the full local protocol name (module name is the prefix)  // LProtocolName key is LProtocol value fullname (i.e., redundant)
	private final Map<ProtoName<Local>, LProjection> projs = new HashMap<>();
			// FIXME: choice-subj fixing, do-pruning -- factor out to Job and do there via AstVisitor? -- make testing compare the two sides 
			// TODO: refactor projection, choice-subj fixing, do-pruning, do-arg fixing, etc. fully to Job (and drop this.projs from here)

	// Built from projected inlined
	private final Map<ProtoName<Local>, EGraph> fEGraphs = new HashMap<>();
	private final Map<ProtoName<Local>, EGraph> uEGraphs = new HashMap<>();
	private final Map<ProtoName<Local>, EGraph> mEGraphs = new HashMap<>();  
			// Toolchain currently depends on single instance of each graph (state id equality), e.g. cannot re-build or re-minimise, would not be the same graph instance
			// FIXME: currently only minimising "fair" graph, need to consider minimisation orthogonally to fairness -- NO: minimising (of fair) is for API gen only, unfair-transform does not use minimisation (regardless of user flag) for WF

	private final Map<ProtoName<Global>, SGraph> fSGraphs = new HashMap<>();
	private final Map<ProtoName<Global>, SGraph> uSGraphs = new HashMap<>();
	
	protected CoreContext(Core core, //Map<ModuleName, ModuleContext> modcs,
			Set<GProtocol> imeds)
	{
		this.core = core;
		//this.modcs = Collections.unmodifiableMap(modcs);
		this.imeds = imeds.stream()
				.collect(Collectors.toMap(x -> x.fullname, x -> x));
	}
	
	// Used by Core for pass running
	// Safer to return names and require user to get the target value by name, to make sure the value is created
	public Set<ProtoName<Global>> getParsedFullnames()
	{
		return this.imeds.keySet().stream().collect(Collectors.toSet());
	}

	/*// Pre: getProjectedInlined done for all... -- or directly derive these from the global names?
	// N.B. uses this.*i*projs (more "important" than this.projs?)
	public Set<LProtoName> getProjectedFullnames()
	{
		return this.iprojs.keySet().stream().collect(Collectors.toSet());
	}*/
	
	public GProtocol getIntermediate(ProtoName<Global> fullname)
	{
		return this.imeds.get(fullname);
	}

	/*// OK for prevoius CoreContext with more basic getters/setters -- now values created on-demand by individual getters, so a collective getter is less suitable
	public Set<GProtocol> getIntermediates()
	{
		return this.imeds.values().stream().collect(Collectors.toSet());
	}*/
	
	public GProtocol getInlined(ProtoName<Global> fullname)
	{
		GProtocol inlined = this.inlined.get(fullname);
		if (inlined == null)
		{
			GTypeInliner v = this.core.config.vf.global.GTypeInliner(this.core);  // Factor out?
			inlined = this.imeds.get(fullname).getInlined(v);  // Protocol.getInlined does pruneRecs
			addInlined(fullname, inlined);
		}
		return inlined;
	}
	
	protected void addInlined(ProtoName<Global> fullname, GProtocol g)
	{
		this.inlined.put(fullname, g);
	}

	/*// OK for prevoius CoreContext with more basic getters/setters -- now values created on-demand by individual getters, so a collective getter is less suitable
	public Set<GProtocol> getInlineds()
	{
		return this.inlined.values().stream().collect(Collectors.toSet());
	}*/
	
	public GProtocol getOnceUnfolded(ProtoName<Global> fullname)
	{
		GProtocol unf = this.unfs.get(fullname);
		if (unf == null)
		{
			GTypeUnfolder v = this.core.config.vf.global.GTypeUnfolder(this.core);
			unf = this.inlined.get(fullname).unfoldAllOnce(v);  // Protocol.getInlined does pruneRecs
			addOnceUnfolded(fullname, unf);
		}
		return unf;
	}
	
	protected void addOnceUnfolded(ProtoName<Global> fullname, GProtocol g)
	{
		this.unfs.put(fullname, g);
	}
	
  // Projected from inlined
	public LProjection getProjectedInlined(ProtoName<Global> fullname, Role self)
	{
		LProtoName projFullname = InlinedProjector.getFullProjectionName(fullname,
				self);
		LProjection iproj = this.iprojs.get(projFullname);
		if (iproj == null)
		{
			iproj = getInlined(fullname).projectInlined(this.core, self);
			addProjectedInlined(iproj);
		}
		return iproj;
	}

	/*public LProjection getProjectedInlined(LProtoName fullname)
	{
		LProjection iproj = this.iprojs.get(fullname);
		if (iproj == null)
		{
			// FIXME: need global inlined to build (reverse derive from fullname?)
			throw new RuntimeException("[TODO]: " + fullname);
		}
		return iproj;
	}*/
	
	/*// OK for prevoius CoreContext with more basic getters/setters -- now values created on-demand by individual getters, so a collective getter is less suitable
	public Map<LProtoName, LProtocol> getProjectedInlineds()
	{
		return Collections.unmodifiableMap(this.iprojs);
	}
	//*/
	
	protected void addProjectedInlined(LProjection iproj)
	{
		this.iprojs.put(iproj.fullname, iproj);
	}

	// Projected from intermediate
	// Core gives LProjection -- projection Modules should be by Job
	public LProjection getProjection(ProtoName<Global> fullname, Role self)
	{
		LProtoName projFullname = InlinedProjector.getFullProjectionName(fullname,
				self);
		LProjection proj = this.projs.get(projFullname);
		if (proj == null)
		{
			proj = getIntermediate(fullname).project(this.core, self);
			addProjection(proj);
		}
		return proj;
	}

	// Pre: addProjectedInlined (i.e., getProjection(ProtoName<Global>, Role))
	public LProjection getProjection(ProtoName<Local> fullname)
	{
		LProjection proj = this.projs.get(fullname);
		if (proj == null)
		{
			// FIXME: need global imed to build (reverse derive from fullname?)
			throw new RuntimeException("[TODO]: " + fullname);
		}
		return proj;
	}
	
	protected void addProjection(LProjection proj)
	{
		this.projs.put(proj.fullname, proj);
	}
	
	// N.B. mutates this.projected -- used by "fixing" passes
	// TODO refactor (overall, into Job)
	public void setProjection(LProjection proj)
	{
		this.projs.put(proj.fullname, proj);
	}
	
	// N.B. graphs built from inlined (not unfolded)
	public EGraph getEGraph(ProtoName<Global> fullname, Role self)
	{
		LProtoName projFullname = InlinedProjector.getFullProjectionName(fullname, self);
		EGraph graph = this.fEGraphs.get(projFullname);
		if (graph == null)
		{
			LProjection inlined = getProjectedInlined(fullname, self);
			graph = inlined.toEGraph(this.core);
			addEGraph(inlined.fullname, graph);  // inlined.fullname.equals(projFullname)
		}
		return graph;
	}
	
	protected void addEGraph(ProtoName<Local> fullname, EGraph graph)
	{
		this.fEGraphs.put(fullname, graph);
	}
	
	// Pre: Core.runSyntacticWfPasses
	public EGraph getUnfairEGraph(ProtoName<Global> fullname, Role role)
	{
		return getUnfairEGraph(
				InlinedProjector.getFullProjectionName(fullname, role));
	}

	// Pre: Core.runSyntacticWfPasses
	// Pre: getEGraph(Global, Role) -- currently (CHECKME: revise ?)
	public EGraph getUnfairEGraph(ProtoName<Local> fullname)
	{
		EGraph unfair = this.uEGraphs.get(fullname);
		if (unfair == null)
		{
			EGraph fair = this.fEGraphs.get(fullname);  // Getting fair EGraph directly by projected fullname (cf. getEGraph(GProtoName, Role))
			if (fair == null)
			{
				throw new RuntimeException(
						"Call getEGraph(Global, Role) before getUnfairEGraph: " + fullname);
						// CHECKME: refactor ?
			}
			Pair<EState, EState> p = fair.init.unfairTransform(this.core.config.mf); //.toGraph();
			unfair = new EGraph(p.left, p.right);
			addUnfairEGraph(fullname, unfair);
		}
		return unfair;
	}
	
	protected void addUnfairEGraph(ProtoName<Local> fullname, EGraph graph)
	{
		this.uEGraphs.put(fullname, graph);
	}
	
	public SGraph getSGraph(ProtoName<Global> fullname) throws ScribException
	{
		SGraph graph = this.fSGraphs.get(fullname);
		if (graph == null)
		{
			Map<Role, EGraph> egraphs = getEGraphsForSGraphBuilding(fullname, true);
			boolean explicit = this.imeds.get(fullname).isExplicit();
			GProtoName cast = (GProtoName) fullname;  // Could also reconstruct if really needed
			graph = new SGraphBuilder(this.core).build(egraphs, explicit, cast);
			addSGraph(fullname, graph);
		}
		return graph;
	}

	private Map<Role, EGraph> getEGraphsForSGraphBuilding(
			ProtoName<Global> fullname, boolean fair) throws ScribException
	{
		Map<Role, EGraph> egraphs = new HashMap<>();
		for (Role self : this.imeds.get(fullname).roles)
		{
			egraphs.put(self, fair 
					? getEGraph(fullname, self) 
					: getUnfairEGraph(fullname, self));
		}
		return egraphs;
	}

	protected void addSGraph(ProtoName<Global> fullname, SGraph graph)
	{
		this.fSGraphs.put(fullname, graph);
	}

	public SGraph getUnfairSGraph(ProtoName<Global> fullname) throws ScribException
	{
		SGraph graph = this.uSGraphs.get(fullname);
		if (graph == null)
		{
			Map<Role, EGraph> egraphs = getEGraphsForSGraphBuilding(fullname, false);
			boolean explicit = this.imeds.get(fullname).isExplicit();
			GProtoName cast = (GProtoName) fullname;  // Could also reconstruct if really needed
			graph = new SGraphBuilder(this.core).build(egraphs, explicit, cast);
			addUnfairSGraph(fullname, graph);
		}
		return graph;
	}

	protected void addUnfairSGraph(ProtoName<Global> fullname, SGraph graph)
	{
		this.uSGraphs.put(fullname, graph);
	}
	
	public EGraph getMinimisedEGraph(ProtoName<Global> fullname, Role role)
			throws ScribException
	{
		LProtoName fulllpn = InlinedProjector.getFullProjectionName(fullname, role);

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
	
	protected void addMinimisedEGraph(ProtoName<Local> fullname, EGraph graph)
	{
		this.mEGraphs.put(fullname, graph);
	}

	// TODO: relocate
	// Duplicated from CommandLine.runDot -- TODO: update to use createTmpFile (cf. runDot)
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
