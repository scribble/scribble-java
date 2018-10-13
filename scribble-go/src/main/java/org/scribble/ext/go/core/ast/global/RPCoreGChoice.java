package org.scribble.ext.go.core.ast.global;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreChoice;
import org.scribble.ext.go.core.ast.RPCoreSyntaxException;
import org.scribble.ext.go.core.ast.RPCoreType;
import org.scribble.ext.go.core.ast.local.RPCoreLActionKind;
import org.scribble.ext.go.core.ast.local.RPCoreLCrossChoice;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.type.RPAnnotatedInterval;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.core.type.name.RPCoreGDelegationType;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPBinIndexExpr.Op;
import org.scribble.ext.go.type.index.RPForeachVar;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexFactory;
import org.scribble.ext.go.type.index.RPIndexSelf;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.ext.go.util.Smt2Translator;
import org.scribble.ext.go.util.Z3Wrapper;
import org.scribble.type.Message;
import org.scribble.type.MessageSig;
import org.scribble.type.kind.Global;
import org.scribble.type.name.GDelegationType;
import org.scribble.type.name.PayloadElemType;
import org.scribble.type.name.Role;

public class RPCoreGChoice extends RPCoreChoice<RPCoreGType, Global> implements RPCoreGType
{
	public final RPIndexedRole src;  // "Singleton" -- checked by isWellFormed
	public final RPIndexedRole dest;  // this.dest == super.role -- arbitrary?

	public RPCoreGChoice(RPIndexedRole src, RPCoreGActionKind kind, RPIndexedRole dest, 
			//LinkedHashMap<RPCoreMessage, RPCoreGType> cases)
			LinkedHashMap<Message, RPCoreGType> cases)
	{
		super(dest, kind, cases);
		this.src = src;
		this.dest = dest;
	}
	
	@Override
	public RPCoreGType subs(RPCoreAstFactory af, RPCoreType<Global> old, RPCoreType<Global> neu)
	{
		if (this.equals(old))
		{
			return (RPCoreGType) neu;
		}
		else
		{
			LinkedHashMap<Message, RPCoreGType> tmp = new LinkedHashMap<>();
			this.cases.forEach((k, v) -> tmp.put(k, v.subs(af, old, neu)));  // Immutable, so neu can be shared by multiple cases
			return af.ParamCoreGChoice(this.src, RPCoreGActionKind.CROSS_TRANSFER, this.dest, tmp);
		}
	}
	
	// gpd only for calling Z3Wrapper.checkSat
	@Override
	public boolean isWellFormed(GoJob job, Stack<Map<RPForeachVar, RPInterval>> context, GProtocolDecl gpd, Smt2Translator smt2t)
	{
		// src (i.e., choice subj) range size=1 for non-unary choices enforced by ParamScribble.g syntax
		// Directed choice check by ParamCoreGProtocolDeclTranslator ensures all dests (including ranges) are (syntactically) the same
		
		RPInterval srcRange = this.src.getParsedRange();
		RPInterval destRange = this.dest.getParsedRange();
		Set<RPIndexVar> vars = Stream.of(srcRange, destRange).flatMap(r -> r.getIndexVars().stream()).collect(Collectors.toSet());
				// FIXME: record foreachvars separately, for additional constraint generation
		
		//if (!checkNonEmptyIntervals(...))
		if (!checkIntervalRanges(job, gpd, vars, smt2t))
		{
			return false;
		}

		if (this.kind == RPCoreGActionKind.CROSS_TRANSFER)
		{
			if (this.cases.size() > 1)
			{
				if (!checkSingletonChoiceSubject(job, gpd, vars, smt2t))
				{
					return false;
				}
			}
			
			if (!checkForeachVars(job, context, smt2t))
			{
				return false;
			}
		}
		
		if (this.src.getName().equals(this.dest.getName()))
		{
			if (this.kind != RPCoreGActionKind.CROSS_TRANSFER)
			{
				throw new RuntimeException("Shouldn't get here: " + this.kind);
			}

			if (!checkOverlappingIntervals(job, context, gpd, vars, smt2t) || !checkForeachVarAlignment(job, context, gpd, smt2t))
			{
				return false;
			}
		}

		/*// Now redundant -- restore for "pair/pipe" sugar
		if (this.kind == RPCoreGActionKind.DOT_TRANSFER)
		{
			String smt2 = "(assert"
					+ (vars.isEmpty() ? "" : " (forall (" + vars.stream().map(v -> "(" + v + " Int)").collect(Collectors.joining(" "))) + ") "
					+ "(and (= (- " + srcRange.end.toSmt2Formula() + " " + srcRange.start.toSmt2Formula() + ") (- "
							+ destRange.end.toSmt2Formula() + " " + destRange.start.toSmt2Formula() + "))"
					+ (!this.src.getName().equals(this.dest.getName()) ? "" :
						" (not (= " + srcRange.start.toSmt2Formula() + " " + destRange.start.toSmt2Formula() + "))")
				  + ")"
					+ (vars.isEmpty() ? "" : ")")
					+ ")";
			
			job.debugPrintln("\n[param-core] [WF] Checking dot-range alignment between " + srcRange + " and " + destRange + ":\n  " + smt2);
			
			if (!Z3Wrapper.checkSat(job, gpd, smt2))
			{
				return false;
			}
		}*/

		return true;
	}

