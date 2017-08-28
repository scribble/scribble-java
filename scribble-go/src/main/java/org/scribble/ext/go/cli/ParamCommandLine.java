package org.scribble.ext.go.cli;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.cli.CLArgFlag;
import org.scribble.cli.CommandLine;
import org.scribble.cli.CommandLineException;
import org.scribble.ext.go.codegen.statetype.go.GoEndpointApiGenerator;
import org.scribble.ext.go.core.ast.ParamCoreAstFactory;
import org.scribble.ext.go.core.ast.ParamCoreSyntaxException;
import org.scribble.ext.go.core.ast.global.ParamCoreGProtocolDeclTranslator;
import org.scribble.ext.go.core.ast.global.ParamCoreGType;
import org.scribble.ext.go.core.ast.local.ParamCoreLType;
import org.scribble.ext.go.core.codegen.statetype.ParamCoreSTEndpointApiGenerator;
import org.scribble.ext.go.core.model.endpoint.ParamCoreEGraphBuilder;
import org.scribble.ext.go.core.type.ParamActualRole;
import org.scribble.ext.go.core.type.ParamRange;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.ext.go.main.ParamException;
import org.scribble.ext.go.main.ParamJob;
import org.scribble.ext.go.main.ParamMainContext;
import org.scribble.ext.go.type.index.ParamIndexVar;
import org.scribble.ext.go.util.Z3Wrapper;
import org.scribble.main.AntlrSourceException;
import org.scribble.main.Job;
import org.scribble.main.JobContext;
import org.scribble.main.ScribbleException;
import org.scribble.main.resource.DirectoryResourceLocator;
import org.scribble.main.resource.ResourceLocator;
import org.scribble.model.endpoint.EGraph;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;
import org.scribble.util.ScribParserException;

public class ParamCommandLine extends CommandLine
{
	protected final Map<ParamCLArgFlag, String[]> paramArgs;  // Maps each flag to list of associated argument values

	// HACK: store in (Core) Job/JobContext?
	protected GProtocolDecl gpd;
	//protected Map<Role, Map<Set<ParamRange>, ParamCoreLType>> P0;
	protected Map<Role, Map<ParamActualRole, ParamCoreLType>> P0;
	//protected Map<Role, Map<Set<ParamRange>, EGraph>> E0;  // FIXME: make a "proper role" for param APIs
	protected Map<Role, Map<ParamActualRole, EGraph>> E0;  // FIXME: make a "proper role" for param APIs
	//protected ParamCoreSModel model;
	
	public ParamCommandLine(String... args) throws CommandLineException
	{
		this(new ParamCLArgParser(args));
	}

