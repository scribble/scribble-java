package org.scribble.ext.go.core.cli;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ast.Module;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.cli.CLArgFlag;
import org.scribble.cli.CommandLine;
import org.scribble.cli.CommandLineException;
import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreSyntaxException;
import org.scribble.ext.go.core.ast.global.RPCoreGProtocolDeclTranslator;
import org.scribble.ext.go.core.ast.global.RPCoreGType;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
//import org.scribble.ext.go.core.codegen.statetype.ParamCoreSTEndpointApiGenerator;
//import org.scribble.ext.go.core.codegen.statetype2.ParamCoreSTEndpointApiGenerator;
import org.scribble.ext.go.core.codegen.statetype3.RPCoreSTApiGenerator;
import org.scribble.ext.go.core.main.RPCoreException;
import org.scribble.ext.go.core.main.RPCoreMainContext;
import org.scribble.ext.go.core.model.endpoint.RPCoreEGraphBuilder;
import org.scribble.ext.go.core.model.endpoint.RPCoreEModelFactory;
import org.scribble.ext.go.core.model.endpoint.RPCoreEState;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEAction;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.ext.go.util.RecursiveFunctionalInterface;
import org.scribble.ext.go.util.Z3Wrapper;
import org.scribble.main.AntlrSourceException;
import org.scribble.main.Job;
import org.scribble.main.JobContext;
import org.scribble.main.ScribbleException;
import org.scribble.main.resource.DirectoryResourceLocator;
import org.scribble.main.resource.ResourceLocator;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EGraph;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;
import org.scribble.util.Pair;
import org.scribble.util.ScribParserException;
import org.scribble.util.ScribUtil;

// N.B. this is the CL for both -goapi and rp-core extensions
public class RPCoreCommandLine extends CommandLine
{
	protected final Map<RPCoreCLArgFlag, String[]> rpArgs;  // Maps each flag to list of associated argument values

	// HACK: store in (Core) Job/JobContext?
	private GProtocolDecl gpd;
	private Map<Role, Map<RPRoleVariant, RPCoreLType>> L0;
	private Map<Role, Map<RPRoleVariant, EGraph>> E0;
	//protected ParamCoreSModel model;
	private Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> families;
	private Map<RPRoleVariant, Set<RPRoleVariant>> peers;
	
	public RPCoreCommandLine(String... args) throws CommandLineException
	{
		this(new RPCoreCLArgParser(args));
	}

	private RPCoreCommandLine(RPCoreCLArgParser p) throws CommandLineException
	{
		super(p);  // calls p.parse()
		if (this.args.containsKey(CLArgFlag.INLINE_MAIN_MOD))
		{
			// FIXME: should be fine
			throw new RuntimeException("[param] Inline modules not supported:\n" + this.args.get(CLArgFlag.INLINE_MAIN_MOD));
		}
		// FIXME? Duplicated from core
		if (!this.args.containsKey(CLArgFlag.MAIN_MOD))
		{
			throw new CommandLineException("No main module has been specified\r\n");
		}
		this.rpArgs = p.getParamArgs();
	}
	