	// Returns true if OK
	private boolean checkForeachVarAlignment(GoJob job, Stack<Map<RPForeachVar, RPInterval>> context, GProtocolDecl gpd, Smt2Translator smt2t)
	{
		RPInterval srcRange = this.src.getParsedRange();
		RPInterval destRange = this.dest.getParsedRange();

		Map<RPForeachVar, RPInterval> peek = context.isEmpty() ? Collections.emptyMap() : context.peek();
		// FIXME: only do if both are foreachvars
		if (hasValidForeachVarIndex(context, this.src) && hasValidForeachVarIndex(context, this.dest))
		{
			String sv = ((RPIndexVar) this.src.intervals.iterator().next().start).toString();
			String dv = ((RPIndexVar) this.dest.intervals.iterator().next().end).toString();
			RPInterval s = peek.get(RPIndexFactory.RPForeachVar(sv));
			RPInterval d = peek.get(RPIndexFactory.RPForeachVar(dv));
			Set<RPIndexVar> vars = Stream.of(s, d).flatMap(r -> r.getIndexVars().stream()).collect(Collectors.toSet());
			// Duplicated from DOT_TRANSFER
			/*String smt2 = "(assert"
					+ (tmp.isEmpty() ? "" : " (forall (" + vars.stream().map(v -> "(" + v + " Int)").collect(Collectors.joining(" "))) + ") "
					+ "(and (= (- " + s.end.toSmt2Formula() + " " + s.start.toSmt2Formula() + ") (- "
							+ d.end.toSmt2Formula() + " " + d.start.toSmt2Formula() + "))"
					+ (!this.src.getName().equals(this.dest.getName()) ? "" :
						" (not (= " + s.start.toSmt2Formula() + " " + d.start.toSmt2Formula() + "))")
					+ ")"
					+ (tmp.isEmpty() ? "" : ")")
					+ ")";*/
			
			List<String> cs = new LinkedList<>();
			cs.add(smt2t.makeEq(smt2t.makeSub(s.end.toSmt2Formula(), s.start.toSmt2Formula()), smt2t.makeSub(d.end.toSmt2Formula(), d.start.toSmt2Formula())));
			if (this.src.getName().equals(this.dest.getName()))
			{
				cs.add(smt2t.makeNot(smt2t.makeEq(s.start.toSmt2Formula(), d.start.toSmt2Formula())));
			}
			String smt2 = (cs.size() == 1) ? cs.get(0) : smt2t.makeAnd(cs);
			if (!vars.isEmpty())
			{
				smt2 = smt2t.makeForall(vars.stream().map(x -> x.toSmt2Formula()).collect(Collectors.toList()), smt2);
			}
			smt2 = smt2t.makeAssert(smt2);
		
			job.debugPrintln("\n[param-core] [WF] Checking foreach-var alignment between " + srcRange + " and " + destRange + ":\n  " + smt2);
			
			if (!Z3Wrapper.checkSat(job, gpd, smt2))
			{
				return false;
			}
		}
		return true;
	}

