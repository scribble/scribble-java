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
import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.global.SGraph;
import org.scribble.core.model.visit.local.NonDetPayChecker;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.LProtoName;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.ModuleName;
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.STypeFactory;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.core.type.session.global.GTypeFactoryImpl;
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
	
	public Core(ModuleName mainFullname, Map<CoreArgs, Boolean> args,
			//Map<ModuleName, ModuleContext> modcs, 
			Set<GProtocol> imeds)
	{
		this.config = newCoreConfig(mainFullname, args);
		this.context = newCoreContext(//modcs, 
				imeds);  // Single instance per Core and should never be shared
	}

	// A Scribble extension should override newCoreConfig/Context/etc as appropriate
	protected CoreConfig newCoreConfig(ModuleName mainFullname,
			Map<CoreArgs, Boolean> args)
	{
		// TODO: factor out factory methods
		STypeFactory tf = new STypeFactory(new GTypeFactoryImpl());
		ModelFactory mf = new ModelFactoryImpl();
		return new CoreConfig(mainFullname, args, tf, mf); 
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
		runSyntaxTransformPasses();
		runGlobalSyntaxWfPasses();
		runProjectionPasses();  // CHECKME: can try before validation (i.e., including syntactic WF), to promote greater tool feedback? (cf. CommandLine output "barrier")
		runProjectionSyntaxWfPasses();
		runEfsmBuildingPasses();  // Currently, unfair-transform graph building must come after syntactic WF --- TODO fix graph building to prevent crash ?
		runLocalModelCheckingPasses();
		runGlobalModelCheckingPasses();
	}
	
	// Populates JobContext -- although patten is to do on-demand via (first) getter, so (partially) OK to delay population
	protected void runSyntaxTransformPasses()  // No ScribException, no errors expected
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
			// TODO: currently, unfolded not actually stored by Context -- unfoldAllOnce repeated manually when needed, e.g., runSyntaxWfPasses
			GProtocol inlined = this.context.getInlined(fullname);
			GTypeUnfolder v = new GTypeUnfolder(this);
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

	// Pre: runGlobalSyntaxWfPasses -- unfair EFSM building depends on WF (role enabling, e.g., bad.wfchoice.enabling.threeparty.Test02) for building algorithm to work...
	// ...or patch unfair-transform graph building to not crash?
	protected void runEfsmBuildingPasses()
	{
		verbosePrintPass("Building EFSMs for all projected inlineds...");
		for (GProtoName fullname : this.context.getParsedFullnames())
		{
			GProtocol inlined = this.context.getInlined(fullname);
			for (Role self : inlined.roles)
			{
				// Seems to be OK even if runSyntaxWfPasses does not succeed (cf. unfair transform)
				EGraph graph = this.context.getEGraph(fullname, self);
				verbosePrintPass(
						"Built EFSM: " + inlined.fullname + "\n" + graph.toDot());
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
					// Pre: runGlobalSyntaxWfPasses -- e.g., bad.wfchoice.enabling.threeparty.Test02
					EGraph graph = this.context.getUnfairEGraph(inlined.fullname, self);
					verbosePrintPass("Built \"unfair\" EFSM: " + inlined.fullname + ":\n"
							+ graph.toDot());
				}
			}
		}
	}
	
	protected void runGlobalSyntaxWfPasses() throws ScribException
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
			GTypeUnfolder v = new GTypeUnfolder(this);
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

		verbosePrintPass(
				"Checking connectedness on all inlined globals...");
		for (GProtoName fullname : this.context.getParsedFullnames())
		{
			GProtocol inlined = this.context.getInlined(fullname);
			if (inlined.isAux())
			{
				continue;
			}
			GTypeUnfolder v = new GTypeUnfolder(this);  // FIXME: record unfoldings in Context (factor out with role enabling)
					//e.g., rec X { connect A to B; continue X; }
			inlined.unfoldAllOnce(v).checkConnectedness(!inlined.isExplicit());
		}
	}
		
	// Pre: runGlobalSyntaxWfPasses
	protected void runProjectionSyntaxWfPasses() throws ScribException
	{
		verbosePrintPass("Checking reachability on all projected inlineds...");
		for (GProtoName fullname : this.context.getParsedFullnames())
		{
			GProtocol inlined = this.context.getInlined(fullname);
			if (inlined.isAux())  // CHECKME: also check for aux? e.g., bad.reach.globals.gdo.Test01b 
			{
				continue;
			}
			for (Role self : inlined.roles)
			{
				LProjection iproj = this.context.getProjectedInlined(fullname, self);
				iproj.checkReachability();
			}
		}
	}

	protected void runLocalModelCheckingPasses() throws ScribException
	{
		verbosePrintPass("Checking non-deterministic messaging action payloads...");
		for (GProtoName fullname : this.context.getParsedFullnames())
		{
			GProtocol inlined = this.context.getInlined(fullname);
			if (inlined.isAux())  // CHECKME: also check for aux? e.g., bad.reach.globals.gdo.Test01b 
			{
				continue;
			}
			for (Role self : inlined.roles)
			{
				EState init = this.context.getEGraph(fullname, self).init;
				init.traverse(new NonDetPayChecker());
			}
		}
	}

	protected void runGlobalModelCheckingPasses() throws ScribException
	{
		verbosePrintPass("Building and checking global models from projected inlineds...");  // CHECKME: separate and move model building earlier?
		// CHECKME: refactor more/whole validation into lang.GProtocol ?
		for (GProtoName fullname : this.context.getParsedFullnames())
		{
			if (this.context.getIntermediate(fullname).isAux())
			{
				continue;
			}
			validateByScribble(fullname, true);
			if (!this.config.args.get(CoreArgs.FAIR))
			{
				//verbosePrintPass("Validating by Scribble with \"unfair\" output choices: " + fullname);
				validateByScribble(fullname, false);  // TODO: only need to check progress, not "full" validation
			}
		}
	}
	
	protected void validateByScribble(GProtoName fullname, boolean fair)
			throws ScribException
	{
		SGraph graph = fair
				? this.context.getSGraph(fullname)
				: this.context.getUnfairSGraph(fullname);
		if (this.config.args.containsKey(CoreArgs.VERBOSE))
		{
			String dot = graph.init.toDot();
			String[] lines = dot.split("\\R");
			verbosePrintPass(
					//"(" + fullname + ") Built global model...\n" + graph.init.toDot() + "\n(" + fullname + ") ..." + graph.states.size() + " states");
					"Built " + (!fair ? "\"unfair\" " : "") + "global model ("
							+ graph.states.size() + " states): " + fullname + "\n"
							+ ((lines.length > 50)  // CHECKME: factor out constant?
									? "...[snip]...  (model text over 50 lines, try -[u]model[png])"
									: dot));
		}

		verbosePrintPass("Checking " + (!fair ? "\"unfair\" " : "")
				+ "global model: " + fullname);
		this.config.mf.newSModel(graph).validate(this);
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
	}
	//*/
	
	/* // TODO FIXME: refactor following methods (e.g., non-static?)
	public static void validateBySpin(Core core, GProtoName fullname)
			throws ScribException
	{
		CoreContext corec = core.getContext();
		GProtocol gpd = corec.getInlined(fullname);
		
		List<Role> rs = gpd.roles.stream()
				.sorted(Comparator.comparing(Role::toString))
				.collect(Collectors.toList());
	
		Set<MsgId<?>> mids = gpd.def.//getMessageIds();
				gather(new MessageIdGatherer<Global, GSeq>()::visit)
				.collect(Collectors.toSet());
		
		//..........FIXME: get mids from SType, instead of old AST Collector
		
		String pml = "";
		pml += "mtype {" + mids.stream().map(mid -> mid.toString())
				.collect(Collectors.joining(", ")) + "};\n";
	
		// TODO CHECKME: explicit ?
	
		pml += "\n";
		List<Role[]> pairs = new LinkedList<>();
		for (Role r1 : rs)
		{
			for (Role r2 : rs)
			{
				if (!r1.equals(r2))
				{
					pairs.add(new Role[] {r1, r2});
				}
			}
		}
		//for (Role[] p : (Iterable<Role[]>) () -> pairs.stream().sorted().iterator())
		for (Role[] p : pairs)
		{
			pml += "chan s_" + p[0] + "_" + p[1] + " = [1] of { mtype };\n"
					+ "chan r_" + p[0] + "_" + p[1] + " = [1] of { mtype };\n"
					+ "bool empty_" + p[0] + "_" + p[1] + " = true;\n"
					+ "active proctype chan_" + p[0] + "_" + p[1] + "() {\n"
					+ "mtype m;\n"
					+ "end_chan_" + p[0] + "_" + p[1] + ":\n"
					+ "do\n"
					+ "::\n"
					+ "atomic { s_" + p[0] + "_" + p[1] + "?m; empty_" + p[0] + "_" + p[1]
							+ " = false }\n"
					+ "atomic { r_" + p[0] + "_" + p[1] + "!m; empty_" + p[0] + "_" + p[1]
							+ " = true }\n"
					+ "od\n"
					+ "}\n";
		}
		
		for (Role r : rs)
		{
			pml += "\n\n" + corec.getEGraph(fullname, r).toPml(r);
		}
		if (core.config.args.get(CoreArgs.VERBOSE))
		{
			System.out.println("[-spin]: Promela processes\n" + pml + "\n");
		}
		
		List<String> clauses = new LinkedList<>();
		for (Role r : rs)
		{
			Set<EState> tmp = new HashSet<>();
			EGraph g = corec.getEGraph(fullname, r);
			tmp.add(g.init);
			tmp.addAll(g.init.getReachableStates());
			if (g.term != null)
			{
				tmp.remove(g.term);
			}
			tmp.forEach(  // Throws exception, cannot use flatMap
					s -> clauses.add("!<>[]" + r + "@label" + r + s.id)  // FIXME: factor out
			);
		}
		// * /
		/*String roleProgress = "";  // This way is not faster
		for (Role r : rs)
		{
			Set<EState> tmp = new HashSet<>();
			EGraph g = jc.getEGraph(fullname, r);
			tmp.add(g.init);
			tmp.addAll(MState.getReachableStates(g.init));
			if (g.term != null)
			{
				tmp.remove(g.term);
			}
			roleProgress += (((roleProgress.isEmpty()) ? "" : " || ")
					+ tmp.stream().map(s -> r + "@label" + r + s.id).collect(Collectors.joining(" || ")));
		}
		roleProgress = "!<>[](" + roleProgress + ")";
		clauses.add(roleProgress);* /
		String eventualStability = "";
		for (Role[] p : pairs)
		{
			//eventualStability += (((eventualStability.isEmpty()) ? "" : " && ") + "empty_" + p[0] + "_" + p[1]);
			eventualStability += (((eventualStability.isEmpty()) ? "" : " && ") + "<>empty_" + p[0] + "_" + p[1]);
		}
		//eventualStability = "[]<>(" + eventualStability + ")";
		eventualStability = "[](" + eventualStability + ")";  // FIXME: current "eventual reception", not eventual stability
		clauses.add(eventualStability);
	
		//int batchSize = 10;  // FIXME: factor out
		int batchSize = 6;  // FIXME: factor out  // FIXME: dynamic batch sizing based on previous batch duration?
		for (int i = 0; i < clauses.size(); )
		{
			int j = (i+batchSize < clauses.size()) ? i+batchSize : clauses.size();
			String batch = clauses.subList(i, j).stream().collect(Collectors.joining(" && "));
			String ltl = "ltl {\n" + batch + "\n" + "}";
			if (core.config.args.get(CoreArgs.VERBOSE))
			{
				System.out.println("[-spin] Batched ltl:\n" + ltl + "\n");
			}
			if (!GProtocol.runSpin(fullname.toString(), pml + "\n\n" + ltl))
			{
				throw new ScribException("Protocol not valid:\n" + gpd);
			}
			i += batchSize;
		}
	}

	// TODO:: relocate (also Context.runAut)
	public static boolean runSpin(String prefix, String pml) //throws ScribbleException
	{
		File tmp;
		try
		{
			tmp = File.createTempFile(prefix, ".pml.tmp");
			try
			{
				String tmpName = tmp.getAbsolutePath();				
				ScribUtil.writeToFile(tmpName, pml);
				String[] res = ScribUtil.runProcess("spin", "-a", tmpName);
				res[0] = res[0].replaceAll("(?m)^ltl.*$", "");
				res[1] = res[1].replace(
						"'gcc-4' is not recognized as an internal or external command,\noperable program or batch file.",
						"");
				res[1] = res[1].replace(
						"'gcc-3' is not recognized as an internal or external command,\noperable program or batch file.",
						"");
				res[0] = res[0].trim();
				res[1] = res[1].trim();
				if (!res[0].trim().isEmpty() || !res[1].trim().isEmpty())
				{
					//throw new RuntimeException("[scrib] : " + Arrays.toString(res[0].getBytes()) + "\n" + Arrays.toString(res[1].getBytes()));
					throw new RuntimeException("[-spin] [spin]: " + res[0] + "\n" + res[1]);
				}
				int procs = 0;
				for (int i = 0; i < pml.length(); procs++)
				{
					i = pml.indexOf("proctype", i);
					if (i == -1)
					{
						break;
					}
					i++;
				}
				int dnfair = (procs <= 6) ? 2 : 3;  // FIXME
				res = ScribUtil.runProcess("gcc", "-o", "pan", "pan.c", "-DNFAIR=" + dnfair);
				res[0] = res[0].trim();
				res[1] = res[1].trim();
				if (!res[0].isEmpty() || !res[1].isEmpty())
				{
					throw new RuntimeException("[-spin] [gcc]: " + res[0] + "\n" + res[1]);
				}
				res = ScribUtil.runProcess("pan", "-a", "-f");
				res[1] = res[1].replace("warning: no accept labels are defined, so option -a has no effect (ignored)", "");
				res[0] = res[0].trim();
				res[1] = res[1].trim();
				if (res[0].contains("error,") || !res[1].isEmpty())
				{
					throw new RuntimeException("[-spin] [pan]: " + res[0] + "\n" + res[1]);
				}
				int err = res[0].indexOf("errors: ");
				boolean valid = (res[0].charAt(err + 8) == '0');
				if (!valid)
				{
					System.err.println("[-spin] [pan] " + res[0] + "\n" + res[1]);
				}
				return valid;
			}
			catch (ScribException e)
			{
				throw new RuntimeException(e);
			}
			finally
			{
				tmp.delete();
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	//*/