	@Override
	protected RPCoreMainContext newMainContext() throws ScribParserException, ScribbleException
	{
		boolean debug = this.args.containsKey(CLArgFlag.VERBOSE);  // TODO: factor out with CommandLine (cf. MainContext fields)
		boolean useOldWF = this.args.containsKey(CLArgFlag.OLD_WF);
		boolean noLiveness = this.args.containsKey(CLArgFlag.NO_LIVENESS);
		boolean minEfsm = this.args.containsKey(CLArgFlag.LTSCONVERT_MIN);
		boolean fair = this.args.containsKey(CLArgFlag.FAIR);
		boolean noLocalChoiceSubjectCheck = this.args.containsKey(CLArgFlag.NO_LOCAL_CHOICE_SUBJECT_CHECK);
		boolean noAcceptCorrelationCheck = this.args.containsKey(CLArgFlag.NO_ACCEPT_CORRELATION_CHECK);
		boolean noValidation = this.args.containsKey(CLArgFlag.NO_VALIDATION);

		boolean selectApi = this.rpArgs.containsKey(RPCoreCLArgFlag.RPCORE_SELECT_BRANCH);
		boolean noCopy = this.rpArgs.containsKey(RPCoreCLArgFlag.RPCORE_NO_COPY);

		List<Path> impaths = this.args.containsKey(CLArgFlag.IMPORT_PATH)
				? CommandLine.parseImportPaths(this.args.get(CLArgFlag.IMPORT_PATH)[0])
				: Collections.emptyList();
		ResourceLocator locator = new DirectoryResourceLocator(impaths);
		if (this.args.containsKey(CLArgFlag.INLINE_MAIN_MOD))
		{
			/*return new ParamMainContext(debug, locator, this.args.get(CLArgFlag.INLINE_MAIN_MOD)[0], useOldWF, noLiveness, minEfsm, fair,
					noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, solver);*/
			throw new RuntimeException("[param] Shouldn't get in here:\n" + this.args.get(CLArgFlag.INLINE_MAIN_MOD)[0]);  // Checked in constructor
		}
		else
		{
			Path mainpath = CommandLine.parseMainPath(this.args.get(CLArgFlag.MAIN_MOD)[0]);
			return new RPCoreMainContext(debug, locator, mainpath, useOldWF, noLiveness, minEfsm, fair,
					noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, noCopy, selectApi);
		}
	}

	public static void main(String[] args) throws CommandLineException, AntlrSourceException
	{
		new RPCoreCommandLine(args).run();
	}

	@Override
	protected void doValidationTasks(Job job) throws RPCoreSyntaxException, AntlrSourceException, ScribParserException, CommandLineException
	{
		if (this.rpArgs.containsKey(RPCoreCLArgFlag.RPCORE_PARAM))
		{
			doParamCoreValidationTasks((GoJob) job);
		}
		else
		{
			super.doValidationTasks(job);
		}
	}

	@Override
	protected void doNonAttemptableOutputTasks(Job job) throws ScribbleException, CommandLineException
	{		
		GoJob gjob = (GoJob) job;
		if (this.rpArgs.containsKey(RPCoreCLArgFlag.RPCORE_API_GEN))
		{
			JobContext jcontext = job.getContext();
			String simpname = this.rpArgs.get(RPCoreCLArgFlag.RPCORE_PARAM)[0];
			GProtocolName fullname = checkGlobalProtocolArg(jcontext, simpname);
			String impath = this.rpArgs.get(RPCoreCLArgFlag.RPCORE_PARAM)[1];
			String[] args = this.rpArgs.get(RPCoreCLArgFlag.RPCORE_API_GEN);
			List<Role> roles = new LinkedList<>();
			for (int i = 0; i < args.length; i += 1)
			{
				Role role = checkRoleArg(jcontext, fullname, args[i]);
				roles.add(role);
				/*for (ParamActualRole ranges : this.P0.get(role).keySet())
				{
					EGraph efsm = this.E0.get(role).get(ranges);
					Map<String, String> goClasses = new ParamCoreSTEndpointApiGenerator(job, fullname, ranges, efsm).build();
					outputClasses(goClasses);
				}*/

				/*job.debugPrintln("\n[rp-core] Running " + ParamCoreSTSessionApiBuilder.class + " for " + fullname);

				/*Map<String, String> goClasses = new ParamCoreSTSessionApiBuilder((GoJob) job, fullname, this.E0).build();* /
				//Map<ParamActualRole, EGraph> actuals = this.E0.get(role);
				Map<String, String> goClasses = new RPCoreSTApiGenerator(gjob, fullname, this.L0, this.E0, this.families, impath, role).build();
				outputClasses(goClasses);*/
			}

			Map<String, String> goClasses = new RPCoreSTApiGenerator(gjob, fullname, 
					this.L0, this.E0, this.families, this.peers, impath, roles).build();
			outputClasses(goClasses);
		}
		else
		{
			super.doNonAttemptableOutputTasks(job);
		}
	}

