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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.lang.local.LProtocol;
import org.scribble.core.model.ModelFactory;
import org.scribble.core.model.ModelFactoryImpl;
import org.scribble.core.model.endpoint.EGraph;
import org.scribble.core.model.global.SGraph;
import org.scribble.core.model.global.SGraphBuilderUtil;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.LProtoName;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.ModuleName;
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.visit.NonProtoDepsGatherer;
import org.scribble.core.visit.ProtoDepsCollector;
import org.scribble.core.visit.RoleGatherer;
import org.scribble.core.visit.global.GTypeInliner;
import org.scribble.core.visit.global.GTypeUnfolder;
import org.scribble.util.ScribException;

// A "compiler job" front-end that supports operations comprising visitor passes over the AST and/or local/global models
public class Core
{
	public final CoreConfig config;  // Immutable

	private final CoreContext context;  // Mutable (Visitor passes replace modules)
	private final SGraphBuilderUtil sgraphb;
	
	public Core(ModuleName mainFullname, Map<CoreArgs, Boolean> args,
			//Map<ModuleName, ModuleContext> modcs, 
			Set<GProtocol> imeds)
	{
		this.config = newCoreConfig(mainFullname, args);
		this.context = newCoreContext(//modcs, 
				imeds);  // Single instance per Core and should never be shared
		this.sgraphb = this.config.mf.newSGraphBuilderUtil();
	}

	// A Scribble extension should override newCoreConfig/Context/etc as appropriate
	protected CoreConfig newCoreConfig(ModuleName mainFullname,
			Map<CoreArgs, Boolean> args)
	{
		ModelFactory mf = new ModelFactoryImpl();
		return new CoreConfig(mainFullname, args, mf); 
				// CHECKME: combine E/SModelFactory?
	}

	// A Scribble extension should override newCoreConfig/Context/etc as appropriate
	protected CoreContext newCoreContext(//Map<ModuleName, ModuleContext> modcs,
			Set<GProtocol> imeds)
	{
		return new CoreContext(this, //modcs, 
				imeds);
	}

	public void runPasses() throws ScribException
	{
		runContextBuildingPasses();  // Populates JobContext -- includes runProjectionPasses and runEfsmBuildingPasses
		runSyntacticWfPasses();
		runModelCheckingPasses();
	}
	
	// Populates JobContext -- although patten is to do on-demand via (first) getter, so (partially) OK to delay population
	public void runContextBuildingPasses()  // No ScribException, no errors expected
	{
		runInliningAndUnfoldingPasses();
		runProjectionPasses();  // Do before any validation (i.e., including global WF), to promote greater tool feedback
		runEfsmBuildingPasses();
	}

	private void runInliningAndUnfoldingPasses()
	{
		verbosePrintPass("Inlining subprotocols for all globals...");
		for (GProtocol imed : this.context.getIntermediates())
		{
			GTypeInliner v = new GTypeInliner(this);
			GProtocol inlined = imed.getInlined(v);  // Protocol.getInlined does pruneRecs
			verbosePrintPass(
					"Inlined subprotocols: " + imed.fullname + "\n" + inlined);
			this.context.addInlined(imed.fullname, inlined);
		}
				
		verbosePrintPass("Unfolding all recursions once for all inlined globals...");
		for (GProtocol inlined : this.context.getInlineds())
		{
			GTypeUnfolder v = new GTypeUnfolder();
			GProtocol unf = (GProtocol) inlined.unfoldAllOnce(v);//.unfoldAllOnce(unf2);  // CHECKME: twice unfolding? instead of "unguarded"-unfolding?
			verbosePrintPass(
					"Unfolded all recursions once: " + inlined.fullname + "\n" + unf);
		}
	}

	// Due to Projector not being a subprotocol visitor, so "external" subprotocols may not be visible in ModuleContext building for the projections of the current root Module
	// SubprotocolVisitor it doesn't visit the target Module/ProtocolDecls -- that's why the old Projector maintained its own dependencies and created the projection modules after leaving a Do separately from SubprotocolVisiting
	// So Projection should not be an "inlining" SubprotocolVisitor, it would need to be more a "DependencyVisitor"
	protected void runProjectionPasses()  // No ScribException, no errors expected
	{
		verbosePrintPass("Projecting all inlined globals...");
		for (GProtocol inlined : this.context.getInlineds())
		{
			for (Role self : inlined.roles)
			{
				// pruneRecs already done (see runContextBuildingPasses)
				LProtocol iproj = inlined.projectInlined(this, self);  // CHECKME: projection and inling commutative?
				this.context.addProjectedInlined(iproj.fullname, iproj);
				verbosePrintPass("Projected inlined onto " + self + ": "
						+ inlined.fullname + "\n" + iproj);
			}
		}

		// Pre: inlined already projected -- used for Do projection
		verbosePrintPass("Projecting all intermediate globals...");
		for (GProtocol imed : this.context.getIntermediates())
		{
			for (Role self : imed.roles)
			{
				LProtocol proj = imed.project(this, self);  // Does pruneRecs
				this.context.addProjection(proj);
				verbosePrintPass("Projected intermediate onto " + self + ": "
						+ imed.fullname + "\n" + proj);
			}
		}
	}