	/*private boolean checkSelfCommunication(...)
	 {
		//Set<String> curr = peek.keySet().stream().map(k -> k.toString()).collect(Collectors.toSet());
		if (isValidForeachIndexVar(context, this.src) && isValidForeachIndexVar(context, this.dest))
		{
			String sv = srcVars.iterator().next().toString();
			String dv = destVars.iterator().next().toString();
			if (curr.contains(sv) && curr.contains(dv))
			{
				RPIndexExpr s = peek.get(RPIndexFactory.RPForeachVar(sv)).start;
				RPIndexExpr d = peek.get(RPIndexFactory.RPForeachVar(dv)).start;
				if (!(s instanceof RPIndexInt) && !(s instanceof RPIndexInt))
				{
					System.err.println("\n[param-core] Interval separation not being fully proved: " + s + "  and  " + d);
				}
				if (s.equals(d))
						// FIXME: use Z3 to prove intervals not equal (not just checking against syntactic equality)
				{
					job.debugPrintln("\n[param-core] (Potential) illegal self-communication: " + this);
					return false;
				}
			}
		}
	}*/
				
	// Returns true if OK
	private boolean checkOverlappingIntervals(GoJob job,
			Stack<Map<RPForeachVar, RPInterval>> context, GProtocolDecl gpd, Set<RPIndexVar> vars, Smt2Translator smt2t)
	{
		RPInterval srcRange = this.src.getParsedRange();
		RPInterval destRange = this.dest.getParsedRange();
		Map<RPForeachVar, RPInterval> peek = context.isEmpty() ? Collections.emptyMap() : context.peek();
		Set<String> curr = peek.keySet().stream().map(k -> k.name).collect(Collectors.toSet());

		/*String smt2 = "(assert (exists ((foobartmp Int)";  // FIXME: factor out
		smt2 += vars.stream().map(v -> " (" + v.name + " Int)").collect(Collectors.joining(""));
		smt2 += ") (and";
		smt2 += vars.isEmpty() ? "" : vars.stream().map(v -> " (>= " + v + " 1)").collect(Collectors.joining(""));  
				// FIXME: lower bound constant '1' -- replace by global invariant

		Set<RPIndexVar> srcAndDestVars = new HashSet<>();
		srcAndDestVars.addAll(this.src.getIndexVars());
		srcAndDestVars.addAll(this.dest.getIndexVars());
		for (RPIndexVar sv : srcAndDestVars)
		{
			String tmp = sv.toString();
			if (curr.contains(tmp))  // FIXME: awkwardness of RPForeachVar and RPIndexVar
			{
				RPInterval ival = peek.get(RPIndexFactory.RPForeachVar(tmp));
				smt2 += " (= " + sv + " " + ival.start + ")";
			}
		}

		smt2 += Stream.of(srcRange, destRange)
				.map(r -> " (>= foobartmp " + r.start.toSmt2Formula() + ") (<= foobartmp " + r.end.toSmt2Formula() + ")")
				.collect(Collectors.joining());
		smt2 += ")))";*/
		
		List<String> cs = new LinkedList<>();
		vars.forEach(x -> cs.add(smt2t.makeGte(x.toSmt2Formula(), "1")));
		Set<RPIndexVar> srcAndDestVars = new HashSet<>();
		srcAndDestVars.addAll(this.src.getIndexVars());
		srcAndDestVars.addAll(this.dest.getIndexVars());
		for (RPIndexVar sv : srcAndDestVars)
		{
			String tmp = sv.toString();
			if (curr.contains(tmp))  // FIXME: awkwardness of RPForeachVar and RPIndexVar
			{
				RPInterval ival = peek.get(RPIndexFactory.RPForeachVar(tmp));
				cs.add(smt2t.makeEq(sv.toSmt2Formula(), ival.start.toSmt2Formula()));
			}
		}
		Stream.of(srcRange, destRange).forEach(r -> 
		{
			cs.add(smt2t.makeGte("foobartmp", r.start.toSmt2Formula()));
			cs.add(smt2t.makeLte("foobartmp", r.end.toSmt2Formula()));
		});
		List<String> tmp = new LinkedList<>();
		tmp.add("foobartmp");
		vars.forEach(x -> tmp.add(x.toSmt2Formula()));
		String smt2 = smt2t.makeAssert(smt2t.makeExists(tmp, smt2t.makeAnd(cs)));
		
		job.debugPrintln("\n[param-core] [WF] Checking non-overlapping ranges (potential self-communication) for " + this.src.getName() + ":\n  " + smt2);
		
		if (Z3Wrapper.checkSat(job, gpd, smt2))
		{
			return false;
		}

		// CHECKME: projection cases for rolename self-comm but non-overlapping intervals

		return true;
	}