	private void doParamCoreValidationTasks(GoJob j) throws RPCoreSyntaxException, ScribbleException, ScribParserException, CommandLineException
	{
		/*if (this.args.containsKey(CLArgFlag.PROJECT))  // HACK
			// modules/f17/src/test/scrib/demo/fase17/AppD.scr in [default] mode bug --- projection/EFSM not properly formed if this if is commented ????
		{

		}*/

		paramCorePreContextBuilding(j);

		GProtocolName simpname = new GProtocolName(this.rpArgs.get(RPCoreCLArgFlag.RPCORE_PARAM)[0]);
		if (simpname.toString().equals("[ParamCoreAllTest]"))  // HACK: ParamCoreAllTest
		{
			paramCoreParseAndCheckWF(j);  // Includes base passes
		}
		else
		{
			paramCoreParseAndCheckWF(j, simpname);  // Includes base passes
		}
		
		// FIXME? rp-core FSM building only used for rp-core validation -- output tasks, e.g., -api, will still use default Scribble FSMs
		// -- but the FSMs should be the same? -- no: action assertions treated differently in core than base
	}

	
	// Refactor into Param(Core)Job?
	// Following methods are for assrt-*core*
	
	private void paramCorePreContextBuilding(GoJob job) throws ScribbleException
	{
		job.runContextBuildingPasses();

		//job.runVisitorPassOnParsedModules(RecRemover.class);  // FIXME: Integrate into main passes?  Do before unfolding? 
				// FIXME: no -- revise to support annots
	}

	// Pre: assrtPreContextBuilding(job)
	private void paramCoreParseAndCheckWF(GoJob job) throws RPCoreSyntaxException, ScribbleException, ScribParserException, CommandLineException
	{
		Module main = job.getContext().getMainModule();
		for (GProtocolDecl gpd : main.getGlobalProtocolDecls())
		{
			if (!gpd.isAuxModifier())
			{
				paramCoreParseAndCheckWF(job, gpd.getHeader().getDeclName());  // decl name is simple name
			}
		}
	}

	// Pre: paramCorePreContextBuilding(job)
	private void paramCoreParseAndCheckWF(GoJob job, GProtocolName simpname) throws RPCoreSyntaxException, ScribbleException, ScribParserException, CommandLineException
	{
		Module main = job.getContext().getMainModule();
		if (!main.hasProtocolDecl(simpname))
		{
			throw new RPCoreException("[rp-core] Global protocol not found: " + simpname);
		}
		this.gpd = (GProtocolDecl) main.getProtocolDecl(simpname);

		RPCoreAstFactory af = new RPCoreAstFactory();
		RPCoreGType gt = new RPCoreGProtocolDeclTranslator(job, af).translate(this.gpd);
		
		job.debugPrintln("\n[rp-core] Translated:\n  " + gt);
		
		if (!gt.isWellFormed(job, gpd))
		{
			throw new RPCoreException("[rp-core] Global type not well-formed:\n  " + gt);
		}

		//Map<Role, Set<Set<ParamRange>>> 
		Map<Role, Set<RPRoleVariant>> 
				variants = getVariants(job, gt);
		
		job.debugPrintln("\n[rp-core] Computed roles: " + variants);

		this.L0 = new HashMap<>();
		for (Role r : gpd.header.roledecls.getRoles())  // getRoles gives decl names  // CHECKME: can ignore params?
		{
			//for (Set<ParamRange> ranges : protoRoles.get(r))
			for (RPRoleVariant ranges : variants.get(r))
			{
				//ParamCoreLType lt = gt.project(af, r, ranges);
				RPCoreLType lt = gt.project(af, ranges);
				//Map<Set<ParamRange>, ParamCoreLType> tmp = P0.get(r);
				Map<RPRoleVariant, RPCoreLType> tmp = L0.get(r);
				if (tmp == null)
				{
					tmp = new HashMap<>();
					L0.put(r, tmp);
				}
				tmp.put(ranges, lt);

				job.debugPrintln("\n[rp-core] Projected onto " + r + " for " + ranges + ":\n  " + lt);
			}
		}

		RPCoreEGraphBuilder builder = new RPCoreEGraphBuilder(job);
		this.E0 = new HashMap<>();
		for (Role r : L0.keySet())
		{
			//for (Set<ParamRange> ranges : this.P0.get(r).keySet())
			for (RPRoleVariant ranges : this.L0.get(r).keySet())
			{
				EGraph g = builder.build(this.L0.get(r).get(ranges));
				//Map<Set<ParamRange>, EGraph> tmp = this.E0.get(r);
				Map<RPRoleVariant, EGraph> tmp = this.E0.get(r);
				if (tmp == null)
				{
					tmp = new HashMap<>();
					this.E0.put(r, tmp);
				}
				tmp.put(ranges, g);

				job.debugPrintln("\n[rp-core] Built endpoint graph for " 
						//+ r + " for "
						+ ranges + ":\n" + g.toDot());
				
				RecursiveFunctionalInterface<Function<RPCoreEState, Set<RPCoreEState>>> getNestedInits
						= new RecursiveFunctionalInterface<>();
				getNestedInits.func = s ->
				{
					Set<RPCoreEState> inits = new HashSet<>();
					if (s.hasNested())
					{
						inits.add(s.getNested());
					}
					inits.addAll(MState.getReachableStates(s).stream()
							.filter(x -> ((RPCoreEState) x).hasNested())
							.map(x -> ((RPCoreEState) x).getNested())
							.collect(Collectors.toSet()));
					inits.forEach(x -> inits.addAll(getNestedInits.func.apply(x)));
					return inits;
				};
				
				getNestedInits.func.apply((RPCoreEState) g.init).stream()
						.forEach(s -> job.debugPrintln("\n" + s.toDot()));
			}
		}
				
		/*assrtCoreValidate(job, simpname, gpd.isExplicitModifier());//, this.E0);  // TODO

		/*if (!job.fair)
		{
			Map<Role, EState> U0 = new HashMap<>();
			for (Role r : E0.keySet())
			{
				EState u = E0.get(r).unfairTransform();
				U0.put(r, u);

				job.debugPrintln
				//System.out.println
						("\n[rp-core] Unfair transform for " + r + ":\n" + u.toDot());
			}
			
			//validate(job, gpd.isExplicitModifier(), U0, true);  //TODO
		}*/
		
		//((ParamJob) job).runF17ProjectionPasses();  // projections not built on demand; cf. models

		//return gt;
		
		this.families = new HashSet<>();
		this.families.addAll(getFamilies(job));
		this.peers = new HashMap<>();
		this.peers.putAll(getPeers(job));
	}