	private void runEfsmBuildingPasses()
	{
		verbosePrintPass("Building EFSMs for all projected inlineds...");
		for (Entry<LProtoName, LProtocol> e : this.context.getProjectedInlineds()
				.entrySet())
		{
			LProtocol proj = e.getValue();
			EGraph graph = proj.toEGraph(this);  // CHECKME: refactor actual construction back to on-demand in Context, and just use getters here?  And for other passes?
			this.context.addEGraph(proj.fullname, graph);
			verbosePrintPass("Built EFSM: " + proj.fullname + ":\n" + graph.toDot());
		}
				
		/*if (!this.config.args.get(CoreArgs.FAIR))
		{
			// Pre: runSyntacticWfPasses -- depends on WF (role enabling, e.g., bad.wfchoice.enabling.threeparty.Test02), for graph building algorithm to work 
			verbosePrintPass("Building \"unfair\" EFSMs for all projected inlineds...");
			for (Entry<LProtoName, LProtocol> e : this.context.getProjectedInlineds()
					.entrySet())
			{
				LProtocol proj = e.getValue();  // FIXME: don't need actual proj
				EGraph graph = this.context.getUnfairEGraph(proj.fullname);  // CHECKME: refactor actual construction back to on-demand in Context, and just use getters here?  And for other passes?
				verbosePrintPass("Built \"unfair\" EFSM: " + proj.fullname + ":\n" + graph.toDot());
			}
		}*/
	}
	
	public void runSyntacticWfPasses() throws ScribException
	{
		verbosePrintPass(
				"Checking for unused role decls on all inlined globals...");
		for (GProtocol inlined : this.context.getInlineds())
		{
			// CHECKME: relegate to "warning" ? -- some downsteam operations may depend on this though (e.g., graph building?)
			Set<Role> used = inlined.def
					.gather(new RoleGatherer<Global, GSeq>()::visit)
					.collect(Collectors.toSet());
			Set<Role> unused = this.context.getIntermediate(inlined.fullname).roles
							// imeds have original role decls (inlined's are pruned)
					.stream().filter(x -> !used.contains(x)).collect(Collectors.toSet());
			if (!unused.isEmpty())
			{
				throw new ScribException(
						"Unused roles in " + inlined.fullname + ": " + unused);
			}
		}

		verbosePrintPass("Checking role enabling on all inlined globals...");
		for (GProtocol inlined : (Iterable<GProtocol>) this.context.getInlineds()
				.stream().filter(x -> !x.isAux())::iterator)
		{
			GTypeUnfolder v = new GTypeUnfolder();
					//e.g., C->D captured under an A->B choice after unfolding, cf. bad.wfchoice.enabling.twoparty.Test01b;
			inlined.unfoldAllOnce(v).checkRoleEnabling();
		}

		verbosePrintPass(
				"Checking consistent external choice subjects on all inlined globals...");
		for (GProtocol inlined : (Iterable<GProtocol>) this.context.getInlineds()
				.stream().filter(x -> !x.isAux())::iterator)
		{
			inlined.checkExtChoiceConsistency();
		}
		
		verbosePrintPass("Checking reachability on all projected inlineds...");
		for (LProtocol proj : (Iterable<LProtocol>) this.context
				.getProjectedInlineds().values().stream()
				.filter(x -> !x.isAux())::iterator)  // CHECKME? e.g., bad.reach.globals.gdo.Test01b 
		{
			proj.checkReachability();
		}
	}

	public void runModelCheckingPasses() throws ScribException
	{
		verbosePrintPass("Building and checking models from projected inlineds...");  // Move model building earlier?
		// CHECKME: refactor more/whole validation into lang.GProtocol ?
		for (GProtocol iproj : (Iterable<GProtocol>) this.context.getInlineds()
				.stream().filter(x -> !x.isAux())::iterator)
		{
			verbosePrintPass("Validating: " + iproj.fullname);
			if (this.config.args.get(CoreArgs.SPIN))
			{
				if (this.config.args.get(CoreArgs.FAIR))
				{
					throw new RuntimeException(
							"[TODO]: -spin currently does not support fair ouput choices.");
				}
				GProtocol.validateBySpin(this, iproj.fullname);
			}
			else
			{
				GProtocol.validateByScribble(this, iproj.fullname, true);
				if (!this.config.args.get(CoreArgs.FAIR))
				{
					verbosePrintPass(
							"Validating with \"unfair\" output choices: " + iproj.fullname);
					GProtocol.validateByScribble(this, iproj.fullname, false);  // TODO: only need to check progress, not full validation
				}
			}
		}
	}
	