	// Returns true if OK
	private boolean checkForeachVars(GoJob job, Stack<Map<RPForeachVar, RPInterval>> context, Smt2Translator smt2t)
	{
		Set<String> all = context.stream().flatMap(m -> m.keySet().stream().map(k -> k.name)).collect(Collectors.toSet());  // FIXME: awkwardness of RPForeachVar and RPIndexVar
		Function<RPIndexedRole, Boolean> checkForeachVarIndex = ir ->
		{
			Set<String> vs = ir.getIndexVars().stream().map(x -> x.name).collect(Collectors.toSet());  // FIXME: awkwardness of RPForeachVar and RPIndexVar
			if (vs.stream().anyMatch(x -> all.contains(x)))
			{
				if (!hasValidForeachVarIndex(context, ir))
				{
					// FIXME: check in name disamb pass?
					job.debugPrintln("\n[param-core] [WF] Illegal use/access of foreach-var: " + ir);
					return false;
				}
			}
			return true;
		};
		if (!checkForeachVarIndex.apply(this.src) || !checkForeachVarIndex.apply(this.dest))
		{
			return false;
		}
		return true;
	}

	// Returns true if OK
	private boolean checkSingletonChoiceSubject(GoJob job, GProtocolDecl gpd, Set<RPIndexVar> vars, Smt2Translator smt2t)
	{
		RPInterval srcRange = this.src.getParsedRange();
		/*String bar = "(assert "
				+ (vars.isEmpty() ? "" : "(exists (" + vars.stream().map(v -> "(" + v.name + " Int)").collect(Collectors.joining(" ")) + ") (and ")
				+ vars.stream().map(v -> " (>= " + v + " 1)").collect(Collectors.joining(""))  // FIXME: lower bound constant -- replace by global invariant
				+ "(not (= (- " + srcRange.end.toSmt2Formula() + " " + srcRange.start.toSmt2Formula() + ") 0))"
				+ (vars.isEmpty() ? "" : "))")
				+ ")";*/
		
		List<String> cs = new LinkedList<>();
		vars.forEach(v -> cs.add(smt2t.makeGte(v.toSmt2Formula(), "1")));
		cs.add(smt2t.makeNot(smt2t.makeEq(smt2t.makeSub(srcRange.end.toSmt2Formula(), srcRange.start.toSmt2Formula()), "0")));
		String smt2 = smt2t.makeAnd(cs);
		if (!vars.isEmpty())
		{
			smt2 = smt2t.makeExists(vars.stream().map(x -> x.toSmt2Formula()).collect(Collectors.toList()), smt2);
		}
		smt2 = smt2t.makeAssert(smt2);

		job.debugPrintln("\n[param-core] [WF] Checking singleton choice-subject for " + this.src + ":\n  " + smt2); 

		if (Z3Wrapper.checkSat(job, gpd, smt2))
		{
			return false;
		}
		return true;
	}

	/*// Returns true if OK
	private boolean checkNonEmptyIntervals(...)
	{
		// CHECKME: is range size>0 already ensured by syntax?
		Function<ParamRange, String> foo1 = r -> 
				  "(assert (exists ((foobartmp Int)"
				+ vars.stream().map(v -> " (" + v.name + " Int)").collect(Collectors.joining(""))
				+ ") (and"
				+ " (>= foobartmp " + r.start.toSmt2Formula() + ") (<= foobartmp " + r.end.toSmt2Formula() + ")"  // FIXME: factor out with above
				+ ")))";
		Predicate<ParamRange> foo2 = r ->
		{
			String foo = foo1.apply(srcRange);

			job.debugPrintln("\n[param-core] [WF] Checking non-empty ranges:\n  " + foo);

			return Z3Wrapper.checkSat(job, gpd, foo);
		};
		...
	*/