	// ..FIXME: generalise to multirole processes?  i.e. all roles are A with different indices? -- also subsumes MP with single rolename?
	
	//..HERE FIXME ActualParam -- ParamRange is now already a Set
	
	
	// Compute variant peers of each variant
	private Map<RPRoleVariant, Set<RPRoleVariant>> getPeers(GoJob job)
	{
		job.debugPrintln("\n[rp-core] Computing peers:");

		Map<RPRoleVariant, Set<RPRoleVariant>> map = new HashMap<>();

		String[] args = this.rpArgs.get(RPCoreCLArgFlag.RPCORE_API_GEN);
		for (Role rname : (Iterable<Role>) Arrays.asList(args).stream().map(r -> new Role(r))::iterator)
		{
			for (RPRoleVariant self : this.E0.get(rname).keySet()) 
			{
				/*for (Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family :
						(Iterable<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>>) 
								this.families.keySet().stream().filter(f -> f.left.contains(variant))::iterator)  // FIXME: use family to make accept/dial
				{*/
					// CHECKME: use local types instead of FSMs?  (RPCoreGForeach#getIndexedRoles)
					Set<RPIndexedRole> irs = //MState.getReachableActions
							RPCoreEState.getReachableActions((RPCoreEModelFactory) job.ef,
									(RPCoreEState) this.E0.get(rname).get(self).init).stream()  // FIXME: static "overloading" (cf. MState) error prone
							.map(a -> ((RPCoreEAction) a).getPeer()).collect(Collectors.toSet());
					Set<RPRoleVariant> peers = new HashSet<>();
					next: for (RPRoleVariant peer : (Iterable<RPRoleVariant>)  // Candidate
							this.E0.values().stream()
									.flatMap(m -> m.keySet().stream())::iterator)
					{
						if (!peer.equals(self) && !peers.contains(peer))
						{
							job.debugPrintln("\n[rp-core] For " + self + ", checking potential peer: " + peer);
							
							//System.out.println("aaa: " + self + ",, "+ peer + ",, " + irs);
							
							for (RPIndexedRole ir : irs)
							{
								if (ir.getName().equals(peer.getName()))
								{
									if (ir.intervals.size() > 1)
									{
										throw new RuntimeException("[rp-core] TODO: multi-dimension intervals: " + ir);
									}
									RPInterval d = ir.intervals.stream().findAny().get();
									
									Set<RPIndexVar> vars = Stream.concat(peer.intervals.stream().flatMap(x -> x.getIndexVars().stream()), peer.cointervals.stream().flatMap(x -> x.getIndexVars().stream()))
											.collect(Collectors.toSet());
									vars.addAll(ir.getIndexVars());

									String smt2 = "(assert ";
									smt2 += "(exists ((peer Int) "
											+  (vars.isEmpty() ? "" : vars.stream().map(x -> "(" + x + " Int)").collect(Collectors.joining(" "))) + ")\n";
									smt2 += "(and \n";
									smt2 += peer.intervals.stream().map(x -> "(>= peer " + x.start + ") (<= peer " + x.end + ")").collect(Collectors.joining(" ")) + "\n";
									smt2 += peer.cointervals.isEmpty() 
											? ""
											: "(or " + peer.cointervals.stream().map(x -> "(< peer " + x.start + ") (> peer " + x.end + ")").collect(Collectors.joining(" ")) + ")\n";
									smt2 += "(>= peer " + d.start + ") (<= peer " + d.end + ")\n";
									smt2 += ")";
									smt2 += ")";
									smt2 += ")";
									
									job.debugPrintln("[rp-core] Running Z3 on:\n" + smt2);
									
									boolean isSat = Z3Wrapper.checkSat(job, this.gpd, smt2);
									job.debugPrintln("[rp-core] Checked sat: " + isSat);
									if (isSat)
									{
										peers.add(peer);
										continue next;
									}
								}
							}
						}
					}
					map.put(self, peers);
				//}
			}
		}
		return map;
	}	
	
	
	private Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> getFamilies(GoJob job)
	{
		job.debugPrintln("\n[rp-core] Computing families:");

		Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> fams = new HashSet<>();

		Set<RPRoleVariant> all = this.L0.values().stream()
				.flatMap(m -> m.keySet().stream()).collect(Collectors.toSet());
		Set<RPIndexVar> vars = all.stream()
				.flatMap(v -> v.getIndexVars().stream()).collect(Collectors.toSet());
		Set<Set<RPRoleVariant>> pset = ScribUtil.makePowerSet(all).stream()
				.filter(x -> x.size() >= 2).collect(Collectors.toSet());
		
		int size = pset.size();
		int i = 1;
		for (Set<RPRoleVariant> cand : pset)
		{
			Set<RPRoleVariant> coset = all.stream()
					.filter(x -> !cand.contains(x)).collect(Collectors.toSet());
			
			String smt2 = "(assert ";
			if (!vars.isEmpty())
			{
				smt2 += "(exists (" + vars.stream().map(x -> "(" + x + " Int)").collect(Collectors.joining(" ")) + ")\n";  // FIXME: factor up -- and factor out with getVariants
				smt2 += "(and\n";
			}
			smt2 += "(and " + cand.stream()
					.map(v -> makePhiSmt2(v.intervals, v.cointervals)).collect(Collectors.joining(" ")) + ")\n";
			smt2 += (coset.size() > 0)
					? "(and " + coset.stream()
							.map(v -> "(not " + makePhiSmt2(v.intervals, v.cointervals) + ")").collect(Collectors.joining(" ")) + ")\n"
					: "";
			if (!vars.isEmpty())
			{
				smt2 += "))";
			}
			smt2 += ")";
			
			job.debugPrintln("\n[rp-core] Candidate (" + i++ + "/" + size + "): " + cand);
			job.debugPrintln("[rp-core] Co-set: " + coset);
			job.debugPrintln("[rp-core] Running Z3 on:\n" + smt2);
			
			boolean isSat = Z3Wrapper.checkSat(job, this.gpd, smt2);
			if (isSat)
			{
				fams.add(new Pair<>(Collections.unmodifiableSet(cand), Collections.unmodifiableSet(coset)));
			}
			job.debugPrintln("[rp-core] Checked sat: " + isSat);
		}
		
		return fams;
	}
	
