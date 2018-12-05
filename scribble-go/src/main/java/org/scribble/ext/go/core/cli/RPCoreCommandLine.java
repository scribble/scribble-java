package org.scribble.ext.go.core.cli;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenerator;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenerator.Mode;
import org.scribble.ext.go.core.main.RPCoreException;
import org.scribble.ext.go.core.main.RPCoreMainContext;
import org.scribble.ext.go.core.model.endpoint.RPCoreEGraphBuilder;
import org.scribble.ext.go.core.model.endpoint.RPCoreEModelFactory;
import org.scribble.ext.go.core.model.endpoint.RPCoreEState;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEAction;
import org.scribble.ext.go.core.type.RPFamily;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.ext.go.util.IntPairSmt2Translator;
import org.scribble.ext.go.util.IntSmt2Translator;
import org.scribble.ext.go.util.RecursiveFunctionalInterface;
import org.scribble.ext.go.util.Smt2Translator;
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
import org.scribble.util.ScribParserException;
import org.scribble.util.ScribUtil;

// N.B. this is the CL for both -goapi and rp-core extensions
public class RPCoreCommandLine extends CommandLine
{
	protected final Map<RPCoreCLArgFlag, String[]> rpArgs;  // Maps each flag to list of associated argument values

	// HACK: store in (Core) Job/JobContext?
	private GProtocolDecl gpd;
	private RPCoreGType gt;
	private Smt2Translator smt2t;
	private Map<Role, Map<RPRoleVariant, RPCoreLType>> L0;
	private Map<Role, Map<RPRoleVariant, EGraph>> E0;
	//protected ParamCoreSModel model;

	//private Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> families;  // Factor out Family (cf. RPRoleVariant)
	private Set<RPFamily> families;
		// families *after* subsumption ("minimisation")

	//private Map<RPRoleVariant, Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, Set<RPRoleVariant>>> peers;
	private Map<RPRoleVariant, Map<RPFamily, Set<RPRoleVariant>>>  peers;
		// variant-from-an-original-family -> an-original-family -> peer-variants-in-that-family  // peer-variants is a subset of original-family variants
	
	//private Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> subsum;
	private Map<RPFamily, RPFamily> subsum;
			// new-family-after-subsumptions -> original-family-before-subsumptions

	//private Map<RPRoleVariant, Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, RPRoleVariant>> aliases;
	private Map<RPRoleVariant, Map<RPFamily, RPRoleVariant>> aliases;
			// subsumed-variant -> original-family-subsumed-in -> subsuming-variant 
			// FIXME: can a variant be subsumed by mutiple other variants?  currently only recording one
	
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
		boolean parForeach = this.rpArgs.containsKey(RPCoreCLArgFlag.RPCORE_PARFOREACH);
		boolean dotApi = this.rpArgs.containsKey(RPCoreCLArgFlag.RPCORE_DOTAPI);
		