	// Returns true if OK
	private boolean checkIntervalRanges(GoJob job, GProtocolDecl gpd, Set<RPIndexVar> vars, Smt2Translator smt2t)
	{
		RPInterval srcRange = this.src.getParsedRange();
		RPInterval destRange = this.dest.getParsedRange();

		Function<RPInterval, String> foo1 = r ->  // FIXME: factor out with above
				  "(assert "
				+ (vars.isEmpty() ? "" : "(exists (" + vars.stream().map(v -> "(" + v.name + " Int)").collect(Collectors.joining(" ")) + ") (and (and")
				+ vars.stream().map(v -> " (>= " + v + " 1)").collect(Collectors.joining(""))  // FIXME: lower bound constant -- replace by global invariant
				+ (vars.isEmpty() ? "" : ")")
				+ " (> " + r.start.toSmt2Formula() + " " + r.end.toSmt2Formula() + ")"
				//+ Z3Wrapper.getSmt2_gt(r.start, r.end)
				+ (vars.isEmpty() ? "" : "))")
				+ ")";
		Predicate<RPInterval> foo2 = r ->
		{
			String foo = foo1.apply(r);

			job.debugPrintln("\n[param-core] [WF] Checking WF range interval for " + r + ":\n  " + foo); 

			return Z3Wrapper.checkSat(job, gpd, foo);
		};
		if (foo2.test(srcRange) || foo2.test(destRange))
		{
			return false;
		}
		return true;
	}

	public static boolean hasValidForeachVarIndex(Stack<Map<RPForeachVar, RPInterval>> context, RPIndexedRole ir)
	{
		if (context.isEmpty())
		{
			return false;
		}
		//Set<String> curr = context.peek().keySet().stream().map(k -> k.toString()).collect(Collectors.toSet());
		Set<String> all = context.stream().flatMap(m -> m.keySet().stream().map(k -> k.name)).collect(Collectors.toSet());  // FIXME: awkwardness of RPForeachVar and RPIndexVar
		RPInterval ival = ir.intervals.iterator().next();
		return ir.intervals.size() == 1 && ival.isSingleton() 
				&& (ival.start instanceof RPIndexVar) && all.contains(((RPIndexVar) ival.start).name);  // N.B. RPIndexVar, not RPForeachVar (FIXME)
	}
				
	@Override
	public RPCoreGActionKind getKind()
	{
		return (RPCoreGActionKind) this.kind;
	}
	
	@Override
	public Set<RPIndexedRole> getIndexedRoles()
	{
		Set<RPIndexedRole> res = Stream.of(this.src, this.dest).collect(Collectors.toSet());
		this.cases.values().forEach(c -> res.addAll(c.getIndexedRoles()));
		return res;
	}