	private //Map<Role, Set<Set<ParamRange>>> 
			Map<Role, Set<RPRoleVariant>>
			getVariants(GoJob job, RPCoreGType gt)
	{
		Set<RPIndexedRole> prs = gt.getIndexedRoles();
		
		Map<Role, Set<RPIndexedRole>> map
				= prs.stream()
				.map(x -> x.getName())
				.distinct()
				.collect(Collectors.toMap(x -> x, x -> prs.stream().filter(y -> y.getName().equals(x)).collect(Collectors.toSet())));
		
		Map<Role, Set<Set<RPInterval>>> powersets = map.keySet().stream().collect(Collectors.toMap(k -> k, k -> new HashSet<>()));
		for (Role n : map.keySet())
		{
			Set<RPIndexedRole> tmp = map.get(n);
			Optional<RPIndexedRole> foo = tmp.stream().filter(x -> x.intervals.size() > 1).findAny();
			if (foo.isPresent())
			{
				throw new RuntimeException("[rp-core] TODO: multi-dimension intervals: " + foo.get());
			}
			powersets.put(n, ScribUtil.makePowerSet(tmp.stream().flatMap(v -> v.intervals.stream()).collect(Collectors.toSet())));
		}
		
		//Map<Role, Set<Set<ParamRange>>> protoRoles = powersets.keySet().stream().collect(Collectors.toMap(x -> x, x -> new HashSet<>()));
		Map<Role, Set<RPRoleVariant>> variants = powersets.keySet().stream().collect(Collectors.toMap(x -> x, x -> new HashSet<>()));
		for (Role r : powersets.keySet())
		{
			Set<Set<RPInterval>> powset = powersets.get(r);
			int i = 1;
			int size = powset.size();
			
			job.debugPrintln("\n[rp-core] Ranges powerset for " + r + ": " + powset);
			
			for (Set<RPInterval> cand : powset)
			{
				Set<RPInterval> coset = powset.stream()
						.filter(f -> f.stream().noneMatch(g -> cand.contains(g)))
						.flatMap(Collection::stream)
						.collect(Collectors.toSet());
				
				String z3 = makePhiSmt2(cand, coset);
				Set<RPIndexVar> vars = Stream.concat(
							cand.stream().flatMap(c -> c.getIndexVars().stream()),
							coset.stream().flatMap(c -> c.getIndexVars().stream())
						).collect(Collectors.toSet());
				if (!vars.isEmpty())
				{
					z3 = "(exists ("
							+ vars.stream().map(p -> "(" + p + " Int)").collect(Collectors.joining(" "))
							+ ") " + z3 + ")";
				}
				z3 = "(assert " + z3 + ")";
				
				job.debugPrintln("\n[rp-core] Candidate (" + i++ + "/" + size + "): " + cand);
				job.debugPrintln("[rp-core] Co-set: " + coset);
				job.debugPrintln("[rp-core] Running Z3 on:\n" + z3);
				
				boolean isSat = Z3Wrapper.checkSat(job, this.gpd, z3);
				if (isSat)
				{
					//protoRoles.get(r).add(cand);
					variants.get(r).add(new RPRoleVariant(r.toString(), cand, coset));
				}
				job.debugPrintln("[rp-core] Checked sat: " + isSat);
			}
		}
		return variants;
	}