	public SGraph buildSGraph(GProtoName fullname, Map<Role, EGraph> egraphs,
			boolean explicit) throws ScribException
	{
		/*verbosePrintln("(" + fullname + ") Building global model using:");
		for (Role r : egraphs.keySet())
		{
			verbosePrintln("-- EFSM for "
					+ r + ":\n" + egraphs.get(r).init.toDot());
		}*/
		return this.sgraphb.buildSGraph(this, fullname, egraphs, explicit);  // CHECKME: factor out util -- ?
	}

	// Pre: checkWellFormedness 
	// Returns: fullname -> Module -- CHECKME TODO: refactor local Module creation to Job?
	// CHECKME: generate projection Modules for an inline main? -- no: TODO refactor to Job
	public Map<LProtoName, LProtocol> getProjections(GProtoName fullname,
			Role role) throws ScribException
	{
		//Module root = 
		LProtocol proj =
				this.context.getProjection(fullname, role);
		
		List<ProtoName<Local>> ps = proj.def
				.gather(new ProtoDepsCollector<Local, LSeq>()::visit)
				.collect(Collectors.toList());
		for (ProtoName<Local> p : ps)
		{
			System.out.println("\n" + this.context.getProjection((LProtoName) p));
		}
		if (!ps.contains(proj.fullname))
		{
			System.out.println("\n" + proj);
		}

		List<MemberName<?>> ns = proj.def
				.gather(new NonProtoDepsGatherer<Local, LSeq>()::visit)
				.collect(Collectors.toList());

		warningPrintln("");
		warningPrintln("[TODO] Full module projection and imports: "
				+ fullname + "@" + role);
		
		return Collections.emptyMap();
				//FIXME: build output Modules
				//FIXME: (interleaved) ordering between proto and nonproto (Module) imports -- order by original Global import order?
	}
	
	public CoreContext getContext()
	{
		return this.context;
	}
	
	public boolean isVerbose()
	{
		return this.config.args.get(CoreArgs.VERBOSE);
	}
	
	public void verbosePrintln(String s)
	{
		if (isVerbose())
		{
			System.out.println(s);
		}
	}
	
	private void verbosePrintPass(String s)
	{
		verbosePrintln("\n[Core] " + s);
	}
	
	public void warningPrintln(String s)
	{
		System.err.println("[Warning] " + s);
	}
}

















	
	/*// TODO: deprecate, caller should go through config
	// A Scribble extension should override newCoreConfig/Context/etc as appropriate
	public SGraphBuilderUtil newSGraphBuilderUtil()
	{
		return this.config.mf.newSGraphBuilderUtil();
	}*/

	/*// TODO: deprecate, caller should go through config  // CHECKME: refactor more uniformly with mf.newSGraphBuilderUtil ?
	// A Scribble extension should override newCoreConfig/Context/etc as appropriate
	public EGraphBuilderUtil newEGraphBuilderUtil()
	{
		return new EGraphBuilderUtil(this.config.mf);
	}*/

	/*public Map<String, String> generateSessionApi(GProtocolName fullname) throws ScribbleException
	{
		debugPrintPass("Running " + SessionApiGenerator.class + " for " + fullname);
		SessionApiGenerator sg = new SessionApiGenerator(this, fullname);
		Map<String, String> map = sg.generateApi();  // filepath -> class source
		return map;
	}
	
	// FIXME: refactor an EndpointApiGenerator -- ?
	public Map<String, String> generateStateChannelApi(GProtocolName fullname, Role self, boolean subtypes) throws ScribbleException
	{
		/*if (this.jcontext.getEndpointGraph(fullname, self) == null)
		{
			buildGraph(fullname, self);
		}* /
		debugPrintPass("Running " + StateChannelApiGenerator.class + " for " + fullname + "@" + self);
		StateChannelApiGenerator apigen = new StateChannelApiGenerator(this, fullname, self);
		IOInterfacesGenerator iogen = null;
		try
		{
			iogen = new IOInterfacesGenerator(apigen, subtypes);
		}
		catch (RuntimeScribbleException e)  // FIXME: use IOInterfacesGenerator.skipIOInterfacesGeneration
		{
			//System.err.println("[Warning] Skipping I/O Interface generation for protocol featuring: " + fullname);
			warningPrintln("Skipping I/O Interface generation for: " + fullname + "\n  Cause: " + e.getMessage());
		}
		// Construct the Generators first, to build all the types -- then call generate to "compile" all Builders to text (further building changes will not be output)
		Map<String, String> api = new HashMap<>(); // filepath -> class source  // Store results?
		api.putAll(apigen.generateApi());
		if (iogen != null)
		{
			api.putAll(iogen.generateApi());
		}
		return api;
	}*/