	@Override
	public RPCoreLType project(RPCoreAstFactory af, //Role r, Set<ParamRange> ranges) throws ParamCoreSyntaxException
				RPRoleVariant subj) throws RPCoreSyntaxException
	{
		//LinkedHashMap<RPCoreMessage, RPCoreLType>
		LinkedHashMap<Message, RPCoreLType>
				projs = new LinkedHashMap<>();
		//for (Entry<RPCoreMessage, RPCoreGType>
		for (Entry<Message, RPCoreGType>
				e : this.cases.entrySet())
		{
			//RPCoreMessage a = e.getKey();
			Message a = e.getKey();
			//projs.put(a, e.getValue().project(af, r, ranges));
			projs.put(a, e.getValue().project(af, subj));
					// N.B. local actions directly preserved from globals -- so core-receive also has assertion (cf. ParamGActionTransfer.project, currently no ParamLReceive)
					// FIXME: receive assertion projection -- should not be the same as send?
			
			if (a instanceof MessageSig)
			{
				for (PayloadElemType<?> pet : //a.pay.elems)
						((MessageSig) a).payload.elems)
				{
					if (pet instanceof GDelegationType)
					{
						if (!(pet instanceof RPCoreGDelegationType))
						{
							throw new RuntimeException("[rp-core] TODO: " + pet);
						}
						RPCoreGDelegationType gdt = (RPCoreGDelegationType) pet;  // Payload types come from ParamCoreGProtocolDeclTranslator#parsePayload (toMessage)
						
						// cf. GDelegationElem#project
						
						//new LProtocolName();  // FIXME: need actual role (not just role name)
					}
				}
			}
			// MessageSigName is not delegation  // FIXME: cf. "Sync@A"
		}
		
		// "Simple" cases
		Role srcName = this.src.getName();
		Role destName = this.dest.getName();
		Role subjName = subj.getName();
		RPInterval srcRange = this.src.getParsedRange();
		RPInterval destRange = this.dest.getParsedRange();
		if (this.kind == RPCoreGActionKind.CROSS_TRANSFER)
		{	
			//if (this.src.getName().equals(r))
			if (srcName.equals(subjName) && subj.intervals.contains(srcRange))  // FIXME: factor out?
			{
				return af.ParamCoreLCrossChoice(this.dest, RPCoreLActionKind.CROSS_SEND, projs);
			}
			else if (destName.equals(subjName) && subj.intervals.contains(destRange))
			{
				return af.ParamCoreLCrossChoice(this.src, RPCoreLActionKind.CROSS_RECEIVE, projs);
			}
		}
		/*else if (this.kind == RPCoreGActionKind.DOT_TRANSFER)
		{
			if (srcName.equals(subjName) && subj.intervals.contains(srcRange))  // FIXME: factor out?
			{
				if (destName.equals(subjName) && subj.intervals.contains(destRange))  // Possible for dot-transfer (src.start != dest.start) -- cf. cross-transfer
				{
					RPIndexExpr offset = RPIndexFactory.ParamBinIndexExpr(RPBinIndexExpr.Op.Add,
								RPIndexFactory.ParamIntVar("_id"),
								RPIndexFactory.ParamBinIndexExpr(RPBinIndexExpr.Op.Subt, destRange.start, srcRange.start));
					/*Map<ParamCoreMessage, ParamCoreLType> tmp = projs.entrySet().stream().collect(Collectors.toMap(Entry::getKey,
							p -> new ParamCoreLDotChoice(this.dest, offset, ParamCoreLActionKind.DOT_SEND,
									Stream.of(p.getKey()).collect(Collectors.toMap(k -> k, k -> p.getValue())))
							));* /
					Function<Entry<RPCoreMessage, RPCoreLType>, LinkedHashMap<RPCoreMessage, RPCoreLType>> foo = e ->
					{
						LinkedHashMap<RPCoreMessage, RPCoreLType> res = new LinkedHashMap<>();
						res.put(e.getKey(), e.getValue());
						return res;
					};
					LinkedHashMap<RPCoreMessage, RPCoreLType> tmp = new LinkedHashMap<>();
					projs.entrySet().forEach(e -> tmp.put(e.getKey(), 
							new RPCoreLDotChoice(this.dest, offset, RPCoreLActionKind.DOT_SEND, foo.apply(e))));
					RPIndexExpr offset2 = RPIndexFactory.ParamBinIndexExpr(RPBinIndexExpr.Op.Add,
							RPIndexFactory.ParamIntVar("_id"),
						 RPIndexFactory.ParamBinIndexExpr(RPBinIndexExpr.Op.Subt, srcRange.start, destRange.start));
					return af.ParamCoreLDotChoice(this.src, offset2, RPCoreLActionKind.DOT_RECEIVE, tmp);
				}
				else
				{
					RPIndexExpr offset = RPIndexFactory.ParamBinIndexExpr(RPBinIndexExpr.Op.Add,
							RPIndexFactory.ParamIntVar("_id"),
							RPIndexFactory.ParamBinIndexExpr(RPBinIndexExpr.Op.Subt, destRange.start, srcRange.start));
					return af.ParamCoreLDotChoice(this.dest, offset, RPCoreLActionKind.DOT_SEND, projs);
				}
			}
			else if (destName.equals(subjName) && subj.intervals.contains(destRange))
			{
				RPIndexExpr offset = RPIndexFactory.ParamBinIndexExpr(RPBinIndexExpr.Op.Add,
						RPIndexFactory.ParamIntVar("_id"),
						RPIndexFactory.ParamBinIndexExpr(RPBinIndexExpr.Op.Subt, srcRange.start, destRange.start));
				return af.ParamCoreLDotChoice(this.src, offset, RPCoreLActionKind.DOT_RECEIVE, projs);
			}
		}*/
		else
		{
			throw new RuntimeException("[param-core] TODO: " + this);
		}
		
		// src name != dest name
		//return merge(af, r, ranges, projs);
		return merge(af, subj, projs);
	}