	// Doesn't include "assert", nor exists-bind index vars
	private static String makePhiSmt2(Set<RPInterval> cand, Set<RPInterval> coset)
	{
		String z3 = "";

		if (cand.size() > 0)
		{
			z3 += cand.stream().map(c -> "(and (>= self " + c.start.toSmt2Formula() + ") (<= self " + c.end.toSmt2Formula() + ")"
						+ ((!c.start.isConstant() || !c.end.isConstant()) ? " (<= " + c.start.toSmt2Formula() + " " + c.end.toSmt2Formula() + ")" : "")
						+ ")")
					.reduce((c1, c2) -> "(and " + c1 + " " + c2 +")").get();
		}

		if (coset.size() > 0)
		{
			if (cand.size() > 0)
			{
				z3 = "(and " + z3 + " ";
			}
			z3 += coset.stream().map(c -> "(and (not (and (>= self " + c.start.toSmt2Formula() + ") (<= self " + c.end.toSmt2Formula() + ")))"
						+ ((!c.start.isConstant() || !c.end.isConstant()) ? " (<= " + c.start.toSmt2Formula() + " " + c.end.toSmt2Formula() + ")" : "")
						+ ")")
					.reduce((c1, c2) -> "(and " + c1 + " " + c2 +")").get();
			if (cand.size() > 0)
			{
				z3 += ")";
			}
		}

		z3 = "(exists ((self Int))" + z3 + ")";

		return z3;
	}

