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
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.lang.local.LProjection;
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
		// Passes populate JobContext on-demand by each individual getter
		runSyntacticTransformPasses();
		runSyntacticWfPasses();
		runProjectionPasses();  // CHECKME: can try before validation (i.e., including syntactic WF), to promote greater tool feedback? (cf. CommandLine output "barrier")
		runEfsmBuildingPasses();  // Currently, unfair-transform graph building must come after syntactic WF --- TODO fix graph building to prevent crash ?
		runModelCheckingPasses();
	}
	
	// Populates JobContext -- although patten is to do on-demand via (first) getter, so (partially) OK to delay population
	protected void runSyntacticTransformPasses()  // No ScribException, no errors expected
	{
		verbosePrintPass("Inlining subprotocols for all globals...");
		for (GProtoName fullname : this.context.getParsedFullnames())
		{
			GProtocol inlined = this.context.getInlined(fullname);
			verbosePrintPass(
					"Inlined subprotocols: " + fullname + "\n" + inlined);
		}
				
		verbosePrintPass("Unfolding all recursions once for all inlined globals...");
		for (GProtoName fullname : this.context.getParsedFullnames())
		{
			// TODO: currently, unfolded not actually stored by Context -- unfoldAllOnce repeated manually when needed, e.g., runSyntacticWfPasses
			GProtocol inlined = this.context.getInlined(fullname);
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
		for (GProtoName fullname : this.context.getParsedFullnames())
		{
			GProtocol inlined = this.context.getInlined(fullname);
			for (Role self : inlined.roles)
			{
				// pruneRecs already done (see runContextBuildingPasses)
				// CHECKME: projection and inling commutative?
				LProjection iproj = this.context.getProjectedInlined(inlined.fullname,
						self);
				verbosePrintPass("Projected inlined onto " + self + ": "
						+ inlined.fullname + "\n" + iproj);
			}
		}

		// Pre: inlined already projected -- used for Do projection
		verbosePrintPass("Projecting all intermediate globals...");
		for (GProtoName fullname : this.context.getParsedFullnames())
		{
			GProtocol imed = this.context.getIntermediate(fullname);
			for (Role self : imed.roles)
			{
				LProjection proj = this.context.getProjection(imed.fullname, self);
				verbosePrintPass("Projected intermediate onto " + self + ": "
						+ imed.fullname + "\n" + proj);
			}
		}
	}

	// Builds only the fair EFSMs, unfair EFSM building depends on WF (role enabling, e.g., bad.wfchoice.enabling.threeparty.Test02) for building algorithm to work...
	// ...or patch unfair-transform graph building to not crash?
	protected void runEfsmBuildingPasses()
	{
		verbosePrintPass("Building EFSMs for all projected inlineds...");
		for (GProtoName fullname : this.context.getParsedFullnames())
		{
			GProtocol inlined = this.context.getInlined(fullname);
			for (Role self : inlined.roles)
			{
				// Seems to be OK even if runSyntacticWfPasses fails (cf. unfair transform)
				EGraph graph = this.context.getEGraph(fullname, self);
				verbosePrintPass(
						"Built EFSM: " + inlined.fullname + ":\n" + graph.toDot());
			}
		}
				
		if (!this.config.args.get(CoreArgs.FAIR))
		{
			verbosePrintPass(
					"Building \"unfair\" EFSMs for all projected inlineds...");
			for (GProtoName fullname : this.context.getParsedFullnames())
			{
				GProtocol inlined = this.context.getInlined(fullname);
				for (Role self : inlined.roles)
				{
					// Pre: runSyntacticWfPasses -- e.g., bad.wfchoice.enabling.threeparty.Test02
					EGraph graph = this.context.getUnfairEGraph(inlined.fullname, self);
					verbosePrintPass("Built \"unfair\" EFSM: " + inlined.fullname + ":\n"
							+ graph.toDot());
				}
			}
		}
	}
	
	protected void runSyntacticWfPasses() throws ScribException
	{
		verbosePrintPass(
				"Checking for unused role decls on all inlined globals...");
		for (GProtoName fullname : this.context.getParsedFullnames())
		{
			// CHECKME: relegate to "warning" ? -- some downsteam operations may depend on this though (e.g., graph building?)
			Set<Role> used = this.context.getInlined(fullname).def
					.gather(new RoleGatherer<Global, GSeq>()::visit)
					.collect(Collectors.toSet());
			Set<Role> unused = this.context.getIntermediate(fullname).roles
							// imeds have original role decls (inlined's are pruned)
					.stream().filter(x -> !used.contains(x)).collect(Collectors.toSet());
			if (!unused.isEmpty())
			{
				throw new ScribException(
						"Unused roles in " + fullname + ": " + unused);
			}
		}

		verbosePrintPass("Checking role enabling on all inlined globals...");
		for (GProtoName fullname : this.context.getParsedFullnames())
		{
			GProtocol inlined = this.context.getInlined(fullname);
			if (inlined.isAux())
			{
				continue;
			}
			GTypeUnfolder v = new GTypeUnfolder();
					//e.g., C->D captured under an A->B choice after unfolding, cf. bad.wfchoice.enabling.twoparty.Test01b;
			inlined.unfoldAllOnce(v).checkRoleEnabling();
					// TODO: get unfolded from Context
		}

		verbosePrintPass(
				"Checking consistent external choice subjects on all inlined globals...");
		for (GProtoName fullname : this.context.getParsedFullnames())
		{
			GProtocol inlined = this.context.getInlined(fullname);
			if (inlined.isAux())
			{
				continue;
			}
			inlined.checkExtChoiceConsistency();
		}
		
		verbosePrintPass("Checking reachability on all projected inlineds...");
		for (GProtoName fullname : this.context.getParsedFullnames())
		{
			GProtocol inlined = this.context.getInlined(fullname);
			if (inlined.isAux())  // CHECKME: also check for aux? e.g., bad.reach.globals.gdo.Test01b 
			{
				continue;
			}
			for (Role r : inlined.roles)
			{
				LProjection iproj = this.context.getProjectedInlined(fullname, r);
				iproj.checkReachability();
			}
		}
	}

	protected void runModelCheckingPasses() throws ScribException
	{
		verbosePrintPass("Building and checking models from projected inlineds...");  // CHECKME: separate and move model building earlier?
		// CHECKME: refactor more/whole validation into lang.GProtocol ?
		for (GProtoName fullname : this.context.getParsedFullnames())
		{
			if (this.context.getIntermediate(fullname).isAux())
			{
				continue;
			}
			verbosePrintPass("Validating: " + fullname);
			if (this.config.args.get(CoreArgs.SPIN))
			{
				if (this.config.args.get(CoreArgs.FAIR))
				{
					throw new RuntimeException(
							"[TODO]: -spin currently does not support fair ouput choices.");
				}
				GProtocol.validateBySpin(this, fullname);
			}
			else
			{
				GProtocol.validateByScribble(this, fullname, true);
				if (!this.config.args.get(CoreArgs.FAIR))
				{
					verbosePrintPass(
							"Validating with \"unfair\" output choices: " + fullname);
					GProtocol.validateByScribble(this, fullname, false);  // TODO: only need to check progress, not full validation
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
		
		List<ProtoName<Local>> pfullnames = proj.def
				.gather(new ProtoDepsCollector<Local, LSeq>()::visit)
				.collect(Collectors.toList());
		for (ProtoName<Local> pfullname : pfullnames)
		{
			System.out
					.println("\n" + this.context.getProjection((LProtoName) pfullname));
		}
		if (!pfullnames.contains(proj.fullname))
		{
			System.out.println("\n" + proj);
		}

		List<MemberName<?>> ns = proj.def
				.gather(new NonProtoDepsGatherer<Local, LSeq>()::visit)
				.collect(Collectors.toList());

		warningPrintln("");
		warningPrintln("[TODO] Full module projection (with imports): "
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