	// Duplicated from project above
	@Override
	public RPCoreLType project3(RPCoreAstFactory af, Set<Role> roles, Set<RPAnnotatedInterval> ivals, RPIndexedRole subj) throws RPCoreSyntaxException
	{
		LinkedHashMap<Message, RPCoreLType> projs = new LinkedHashMap<>();
		for (Entry<Message, RPCoreGType> e : this.cases.entrySet())
		{
			Message a = e.getKey();
			projs.put(a, e.getValue().project3(af, roles, ivals, subj));
		}
		
		Role srcName = this.src.getName();
		Role destName = this.dest.getName();
		//Role subjName = subj.getName();
		RPInterval srcRange = this.src.getParsedRange();
		RPInterval destRange = this.dest.getParsedRange();
		Set<String> fvars = ivals.stream().map(x -> x.var.toString()).collect(Collectors.toSet());
		if (this.kind == RPCoreGActionKind.CROSS_TRANSFER)
		{	
			if (this.src.equals(subj))  // N.B. subj uses RPIndexVar (not RPForeachVar) -- cf. the invocation of project3 in RPCoreGForeach#project2
			{
				//return af.ParamCoreLCrossChoice(this.dest, RPCoreLActionKind.CROSS_SEND, projs);
				if (srcRange.start.equals(srcRange.end) //&& srcRange.start instanceof RPIndexVar
						&& destRange.start.equals(destRange.end) //&& destRange.start instanceof RPIndexVar
						)
				{
					RPIndexExpr srcStart = srcRange.start;
					RPIndexExpr destStart = destRange.start;
					//Set<String> tmp = Stream.of(srcStart, destStart).filter(x -> x instanceof RPIndexVar).map(x -> x.toString()).collect(Collectors.toSet());
					String srcTmp = (srcStart instanceof RPIndexVar) ? ((RPIndexVar) srcStart).name : null;
					String destTmp = (destStart instanceof RPIndexVar) ? ((RPIndexVar) destStart).name : null;
							// FIXME: awkwardness of RPIndexVar and RPForeachVar
					Set<String> tmp = Stream.of(srcTmp, destTmp).filter(x -> x != null).collect(Collectors.toSet());
					if (!tmp.isEmpty() && roles.contains(destName) && //fvars.contains(srcVar.toString()) && fvars.contains(destVar.toString()))
							fvars.containsAll(tmp))
					{
						RPIndexExpr upper = (destTmp != null) ? ivals.stream().filter(x -> x.var.name.equals(destTmp)).findFirst().get().start : destStart;
						RPIndexExpr lower = (srcTmp != null) ? ivals.stream().filter(x -> x.var.name.equals(srcTmp)).findFirst().get().start : srcStart;
						RPIndexExpr destExpr = RPIndexFactory.ParamBinIndexExpr(Op.Add, RPIndexSelf.SELF,
								//RPIndexFactory.ParamBinIndexExpr(Op.Subt, destStart, srcStart));
								RPIndexFactory.ParamBinIndexExpr(Op.Subt, upper, lower));
						RPIndexedRole dest = new RPIndexedRole(destName.toString(), Stream.of(new RPInterval(destExpr, destExpr)).collect(Collectors.toSet()));
						return af.ParamCoreLCrossChoice(dest, RPCoreLActionKind.CROSS_SEND, projs);
					}
					else if (!roles.contains(destName) || !(fvars.contains(srcStart.toString()) && fvars.contains(destStart.toString())))
					{
						return af.ParamCoreLCrossChoice(this.dest, RPCoreLActionKind.CROSS_SEND, projs);
					}
				}
				// Otherwise try merge (if neither src/dest are subj)
			}
			else if (this.dest.equals(subj))
			{
				if (srcRange.start.equals(srcRange.end) //&& srcRange.start instanceof RPIndexVar
						&& destRange.start.equals(destRange.end) //&& destRange.start instanceof RPIndexVar)
						)
				{
					RPIndexExpr srcStart = srcRange.start;
					RPIndexExpr destStart = destRange.start;
					//Set<String> tmp = Stream.of(srcStart, destStart).filter(x -> x instanceof RPIndexVar).map(x -> x.toString()).collect(Collectors.toSet());
					String srcTmp = (srcStart instanceof RPIndexVar) ? ((RPIndexVar) srcStart).name : null;
					String destTmp = (destStart instanceof RPIndexVar) ? ((RPIndexVar) destStart).name : null;
							// FIXME: awkwardness of RPIndexVar and RPForeachVar
					Set<String> tmp = Stream.of(srcTmp, destTmp).filter(x -> x != null).collect(Collectors.toSet());
					if (!tmp.isEmpty() && roles.contains(srcName) && //fvars.contains(srcVar.toString()) && fvars.contains(destVar.toString()))
							fvars.containsAll(tmp))
					{
						RPIndexExpr upper = (srcTmp != null) ? ivals.stream().filter(x -> x.var.name.equals(srcTmp)).findFirst().get().start : srcStart;
						RPIndexExpr lower = (destTmp != null) ? ivals.stream().filter(x -> x.var.name.equals(destTmp)).findFirst().get().start : destStart;
						RPIndexExpr srcExpr = RPIndexFactory.ParamBinIndexExpr(Op.Add, RPIndexSelf.SELF,
								//RPIndexFactory.ParamBinIndexExpr(Op.Subt, srcStart, destStart));
								RPIndexFactory.ParamBinIndexExpr(Op.Subt, upper, lower));
						RPIndexedRole src = new RPIndexedRole(destName.toString(), Stream.of(new RPInterval(srcExpr, srcExpr)).collect(Collectors.toSet()));
						return af.ParamCoreLCrossChoice(src, RPCoreLActionKind.CROSS_RECEIVE, projs);
					}
					else
					{
						return af.ParamCoreLCrossChoice(this.src, RPCoreLActionKind.CROSS_RECEIVE, projs);
					}
				}
				// Otherwise merge (if neither src/dest are subj)
			}
		}
		else
		{
			throw new RuntimeException("[param-core] TODO: " + this);
		}
		
		// CROSS_TRANSFER, and subj not sender nor receiver
		if (!this.src.equals(subj) && !this.dest.equals(subj))
		{
			return merge3(af, subj, projs);
		}
		else
		{
			throw new RuntimeException("[rp-core] Projection not defined: " + this + ", " + subj);
		}
	}
		