	// FIXME: factor out -- cf. super.doAttemptableOutputTasks
	@Override
	protected void tryOutputTasks(Job job) throws CommandLineException, ScribbleException
	{
		if (this.rpArgs.containsKey(RPCoreCLArgFlag.RPCORE_EFSM))
		{
			String[] args = this.rpArgs.get(RPCoreCLArgFlag.RPCORE_EFSM);
			for (int i = 0; i < args.length; i += 1)
			{
				Role role = CommandLine.checkRoleArg(job.getContext(), gpd.getHeader().getDeclName(), args[i]);
				this.E0.get(role).entrySet().forEach(e ->
				{
					String out = e.getValue().toDot();
					System.out.println("\nEndpoint FSM for " + e.getKey() + ":\n" + out);  // Endpoint graphs are "inlined" (a single graph is built)
				});
			}
		}
		if (this.rpArgs.containsKey(RPCoreCLArgFlag.RPCORE_EFSM_PNG))
		{
			String[] args = this.rpArgs.get(RPCoreCLArgFlag.RPCORE_EFSM_PNG);
			for (int i = 0; i < args.length; i += 2)
			{
				Role role = CommandLine.checkRoleArg(job.getContext(), gpd.getHeader().getDeclName(), args[i]);
				String png = args[i+1];
				//for (Entry<Set<ParamRange>, EGraph> e : this.E0.get(role).entrySet())
				for (Entry<RPRoleVariant, EGraph> e : this.E0.get(role).entrySet())
				{
					String out = e.getValue().init.toDot();
					runDot(out, png);
				}
			}
		}
		/*if (this.paramArgs.containsKey(ParamCLArgFlag.PARAM_CORE_MODEL))
		{
			System.out.println("\n" + model.toDot());
		}
		if (this.paramArgs.containsKey(ParamCLArgFlag.PARAM_CORE_MODEL_PNG))
		{
			String[] arg = this.paramArgs.get(ParamCLArgFlag.PARAM_CORE_MODEL_PNG);
			String png = arg[0];
			runDot(model.toDot(), png);
		}*/
	}

	/*private void assrtCoreValidate(Job job, GProtocolName simpname, boolean isExplicit, 
			//Map<Role, ParamEState> E0,
			boolean... unfair) throws ScribbleException, CommandLineException
	{
		this.model = new ParamCoreSModelBuilder(job.sf).build(this.E0, isExplicit);

		job.debugPrintln("\n[rp-core] Built model:\n" + this.model.toDot());
		
		if (unfair.length == 0 || !unfair[0])
		{
			ParamCoreSafetyErrors serrs = this.model.getSafetyErrors(job, simpname);  // job just for debug printing
			if (serrs.isSafe())
			{
				job.debugPrintln("\n[rp-core] Protocol safe.");
			}
			else
			{
				throw new ParamException("[rp-core] Protocol not safe:\n" + serrs);
			}
		}
		
		/*F17ProgressErrors perrs = m.getProgressErrors();
		if (perrs.satisfiesProgress())
		{
			job.debugPrintln
			//System.out.println
					("\n[f17] " + ((unfair.length == 0) ? "Fair protocol" : "Protocol") + " satisfies progress.");
		}
		else
		{

			// FIXME: refactor eventual reception as 1-bounded stable check
			Set<F17SState> staberrs = m.getStableErrors();
			if (perrs.eventualReception.isEmpty())
			{
				if (!staberrs.isEmpty())
				{
					throw new RuntimeException("[f17] 1-stable check failure: " + staberrs);
				}
			}
			else
			{
				if (staberrs.isEmpty())
				{
					throw new RuntimeException("[f17] 1-stable check failure: " + perrs);
				}
			}
			
			throw new F17Exception("\n[f17] " + ((unfair.length == 0) ? "Fair protocol" : "Protocol") + " violates progress.\n" + perrs);
		}* /
	}*/
}