		if (selectApi || noCopy || (parForeach && dotApi))
		{
			throw new RuntimeException("TODO: ");
		}

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
					noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, noCopy, selectApi, parForeach, dotApi);
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
			
			Mode mode;
			if (this.smt2t instanceof IntSmt2Translator)
			{
				mode = Mode.Int;
			}
			else if (this.smt2t instanceof IntPairSmt2Translator)
			{
				mode = Mode.IntPair;
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + this.smt2t.getClass());
			}
			Map<String, String> goClasses = new RPCoreSTApiGenerator(gjob, fullname, 
					this.L0, this.E0, this.families, this.peers, this.subsum, this.aliases, impath, roles, mode, this.smt2t).build();
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
			paramCoreParseAndCheckWFAll(j);  // Includes base passes
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
	private void paramCoreParseAndCheckWFAll(GoJob job) throws RPCoreSyntaxException, ScribbleException, ScribParserException, CommandLineException
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

		this.gt = parse(job, af, simpname);

		this.smt2t = Z3Wrapper.getSmt2Translator(job, this.gpd, this.gt);
		
		if (!checkWF(job))
		{
			throw new RPCoreException("[rp-core] Global type not well-formed:\n  " + this.gt);
		}

		//Map<Role, Set<Set<ParamRange>>> 
		Map<Role, Set<RPRoleVariant>> variants = getVariants(job, gt, smt2t);
		
		job.debugPrintln("\n[rp-core] Computed roles: " + variants);

		boolean compactSingletonIvals = true;
		this.L0 = project(job, af, variants, compactSingletonIvals);

		this.E0 = generateFSMs(job);
				
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
		
		this.families = getFamilies(job, smt2t);
		this.peers = getPeers(job, smt2t);
		
		compactFamilies(af);
	}

	private Map<Role, Map<RPRoleVariant, EGraph>> generateFSMs(GoJob job)
	{
		RPCoreEGraphBuilder builder = new RPCoreEGraphBuilder(job);
		Map<Role, Map<RPRoleVariant, EGraph>> E0 = new HashMap<>();

		for (Role r : (Iterable<Role>) this.L0.keySet().stream().sorted(  // For consistent state numbering
					new Comparator<Role>() {
						@Override
						public int compare(Role o1, Role o2) {
							return o1.toString().compareTo(o2.toString());
						}
					}				
				)::iterator)
		{
			//for (Set<ParamRange> ranges : this.P0.get(r).keySet())
			for (RPRoleVariant variant : (Iterable<RPRoleVariant>) this.L0.get(r).keySet().stream().sorted(
						new Comparator<RPRoleVariant>() {
							@Override
							public int compare(RPRoleVariant o1, RPRoleVariant o2) {
								return o1.toString().compareTo(o2.toString());
							}
						}				
					)::iterator)
			{
				
				//System.out.println("\nProjection onto " + variant + ": " + this.L0.get(r).get(variant));
				
				EGraph g = builder.build(this.L0.get(r).get(variant));
				//Map<Set<ParamRange>, EGraph> tmp = this.E0.get(r);
				Map<RPRoleVariant, EGraph> tmp = E0.get(r);
				if (tmp == null)
				{
					tmp = new HashMap<>();
					E0.put(r, tmp);
				}
				tmp.put(variant, g);

				job.debugPrintln("\n[rp-core] Built endpoint graph for " 
						//+ r + " for "
						+ variant + ":\n" + g.toDot());
				
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
		
		return E0;
	}

	public Map<Role, Map<RPRoleVariant, RPCoreLType>> project(GoJob job, RPCoreAstFactory af, Map<Role, Set<RPRoleVariant>> variants, boolean compact) throws RPCoreSyntaxException
	{
		Map<Role, Map<RPRoleVariant, RPCoreLType>> L0 = new HashMap<>();
		for (Role r : this.gpd.header.roledecls.getRoles())  // getRoles gives decl names  // CHECKME: can ignore params?
		{
			//for (Set<ParamRange> ranges : protoRoles.get(r))
			for (RPRoleVariant variant : variants.get(r))
			{
				//ParamCoreLType lt = gt.project(af, r, ranges);
				RPCoreLType lt = this.gt.project(af, variant, this.smt2t);
				
				if (compact)
				{
					lt = lt.compactSingletonIvals(af, variant);
				}
				
				//Map<Set<ParamRange>, ParamCoreLType> tmp = P0.get(r);
				Map<RPRoleVariant, RPCoreLType> tmp = L0.get(r);
				if (tmp == null)
				{
					tmp = new HashMap<>();
					L0.put(r, tmp);
				}
				tmp.put(variant, lt);

				job.debugPrintln("\n[rp-core] Projected onto " + variant + ":\n  " + lt);
			}
		}

		return L0;
	}

	private boolean checkWF(GoJob job) throws RPCoreException
	{
		return this.gt.isWellFormed(job, new Stack<>(), this.gpd, this.smt2t);
	}

	private RPCoreGType parse(GoJob job, RPCoreAstFactory af, GProtocolName simpname) throws RPCoreException, RPCoreSyntaxException
	{
		RPCoreGType gt = new RPCoreGProtocolDeclTranslator(job, af).translate(this.gpd);
		
		job.debugPrintln("\n[rp-core] Translated:\n  " + gt);
		
		return gt;
	}
	
	private void compactFamilies(RPCoreAstFactory af)
	{
		this.subsum = new HashMap<>();
		this.aliases = new HashMap<>();
		
	  //Map<Role, Map<RPRoleVariant, RPCoreLType>> L0 = 
		//Map<Role, Map<RPRoleVariant, EGraph>> E0;
		/*Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> families
		= this.families.stream().map(
				p -> new Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>(new HashSet<>(p.left), new HashSet<>(p.right))
				).collect(Collectors.toSet());*/
		
		/*Map<RPRoleVariant, Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, Set<RPRoleVariant>>> peers
		= 
					this.peers.entrySet().stream().collect(Collectors.toMap(
							e -> e.getKey(),
							e -> e.getValue().entrySet().stream().collect(Collectors.toMap(
									ee -> ee.getKey(), 
									ee -> new HashSet<>(ee.getValue())
								))
					));*/

		//Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> families = new HashSet<>();
		Set<RPFamily> families = new HashSet<>();

		////Map<RPRoleVariant, Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, Set<RPRoleVariant>>> peers = new HashMap<>();
		//for (Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> fam : this.families)
		for (RPFamily fam : this.families)
		{
			Set<RPRoleVariant> tmp = new HashSet<>();
			//Set<RPRoleVariant> vs = fam.left;
			Set<RPRoleVariant> vs = fam.variants;
			Next: for (RPRoleVariant v : vs)
			{
				if (!v.isSingleton())
				{
					tmp.add(v);
				}
				else
				{
					for (RPRoleVariant u : vs.stream().filter(x -> x.getName().equals(v.getName())).collect(Collectors.toList()))
					{
						if (!u.equals(v))
						{
							RPCoreLType vL = this.L0.get(v.getName()).get(v);
							RPCoreLType uL = this.L0.get(u.getName()).get(u);

							//System.out.println("4444: " + v + " \n " + vL + " ,, " + vL.minimise(af, v) + " \n " + u + " ,, " + uL + " ,, " + uL.minimise(af, v) + "\n");
							if (vL.compactSingletonIvals(af, v).equals(uL.compactSingletonIvals(af, v)))
							{
								//System.out.println("5555: " + v + "  subsumed by  " + u);
								
								//Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, RPRoleVariant> m = this.aliases.get(v);
								Map<RPFamily, RPRoleVariant> m = this.aliases.get(v);
								if (m == null)
								{
									m = new HashMap<>();
									this.aliases.put(v, m);
								}
								m.put(fam, u);  // FIXME: can a variant be subsumed by mutiple other variants?  currently only recording one
								
								continue Next;
							}
						}
					}
					tmp.add(v);
				}
			}
			
			//if (!tmp.equals(fam.left))
			{
				//Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> compressed = new Pair<>(tmp, fam.right);

				//RPFamily compressed = new RPFamily(tmp, fam.right);
				RPFamily compressed = new RPFamily(tmp, fam.covariants);
				families.add(compressed);  // Subsumed variants are neither in left nor right

				/*System.out.println("\nFamily:\n" + fam.variants.stream().map(x -> x.toString()).collect(Collectors.joining("\n"))
						+ "\nCovars:\n" + fam.covariants.stream().map(x -> x.toString()).collect(Collectors.joining("\n"))
						+ "\nCompacted:\n" + tmp.stream().map(x -> x.toString()).collect(Collectors.joining("\n")));*/
					
				//if (!tmp.equals(fam.left))
				if (!tmp.equals(fam.variants))
				{
					this.subsum.put(compressed, fam);
				}
			}
		}
		
		this.families = families;
	}

	// ..FIXME: generalise to multirole processes?  i.e. all roles are A with different indices? -- also subsumes MP with single rolename?
	
	//..HERE FIXME ActualParam -- ParamRange is now already a Set
	
	
	// Compute variant peers of each variant -- which peer variants (possibly self variant) can match the indexed-role peers of self's I/O actions
	// for "common" endpoint kind factoring (w.r.t. dial/accept, i.e., to look for variants whose set of dial/accept methods is the same for all families it is involved in)
	// FIXME: optimise, peers are symmetric
	private //Map<RPRoleVariant, Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, Set<RPRoleVariant>>> 
			Map<RPRoleVariant, Map<RPFamily, Set<RPRoleVariant>>>
			getPeers(GoJob job, Smt2Translator smt2t)
	{
		job.debugPrintln("\n[rp-core] Computing peers:");

		//Map<RPRoleVariant, Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, Set<RPRoleVariant>>> 
		Map<RPRoleVariant, Map<RPFamily, Set<RPRoleVariant>>>
				res = new HashMap<>();

		/*String[] args = this.rpArgs.get(RPCoreCLArgFlag.RPCORE_API_GEN);
		for (Role rname : (Iterable<Role>) Arrays.asList(args).stream().map(r -> new Role(r))::iterator)*/
		for (Role rname : this.gpd.header.roledecls.getRoles())
		{
			for (RPRoleVariant self : this.E0.get(rname).keySet()) 
			{
				/*for (Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family :
						(Iterable<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>>) 
								this.families.keySet().stream().filter(f -> f.left.contains(variant))::iterator)  // FIXME: use family to make accept/dial
				{*/
					// CHECKME: use local types instead of FSMs?  (RPCoreGForeach#getIndexedRoles)
					Set<RPIndexedRole> actionIRs = //MState.getReachableActions
							RPCoreEState.getReachableActions((RPCoreEModelFactory) job.ef,
									(RPCoreEState) this.E0.get(rname).get(self).init).stream()  // FIXME: static "overloading" (cf. MState) error prone
							.map(a -> ((RPCoreEAction) a).getPeer()).collect(Collectors.toSet());
									// CHECKME: in presence of foreach, actions will include unqualified foreachvars -- is that what the following Z3 assertion is/should be doing?
					//*/

					Set<RPRoleVariant> peers = new HashSet<>();
					next: 
					for (RPRoleVariant peerVariant : (Iterable<RPRoleVariant>)  // Candidate
							this.E0.values().stream()
									.flatMap(m -> m.keySet().stream())::iterator)
					{
						if (//!peerVariant.equals(self) &&   // No: e.g., pipe/ring middlemen
								!peers.contains(peerVariant))
						{
							/*if (((RPCoreEState) this.E0.get(rname).get(self).init).isPeer(smt2t, self, peerVariant))
							{
								peers.add(peerVariant);
								//continue next;
							}*/

							//*
							job.debugPrintln("\n[rp-core] For " + self + ", checking peer candidate: " + peerVariant);
									// "checking potential peer" means checking if any of our action-peer indexed roles fits the candidate variant-peer (so we would need a dial/accept for them)
							
							for (RPIndexedRole ir : actionIRs)
							{
								//if (ir.isPotentialPeer(smt2t, self, peerVariant))
								if (self.isPotentialPeer(smt2t, ir, peerVariant))
								{
										peers.add(peerVariant);
										continue next;
								}

								/*if (ir.getName().equals(peerVariant.getName()))
								{
									if (ir.intervals.size() > 1)
									{
										throw new RuntimeException("[rp-core] TODO: multi-dimension intervals: " + ir);  // No?  Multiple intervals is not actually about multidim intervals, it's about constraint intersection?  (A multdim interval should be a single interval value?)
									}
									RPInterval d = ir.intervals.stream().findAny().get();
									Set<RPIndexVar> vars = Stream.concat(peerVariant.intervals.stream().flatMap(x -> x.getIndexVars().stream()), peerVariant.cointervals.stream().flatMap(x -> x.getIndexVars().stream()))
											.collect(Collectors.toSet());
									vars.addAll(ir.getIndexVars());

									/*String smt2 = "(assert ";
									smt2 += "(exists ((peer Int) "
											+  (vars.isEmpty() ? "" : vars.stream()
													.map(x -> "(" + x + " Int)").collect(Collectors.joining(" "))) + ")\n";
									smt2 += "(and \n";
									smt2 += vars.stream().map(x -> "(>= " + x + " 1)").collect(Collectors.joining(" ")) + "\n";  // FIXME: generalise, parameter domain annotations

									smt2 += peerVariant.intervals.stream()
											.map(x -> "(>= peer " + x.start.toSmt2Formula() + ") (<= peer " + x.end.toSmt2Formula() + ")")
													// Is there a peer index inside all the peer-variant intervals
											.collect(Collectors.joining(" ")) + "\n";
									smt2 += peerVariant.cointervals.isEmpty() 
											? ""
											: "(or " + peerVariant.cointervals.stream()
											    .map(x -> "(< peer " + x.start.toSmt2Formula() + ") (> peer " + x.end.toSmt2Formula() + ")")
											    .collect(Collectors.joining(" ")) + ")\n";
													// ...and the peer index is outside one of the peer-variant cointervals
									smt2 += "(>= peer " + d.start.toSmt2Formula() + ") (<= peer " + d.end.toSmt2Formula() + ")\n";
													// ...and the peer index is inside our I/O action interval -- then this is peer-variant is a peer

									if (vars.contains(RPIndexSelf.SELF))
									{
										// FIXME: factor out variant/covariant inclusion/exclusion with above
										smt2 += self.intervals.stream()
												.map(x -> "(>= self " + x.start.toSmt2Formula() + ") (<= self " + x.end.toSmt2Formula() + ")")
														// Is there a self index inside all the self-variant intervals
												.collect(Collectors.joining(" ")) + "\n";
										smt2 += self.cointervals.isEmpty() 
												? ""
												: "(or " + self.cointervals.stream()
														.map(x -> "(< self " + x.start.toSmt2Formula() + ") (> self " + x.end.toSmt2Formula() + ")")
														.collect(Collectors.joining(" ")) + ")\n";
														// ...and the self index is outside one of the self-variant cointervals
									}

									smt2 += ")";
									smt2 += ")";
									smt2 += ")";
									* /
									
									// CHECKME: need to further constrain K? (by family?)
									
									List<String> cs = new LinkedList<>();
									cs.addAll(vars.stream().map(x -> smt2t.makeGte(x.toSmt2Formula(smt2t), smt2t.getDefaultBaseValue())).collect(Collectors.toList()));  // FIXME: generalise, parameter domain annotations
									
									if (this.gpd.header instanceof RPGProtocolHeader)
									{
										// FIXME: WIP
										RPAnnotExpr annot = RPAnnotExpr.parse(((RPGProtocolHeader) this.gpd.header).annot);
										if (annot.getVars().stream().map(x -> x.toString()).allMatch(x ->
												vars.stream().map(y -> y.toString()).anyMatch(y -> x.equals(y))))  // TODO: refactor
										{
											cs.add(annot.toSmt2Formula(smt2t));
										}
									}
									
									for (RPInterval ival : peerVariant.intervals)  // Is there a peer index inside all the peer-variant intervals
									{
										cs.add(smt2t.makeGte("peer", ival.start.toSmt2Formula(smt2t)));
										cs.add(smt2t.makeLte("peer", ival.end.toSmt2Formula(smt2t)));
									}
									if (!peerVariant.cointervals.isEmpty())
									{
										// ...and the peer index is outside one of the peer-variant cointervals
										/*cs.add(smt2t.makeOr(
											self.cointervals.stream().flatMap(x ->
													Stream.of(smt2t.makeLt("peer", x.start.toSmt2Formula(smt2t)), smt2t.makeGt("peer", x.end.toSmt2Formula(smt2t)))
											).collect(Collectors.toList())));
											* /
										cs.addAll(peerVariant.cointervals.stream().map(x ->
													smt2t.makeOr(smt2t.makeLt("peer", x.start.toSmt2Formula(smt2t)), smt2t.makeGt("peer", x.end.toSmt2Formula(smt2t)))
										).collect(Collectors.toList()));
									}
									// ...and the peer index is inside our I/O action interval -- then this is peer-variant is a peer
									cs.add(smt2t.makeGte("peer", d.start.toSmt2Formula(smt2t)));
									cs.add(smt2t.makeLte("peer", d.end.toSmt2Formula(smt2t)));

									if (vars.contains(RPIndexSelf.SELF))
									{
										// If self name is peer name, peer index is not self index
										if (peerVariant.getName().equals(self.getName()))
										{
											cs.add(smt2t.makeNot(smt2t.makeEq("peer", "self")));
										}
										// TODO: factor out variant/covariant inclusion/exclusion with above
										for (RPInterval ival : self.intervals)  // Is there a self index inside all the self-variant intervals
										{
											cs.add(smt2t.makeGte("self", ival.start.toSmt2Formula(smt2t)));
											cs.add(smt2t.makeLte("self", ival.end.toSmt2Formula(smt2t)));
										}
										if (!self.cointervals.isEmpty())
										{
											// ...and the self index is outside one of the self-variant cointervals
											/*cs.add(smt2t.makeOr(
												self.cointervals.stream().flatMap(x ->
														Stream.of(smt2t.makeLt("self", x.start.toSmt2Formula(smt2t)), smt2t.makeGt("self", x.end.toSmt2Formula(smt2t)))
												).collect(Collectors.toList())));
												* /
											cs.addAll(self.cointervals.stream().map(x ->
													smt2t.makeOr(smt2t.makeLt("self", x.start.toSmt2Formula(smt2t)), smt2t.makeGt("self", x.end.toSmt2Formula(smt2t)))
											).collect(Collectors.toList()));
										}
									}

									String smt2 = smt2t.makeAnd(cs);
									List<String> tmp = new LinkedList<>();
									tmp.add("peer");
									tmp.addAll(vars.stream().map(x -> x.toSmt2Formula(smt2t)).collect(Collectors.toList()));
									smt2 = smt2t.makeExists(tmp, smt2); 
									smt2 = smt2t.makeAssert(smt2);
									
									job.debugPrintln("[rp-core] Running Z3 on " + d + " :\n" + smt2);
									
									boolean isSat = Z3Wrapper.checkSat(job, this.gpd, smt2);
									job.debugPrintln("[rp-core] Checked sat: " + isSat);
									if (isSat)
									{
										peers.add(peerVariant);
										continue next;
									}
								}
								//*/
							}
							//*/
						}
					}

					//Map<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>, Set<RPRoleVariant>> 
					Map<RPFamily, Set<RPRoleVariant>>
							tmp = new HashMap<>();
					res.put(self, tmp);
					//for (Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> fam : this.families)
					for (RPFamily fam : this.families)
					{
						//if (fam.left.contains(self))
						{
							Set<RPRoleVariant> tmp2 = new HashSet<>(peers);
							//tmp2.retainAll(fam.left);
							tmp2.retainAll(fam.variants);
							tmp.put(fam, tmp2);
						}
					}
				//}
					
			}
		}

		return res;
	}	
	
	private //Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> 
			Set<RPFamily>
			getFamilies(GoJob job, Smt2Translator smt2t)
	{
		job.debugPrintln("\n[rp-core] Computing families:");

		//Set<Pair<Set<RPRoleVariant>, Set<RPRoleVariant>>> fams = new HashSet<>();
		Set<RPFamily> fams = new HashSet<>();

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
			
			RPFamily test = new RPFamily(cand, coset);
			if (test.isValid(smt2t, vars))
			{
				fams.add(test);
			}
		}
		
		return fams;
	}

	/*public static String makeFamilyCheck(Smt2Translator smt2t, Set<RPIndexVar> vars, Set<RPRoleVariant> cand, Set<RPRoleVariant> coset)
	{
		List<String> cs = new LinkedList<>();
		cs.addAll(vars.stream().map(x -> smt2t.makeGte(x.toSmt2Formula(smt2t), smt2t.getDefaultBaseValue())).collect(Collectors.toList()));  // FIXME: generalise, parameter domain annotations
		/*cs.addAll(cand.stream().map(v -> makePhiSmt2(v.intervals, v.cointervals, smt2t, false)).collect(Collectors.toList()));
		cs.addAll(coset.stream().map(v -> makePhiSmt2(v.intervals, v.cointervals, smt2t, true)).collect(Collectors.toList()));* /
		cs.addAll(cand.stream().map(v -> v.makePhiSmt2(smt2t, false)).collect(Collectors.toList()));
		cs.addAll(coset.stream().map(v -> v.makePhiSmt2(smt2t, true)).collect(Collectors.toList()));

		if (!vars.isEmpty())
		{
			if (smt2t.global.header instanceof RPGProtocolHeader)
			{
				// FIXME: WIP
				RPAnnotExpr annot = RPAnnotExpr.parse(((RPGProtocolHeader) smt2t.global.header).annot);
				if (annot.getVars().stream().map(x -> x.toString()).allMatch(x ->
						vars.stream().map(y -> y.toString()).anyMatch(y -> x.equals(y))))  // TODO: refactor
				{
					cs.add(annot.toSmt2Formula(smt2t));
				}
			}
		}

		String smt2 = smt2t.makeAnd(cs);
		if (!vars.isEmpty())
		{
			smt2 = smt2t.makeExists(vars.stream().map(x -> x.toSmt2Formula(smt2t)).collect(Collectors.toList()), smt2); 
		}
		smt2 = smt2t.makeAssert(smt2);
		return smt2;
	}*/
	
	private //Map<Role, Set<Set<ParamRange>>> 
			Map<Role, Set<RPRoleVariant>>
			getVariants(GoJob job, RPCoreGType gt, Smt2Translator smt2t)
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
				
				RPRoleVariant test = new RPRoleVariant(r.toString(), cand, coset);

				if (test.isValid(smt2t))
				{
					variants.get(r).add(test);
				}
				
				/*String z3 = makePhiSmt2(cand, coset, smt2t, false);
				List<RPIndexVar> vars = Stream.concat(
							cand.stream().flatMap(c -> c.getIndexVars().stream()),
							coset.stream().flatMap(c -> c.getIndexVars().stream())
						).distinct().collect(Collectors.toList());
				if (!vars.isEmpty())
				{
					//z3 = "(exists (" + vars.stream().map(p -> "(" + p + " Int)").collect(Collectors.joining(" ")) + ") " + z3 + ")";
					List<String> tmp = vars.stream().map(v -> smt2t.makeLte(smt2t.getDefaultBaseValue(), v.toSmt2Formula(smt2t))).collect(Collectors.toList());

					if (this.gpd.header instanceof RPGProtocolHeader)
					{
						// FIXME: WIP
						RPAnnotExpr annot = RPAnnotExpr.parse(((RPGProtocolHeader) this.gpd.header).annot);
						if (annot.getVars().stream().map(x -> x.toString()).allMatch(x ->
								vars.stream().map(y -> y.toString()).anyMatch(y -> x.equals(y))))  // TODO: refactor
						{
							tmp.add(annot.toSmt2Formula(smt2t));
						}
					}

					tmp.add(z3);
					z3 = smt2t.makeAnd(tmp);
					z3 = smt2t.makeExists(vars.stream().map(v -> v.toSmt2Formula(smt2t)).collect(Collectors.toList()), z3);
				}
				//z3 = smt2t.makeExists(Stream.of("self").collect(Collectors.toList()), z3);
				z3 = smt2t.makeAssert(z3);
				
				job.debugPrintln("\n[rp-core] Variant candidate (" + i++ + "/" + size + "): " + cand);
				job.debugPrintln("[rp-core] Co-set: " + coset);
				job.debugPrintln("[rp-core] Running Z3 on:\n" + z3);
				
				boolean isSat = Z3Wrapper.checkSat(job, this.gpd, z3);
				if (isSat)
				{
					//protoRoles.get(r).add(cand);
					variants.get(r).add(new RPRoleVariant(r.toString(), cand, coset));
				}
				job.debugPrintln("[rp-core] Checked sat: " + isSat);*/
			}
		}
		
		return variants;
	}

	/*// Doesn't include "assert", nor exists-bind index vars (but does exists-bind self)
	private static String makePhiSmt2(Set<RPInterval> cand, Set<RPInterval> coset, Smt2Translator smt2t, boolean not)
	{
		List<String> cs = new LinkedList<>();
		List<String> dom = new LinkedList<>();

		//if (cand.size() > 0)
		{
			//z3 += smt2t.makeAnd(
			cs.addAll(
				cand.stream().map(c -> 
					 //"(and (>= self " + c.start.toSmt2Formula() + ") (<= self " + c.end.toSmt2Formula() + ")" + ((!c.start.isConstant() || !c.end.isConstant()) ? " (<= " + c.start.toSmt2Formula() + " " + c.end.toSmt2Formula() + ")" : "") + ")"
					{
						List<String> tmp = new LinkedList<>();
						tmp.add(smt2t.makeGte("self", c.start.toSmt2Formula(smt2t)));
						tmp.add(smt2t.makeLte("self", c.end.toSmt2Formula(smt2t)));
						if (!c.start.isConstant() || !c.end.isConstant())
						{
							dom.add(smt2t.makeLte(c.start.toSmt2Formula(smt2t), c.end.toSmt2Formula(smt2t)));
						}
						return smt2t.makeAnd(tmp);
					})
				//.reduce((c1, c2) -> "(and " + c1 + " " + c2 +")").get();
				.collect(Collectors.toList())
			);
		}

		/*if (coset.size() > 0)
		{
			if (cand.size() > 0)
			{
				z3 = "(and " + z3 + " ";
			}* /
			cs.addAll(
				coset.stream().map(c -> 
				//z3 += coset.stream().map(c -> "(and (not (and (>= self " + c.start.toSmt2Formula() + ") (<= self " + c.end.toSmt2Formula() + ")))" + ((!c.start.isConstant() || !c.end.isConstant()) ? " (<= " + c.start.toSmt2Formula() + " " + c.end.toSmt2Formula() + ")" : "") + ")")
					{
						List<String> tmp = new LinkedList<>();
						//tmp.add(smt2t.makeOr(smt2t.makeLt("self", c.start.toSmt2Formula()), smt2t.makeGt("self", c.end.toSmt2Formula())));
						tmp.add(smt2t.makeNot(smt2t.makeAnd(smt2t.makeGte("self", c.start.toSmt2Formula(smt2t)), smt2t.makeLte("self", c.end.toSmt2Formula(smt2t)))));
						if (!c.start.isConstant() || !c.end.isConstant())
						{
							dom.add(smt2t.makeLte(c.start.toSmt2Formula(smt2t), c.end.toSmt2Formula(smt2t)));  // Must be outside the not (if one)
						}
						return (tmp.size() == 1) ? tmp.get(0) : smt2t.makeAnd(tmp); 
					})
				//.reduce((c1, c2) -> "(and " + c1 + " " + c2 +")").get();
				.collect(Collectors.toList())
				
				 //(and (not (and (and (>= self 1) (<= self K))) (<= 1 K)))
			);
			/*if (cand.size() > 0)
			{
				z3 += ")";
			}
		}* /

		String z3 = //"(exists ((self Int))" + z3 + ")";
				smt2t.makeExists(Stream.of("self").collect(Collectors.toList()), smt2t.makeAnd(cs));  // CHECKME: need explicit self >= 1?
		if (not)
		{
			z3 = smt2t.makeNot(z3);
		}
		dom.add(z3);
		z3 = smt2t.makeAnd(dom);

		return z3;
	}*/

	// FIXME: factor out -- cf. super.doAttemptableOutputTasks
	@Override
	protected void tryOutputTasks(Job job) throws CommandLineException, ScribbleException
	{
		if (this.rpArgs.containsKey(RPCoreCLArgFlag.RPCORE_EFSM))
		{
			String[] args = this.rpArgs.get(RPCoreCLArgFlag.RPCORE_EFSM);
			for (int i = 0; i < args.length; i += 1)
			{
				Role role = CommandLine.checkRoleArg(job.getContext(), this.gpd.getHeader().getDeclName(), args[i]);
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
				Role role = CommandLine.checkRoleArg(job.getContext(), this.gpd.getHeader().getDeclName(), args[i]);
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