	//private ParamCoreLType merge(ParamCoreAstFactory af, Role r, Set<ParamRange> ranges, Map<ParamCoreMessage, ParamCoreLType> projs) throws ParamCoreSyntaxException
	private RPCoreLType merge(RPCoreAstFactory af, RPRoleVariant r, 
			//Map<RPCoreMessage, RPCoreLType> projs) throws RPCoreSyntaxException
			Map<Message, RPCoreLType> projs) throws RPCoreSyntaxException
	{
		// "Merge"
		Set<RPCoreLType> values = new HashSet<>(projs.values());
		/*if (values.size() > 1)
		{
			throw new RPCoreSyntaxException("[param-core] Cannot project \n" + this + "\n onto " + r 
					//+ " for " + ranges
					+ ": cannot merge: " + projs);
		}*/
		if (values.size() == 1)  // Handles output choices
		{
			return values.iterator().next();
		}
		RPIndexedRole peer = null;
		LinkedHashMap<Message, RPCoreLType> cases = new LinkedHashMap<>();
		for (Message m : projs.keySet())
		{
			RPCoreLType p = projs.get(m);
			if (!(p instanceof RPCoreLCrossChoice))
			{
				throw new RPCoreSyntaxException("[param-core] Cannot project \n" + this + "\n onto " + r + ": cannot merge: " + projs);
			}
			RPCoreLCrossChoice c = (RPCoreLCrossChoice) p;
			if (c.kind != RPCoreLActionKind.CROSS_RECEIVE)  // FIXME generalise
			{
				throw new RPCoreSyntaxException("[param-core] Cannot project \n" + this + "\n onto " + r + ": cannot merge: " + projs);
			}
			if (peer == null)
			{
				peer = c.role;
			}
			else if (!c.role.equals(peer))
			{
				throw new RPCoreSyntaxException("[param-core] Cannot project \n" + this + "\n onto " + r + ": cannot merge: " + projs);
			}
			cases.putAll(c.cases);
		}
		return af.ParamCoreLCrossChoice(peer, RPCoreLActionKind.CROSS_RECEIVE, cases);
	}

	// Cf. merge above
	private RPCoreLType merge3(RPCoreAstFactory af, RPIndexedRole r, Map<Message, RPCoreLType> projs) throws RPCoreSyntaxException
	{
		Set<RPCoreLType> values = new HashSet<>(projs.values());
		if (values.size() > 1)
		{
			throw new RPCoreSyntaxException("[param-core] Cannot project \n" + this + "\n onto " + r + ": cannot merge: " + values);
		}
		return values.iterator().next();
	}
	
	@Override
	public int hashCode()
	{
		int hash = 2339;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.src.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPCoreGChoice))
		{
			return false;
		}
		return super.equals(obj) && this.src.equals(((RPCoreGChoice) obj).src);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof RPCoreGChoice;
	}

	@Override
	public String toString()
	{
		return this.src.toString() + this.kind + this.dest + casesToString();  // toString needed?
	}
}