	private ParamCommandLine(ParamCLArgParser p) throws CommandLineException
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
		this.paramArgs = p.getParamArgs();
	}
	
	@Override
	protected ParamMainContext newMainContext() throws ScribParserException, ScribbleException
	{
		boolean debug = this.args.containsKey(CLArgFlag.VERBOSE);  // TODO: factor out with CommandLine (cf. MainContext fields)
		boolean useOldWF = this.args.containsKey(CLArgFlag.OLD_WF);
		boolean noLiveness = this.args.containsKey(CLArgFlag.NO_LIVENESS);
		boolean minEfsm = this.args.containsKey(CLArgFlag.LTSCONVERT_MIN);
		boolean fair = this.args.containsKey(CLArgFlag.FAIR);
		boolean noLocalChoiceSubjectCheck = this.args.containsKey(CLArgFlag.NO_LOCAL_CHOICE_SUBJECT_CHECK);
		boolean noAcceptCorrelationCheck = this.args.containsKey(CLArgFlag.NO_ACCEPT_CORRELATION_CHECK);
		boolean noValidation = this.args.containsKey(CLArgFlag.NO_VALIDATION);

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
			return new ParamMainContext(debug, locator, mainpath, useOldWF, noLiveness, minEfsm, fair,
					noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation);
		}
	}

	public static void main(String[] args) throws CommandLineException, AntlrSourceException
	{
		new ParamCommandLine(args).run();
	}

	@Override
	protected void doValidationTasks(Job job) throws ParamCoreSyntaxException, AntlrSourceException, ScribParserException, CommandLineException
	{
		if (this.paramArgs.containsKey(ParamCLArgFlag.PARAM))
		{
			doParamCoreValidationTasks((ParamJob) job);
		}
		else
		{
			super.doValidationTasks(job);
		}
	}

	@Override
	protected void doNonAttemptableOutputTasks(Job job) throws ScribbleException, CommandLineException
	{		
		if (this.paramArgs.containsKey(ParamCLArgFlag.GO_API_GEN))
		{
			JobContext jcontext = job.getContext();
			String[] args = this.paramArgs.get(ParamCLArgFlag.GO_API_GEN);
			for (int i = 0; i < args.length; i += 2)
			{
				GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
				Role role = checkRoleArg(jcontext, fullname, args[i+1]);
				Map<String, String> goClasses = new GoEndpointApiGenerator(job).generateGoApi(fullname, role);
				outputClasses(goClasses);
			}
		}
		else if (this.paramArgs.containsKey(ParamCLArgFlag.PARAM_CORE_API_GEN))
		{
			JobContext jcontext = job.getContext();
			String[] args = this.paramArgs.get(ParamCLArgFlag.PARAM_CORE_API_GEN);
			for (int i = 0; i < args.length; i += 1)
			{
				String simpname = this.paramArgs.get(ParamCLArgFlag.PARAM)[0];
				GProtocolName fullname = checkGlobalProtocolArg(jcontext, simpname);
				Role role = checkRoleArg(jcontext, fullname, args[i]);
				//for (Set<ParamRange> ranges : this.P0.get(role).keySet())
				for (ParamActualRole ranges : this.P0.get(role).keySet())
				{
					EGraph efsm = this.E0.get(role).get(ranges);
					Map<String, String> goClasses = new ParamCoreSTEndpointApiGenerator(job).generateGoApi(fullname, ranges, efsm);
					outputClasses(goClasses);
				}
			}
		}
		else
		{
			super.doNonAttemptableOutputTasks(job);
		}
	}

	private void doParamCoreValidationTasks(ParamJob j) throws ParamCoreSyntaxException, ScribbleException, ScribParserException, CommandLineException
	{
		/*if (this.args.containsKey(CLArgFlag.PROJECT))  // HACK
			// modules/f17/src/test/scrib/demo/fase17/AppD.scr in [default] mode bug --- projection/EFSM not properly formed if this if is commented ????
		{

		}*/

		paramCorePreContextBuilding(j);

		GProtocolName simpname = new GProtocolName(this.paramArgs.get(ParamCLArgFlag.PARAM)[0]);
		if (simpname.toString().equals("[ParamCoreAllTest]"))  // HACK: ParamCoreAllTest
		{
			paramCoreParseAndCheckWF(j);  // Includes base passes
		}
		else
		{
			paramCoreParseAndCheckWF(j, simpname);  // Includes base passes
		}
		
		// FIXME? param-core FSM building only used for param-core validation -- output tasks, e.g., -api, will still use default Scribble FSMs
		// -- but the FSMs should be the same? -- no: action assertions treated differently in core than base
	}

	
	// Refactor into Param(Core)Job?
	// Following methods are for assrt-*core*
	
	private void paramCorePreContextBuilding(ParamJob job) throws ScribbleException
	{
		job.runContextBuildingPasses();

		//job.runVisitorPassOnParsedModules(RecRemover.class);  // FIXME: Integrate into main passes?  Do before unfolding? 
				// FIXME: no -- revise to support annots
	}

	// Pre: assrtPreContextBuilding(job)
	private void paramCoreParseAndCheckWF(ParamJob job) throws ParamCoreSyntaxException, ScribbleException, ScribParserException, CommandLineException
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
	private void paramCoreParseAndCheckWF(ParamJob job, GProtocolName simpname) throws ParamCoreSyntaxException, ScribbleException, ScribParserException, CommandLineException
	{
		Module main = job.getContext().getMainModule();
		if (!main.hasProtocolDecl(simpname))
		{
			throw new ParamException("[param-core] Global protocol not found: " + simpname);
		}
		this.gpd = (GProtocolDecl) main.getProtocolDecl(simpname);

		ParamCoreAstFactory af = new ParamCoreAstFactory();
		ParamCoreGType gt = new ParamCoreGProtocolDeclTranslator(job, af).translate(this.gpd);
		
		job.debugPrintln("\n[param-core] Translated:\n  " + gt);
		
		//Map<Role, Set<Set<ParamRange>>> 
		Map<Role, Set<ParamActualRole>> 
				protoRoles = getProtoRoles(job, gt);
		
		job.debugPrintln("\n[param-core] Computed roles: " + protoRoles);

		this.P0 = new HashMap<>();
		for (Role r : gpd.header.roledecls.getRoles())  // getRoles gives decl names  // CHECKME: can ignore params?
		{
			//for (Set<ParamRange> ranges : protoRoles.get(r))
			for (ParamActualRole ranges : protoRoles.get(r))
			{
				//ParamCoreLType lt = gt.project(af, r, ranges);
				ParamCoreLType lt = gt.project(af, ranges);
				//Map<Set<ParamRange>, ParamCoreLType> tmp = P0.get(r);
				Map<ParamActualRole, ParamCoreLType> tmp = P0.get(r);
				if (tmp == null)
				{
					tmp = new HashMap<>();
					P0.put(r, tmp);
				}
				tmp.put(ranges, lt);

				job.debugPrintln("\n[param-core] Projected onto " + r + " for " + ranges + ":\n  " + lt);
			}
		}

		ParamCoreEGraphBuilder builder = new ParamCoreEGraphBuilder(job);
		this.E0 = new HashMap<>();
		for (Role r : P0.keySet())
		{
			//for (Set<ParamRange> ranges : this.P0.get(r).keySet())
			for (ParamActualRole ranges : this.P0.get(r).keySet())
			{
				EGraph g = builder.build(this.P0.get(r).get(ranges));
				//Map<Set<ParamRange>, EGraph> tmp = this.E0.get(r);
				Map<ParamActualRole, EGraph> tmp = this.E0.get(r);
				if (tmp == null)
				{
					tmp = new HashMap<>();
					this.E0.put(r, tmp);
				}
				tmp.put(ranges, g);

				job.debugPrintln("\n[param-core] Built endpoint graph for " 
						//+ r + " for "
						+ ranges + ":\n" + g.toDot());
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
						("\n[param-core] Unfair transform for " + r + ":\n" + u.toDot());
			}
			
			//validate(job, gpd.isExplicitModifier(), U0, true);  //TODO
		}*/
		
		//((ParamJob) job).runF17ProjectionPasses();  // projections not built on demand; cf. models

		//return gt;
	}

	// ..FIXME: generalise to multirole processes?  i.e. all roles are A with different indices? -- also subsumes MP with single rolename?
	
	//..HERE FIXME ActualParam -- ParamRange is now already a Set
	
	private //Map<Role, Set<Set<ParamRange>>> 
			Map<Role, Set<ParamActualRole>>
			getProtoRoles(ParamJob job, ParamCoreGType gt)
	{
		Set<ParamRole> prs = gt.getParamRoles();
		
		Map<Role, Set<ParamRole>> map
				= prs.stream()
				.map(x -> x.getName())
				.distinct()
				.collect(Collectors.toMap(x -> x, x -> prs.stream().filter(y -> y.getName().equals(x)).collect(Collectors.toSet())));
		
		Map<Role, Set<Set<ParamRange>>> powersets = map.keySet().stream().collect(Collectors.toMap(k -> k, k -> new HashSet<>()));
		for (Role n : map.keySet())
		{
			Set<Set<ParamRange>> tmp = powersets.get(n);
			Set<ParamRole> todo = new HashSet<>(map.get(n));
			while (!todo.isEmpty())
			{
				Iterator<ParamRole> i = todo.iterator();
				ParamRole next = i.next();
				i.remove();
				Set<ParamRange> range = new HashSet<>();
				//range.add(next.range);
				range.add(next.ranges.iterator().next());
				if (!tmp.contains(next))
				{
					tmp.addAll(tmp.stream().map(t -> 
					{
						Set<ParamRange> ggg = new HashSet<>();
						ggg.addAll(t);
						ggg.add(range.iterator().next());
						return ggg;
					}).collect(Collectors.toSet()));
					tmp.add(range);
				}
			}
		}
		
		//Map<Role, Set<Set<ParamRange>>> protoRoles = powersets.keySet().stream().collect(Collectors.toMap(x -> x, x -> new HashSet<>()));
		Map<Role, Set<ParamActualRole>> protoRoles = powersets.keySet().stream().collect(Collectors.toMap(x -> x, x -> new HashSet<>()));
		for (Role r : powersets.keySet())
		{
			Set<Set<ParamRange>> powset = powersets.get(r);
			
			job.debugPrintln("\n[param-core] Ranges powerset for " + r + ": " + powset);
			
			for (Set<ParamRange> cand : powset)
			{
				Set<ParamRange> coset = powset.stream().filter(f -> f.stream().noneMatch(g -> cand.contains(g))).flatMap(Collection::stream).collect(Collectors.toSet());
				
				String z3 = "";

				if (cand.size() > 0)
				{
					z3 += cand.stream().map(c -> "(and (>= id " + c.start.toSmt2Formula() + ") (<= id " + c.end.toSmt2Formula() + ")"
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
					z3 += coset.stream().map(c -> "(and (not (and (>= id " + c.start.toSmt2Formula() + ") (<= id " + c.end.toSmt2Formula() + ")))"
								+ ((!c.start.isConstant() || !c.end.isConstant()) ? " (<= " + c.start.toSmt2Formula() + " " + c.end.toSmt2Formula() + ")" : "")
								+ ")")
							.reduce((c1, c2) -> "(and " + c1 + " " + c2 +")").get();
					if (cand.size() > 0)
					{
						z3 += ")";
					}
				}
					
				//Set<ParamRoleParam> actuals
				Set<ParamIndexVar> actuals
						= cand.stream().flatMap(c -> c.getVars().stream()).collect(Collectors.toSet());
				actuals.addAll(coset.stream().flatMap(c -> c.getVars().stream()).collect(Collectors.toSet()));
				//if (!actuals.isEmpty())
				{
					z3 = "(exists ((id Int) "
							+ actuals.stream().map(p -> "(" + p + " Int)").collect(Collectors.joining(" "))
							+ ") " + z3 + ")";
				}
				
				z3 = //"(declare-const id Int)\n
						"(assert " + z3 + ")";
				
				job.debugPrintln("\n[param-core] Candidate role: " + cand);
				job.debugPrintln("[param-core] Ranges co-set: " + coset);
				job.debugPrintln("[param-core] Running Z3 on:\n" + z3);
				
				if (Z3Wrapper.checkSat(job, this.gpd, z3))
				{
					//protoRoles.get(r).add(cand);
					protoRoles.get(r).add(new ParamActualRole(r.toString(), cand, coset));
				}
			}
		}
		return protoRoles;
	}

	// FIXME: factor out -- cf. super.doAttemptableOutputTasks
	@Override
	protected void tryOutputTasks(Job job) throws CommandLineException, ScribbleException
	{
		if (this.paramArgs.containsKey(ParamCLArgFlag.PARAM_CORE_EFSM))
		{
			String[] args = this.paramArgs.get(ParamCLArgFlag.PARAM_CORE_EFSM);
			for (int i = 0; i < args.length; i += 1)
			{
				Role role = CommandLine.checkRoleArg(job.getContext(), gpd.getHeader().getDeclName(), args[i]);
				this.E0.get(role).entrySet().forEach(e ->
				{
					String out = e.getValue().toDot();
					System.out.println("\n" + role + " " + e.getKey() + ":\n" + out);  // Endpoint graphs are "inlined" (a single graph is built)
				});
			}
		}
		if (this.paramArgs.containsKey(ParamCLArgFlag.PARAM_CORE_EFSM_PNG))
		{
			String[] args = this.paramArgs.get(ParamCLArgFlag.PARAM_CORE_EFSM_PNG);
			for (int i = 0; i < args.length; i += 2)
			{
				Role role = CommandLine.checkRoleArg(job.getContext(), gpd.getHeader().getDeclName(), args[i]);
				String png = args[i+1];
				//for (Entry<Set<ParamRange>, EGraph> e : this.E0.get(role).entrySet())
				for (Entry<ParamActualRole, EGraph> e : this.E0.get(role).entrySet())
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

		job.debugPrintln("\n[param-core] Built model:\n" + this.model.toDot());
		
		if (unfair.length == 0 || !unfair[0])
		{
			ParamCoreSafetyErrors serrs = this.model.getSafetyErrors(job, simpname);  // job just for debug printing
			if (serrs.isSafe())
			{
				job.debugPrintln("\n[param-core] Protocol safe.");
			}
			else
			{
				throw new ParamException("[param-core] Protocol not safe:\n" + serrs);
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
