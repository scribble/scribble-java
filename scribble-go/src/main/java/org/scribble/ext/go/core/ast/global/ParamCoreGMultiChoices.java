package org.scribble.ext.go.core.ast.global;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ext.go.core.ast.ParamCoreAstFactory;
import org.scribble.ext.go.core.ast.ParamCoreChoice;
import org.scribble.ext.go.core.ast.ParamCoreMessage;
import org.scribble.ext.go.core.ast.ParamCoreSyntaxException;
import org.scribble.ext.go.core.ast.local.ParamCoreLActionKind;
import org.scribble.ext.go.core.ast.local.ParamCoreLType;
import org.scribble.ext.go.core.type.ParamActualRole;
import org.scribble.ext.go.core.type.ParamRange;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.ext.go.main.ParamJob;
import org.scribble.ext.go.type.index.ParamIndexVar;
import org.scribble.type.kind.Global;

public class ParamCoreGMultiChoices extends ParamCoreChoice<ParamCoreGType, Global> implements ParamCoreGType
{
	public final ParamRole src;   // Cf. ParamCoreGChoice singleton src
	public final ParamRole dest;  // this.dest == super.role -- arbitrary?
	
	public final ParamIndexVar var;

	public ParamCoreGMultiChoices(ParamRole src, ParamIndexVar var, ParamRole dest, Set<ParamCoreMessage> cases, ParamCoreGType cont)
	{
		super(dest, ParamCoreGActionKind.CROSS_TRANSFER, cases.stream().collect(Collectors.toMap(c -> c, c -> cont)));  // FIXME
		this.src = src;
		this.dest = dest;
		this.var = var;
	}
	
	@Override
	public boolean isWellFormed(ParamJob job, GProtocolDecl gpd)
	{
		// src (i.e., choice subj) range size=1 for non-unary choices enforced by ParamScribble.g syntax
		// Directed choice check by ParamCoreGProtocolDeclTranslator ensures all dests (including ranges) are (syntactically) the same
		
		/*ParamRange srcRange = src.getParsedRange();
		ParamRange destRange = dest.getParsedRange();
		Set<ParamIndexVar> vars = Stream.of(srcRange, destRange).flatMap(r -> r.getVars().stream()).collect(Collectors.toSet());
		
		Function<ParamRange, String> foo1 = r ->  // FIXME: factor out with above
				  "(assert "
				+ (vars.isEmpty() ? "" : "(exists (" + vars.stream().map(v -> "(" + v.name + " Int)").collect(Collectors.joining(" ")) + ") (and (and")
				+ vars.stream().map(v -> " (>= " + v + " 1)").collect(Collectors.joining(""))  // FIXME: lower bound constant -- replace by global invariant
				+ (vars.isEmpty() ? "" : ")")
				+ " (> " + r.start.toSmt2Formula() + " " + r.end.toSmt2Formula() + ")"
				+ (vars.isEmpty() ? "" : "))")
				+ ")";
		Predicate<ParamRange> foo2 = r ->
		{
			String foo = foo1.apply(r);

			job.debugPrintln("\n[param-core] [WF] Checking WF range interval for " + r + ":\n  " + foo); 

			return Z3Wrapper.checkSat(job, gpd, foo);
		};
		if (foo2.test(srcRange) || foo2.test(destRange))
		{
			return false;
		}


		String bar = "(assert "
				+ (vars.isEmpty() ? "" : "(exists (" + vars.stream().map(v -> "(" + v.name + " Int)").collect(Collectors.joining(" ")) + ") (and ")
				+ vars.stream().map(v -> " (>= " + v + " 1)").collect(Collectors.joining(""))  // FIXME: lower bound constant -- replace by global invariant
				+ "(not (= (- " + srcRange.end.toSmt2Formula() + " " + srcRange.start.toSmt2Formula() + ") 0))"
				+ (vars.isEmpty() ? "" : "))")
				+ ")";

		job.debugPrintln("\n[param-core] [WF] Checking singleton choice-subk for " + this.src + ":\n  " + bar); 

		if (Z3Wrapper.checkSat(job, gpd, bar))
		{
			return false;
		}
		
		
		if (this.src.getName().equals(this.dest.getName()))
		{
			if (this.kind == ParamCoreGActionKind.CROSS_TRANSFER)
			{
				String smt2 = "(assert (exists ((foobartmp Int)";  // FIXME: factor out
				smt2 += vars.stream().map(v -> " (" + v.name + " Int)").collect(Collectors.joining(""));
				smt2 += ") (and";
				smt2 += vars.isEmpty() ? "" : vars.stream().map(v -> " (>= " + v + " 1)").collect(Collectors.joining(""));  // FIXME: lower bound constant -- replace by global invariant
				smt2 += Stream.of(srcRange, destRange)
						.map(r -> " (>= foobartmp " + r.start.toSmt2Formula() + ") (<= foobartmp " + r.end.toSmt2Formula() + ")")
						.collect(Collectors.joining());
				smt2 += ")))";
				
				job.debugPrintln("\n[param-core] [WF] Checking non-overlapping ranges for " + this.src.getName() + ":\n  " + smt2);
				
				if (Z3Wrapper.checkSat(job, gpd, smt2))
				{
					return false;
				}
				// CHECKME: projection cases for rolename self-comm but non-overlapping intervals
			}
		}

		if (this.kind == ParamCoreGActionKind.DOT_TRANSFER)
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

	@Override
	public ParamCoreGActionKind getKind()
	{
		return (ParamCoreGActionKind) this.kind;
	}
	
	@Override
	public Set<ParamRole> getParamRoles()
	{
		Set<ParamRole> res = Stream.of(this.src, this.dest).collect(Collectors.toSet());
		res.addAll(this.cases.values().iterator().next().getParamRoles());
		return res;
	}

	@Override
	//public ParamCoreLType project(ParamCoreAstFactory af, Role r, Set<ParamRange> ranges) throws ParamCoreSyntaxException
	public ParamCoreLType project(ParamCoreAstFactory af, ParamActualRole subj) throws ParamCoreSyntaxException
	{
		if (this.kind != ParamCoreGActionKind.CROSS_TRANSFER)
		{
			throw new RuntimeException("[param-core] TODO: " + this);
		}
		
		Map<ParamCoreMessage, ParamCoreLType> projs = new HashMap<>();
		for (Entry<ParamCoreMessage, ParamCoreGType> e : this.cases.entrySet())
		{
			ParamCoreMessage a = e.getKey();
			//projs.put(a, e.getValue().project(af, r, ranges));
			projs.put(a, e.getValue().project(af, subj));
					// N.B. local actions directly preserved from globals -- so core-receive also has assertion (cf. ParamGActionTransfer.project, currently no ParamLReceive)
					// FIXME: receive assertion projection -- should not be the same as send?
		}

		// FIXME: same condition as merge (factor out)  // FIXME: no, already syntactic singleton continuation already checked by ParamCoreGProtocolDeclTranslator?
		Set<ParamCoreLType> values = new HashSet<>(projs.values());
		if (values.size() > 1)
		{
			throw new ParamCoreSyntaxException("[param-core] Cannot project \n" + this + "\n onto " + subj 
					//+ " for " + ranges
					+ ": cannot merge for: " + projs.keySet());
		}
		
		// "Simple" cases -- same projection as ParamCoreGChoice, i.e., same local types? -- index var redundant, apart from "syntactic consistency" for subsequent message actions? (i.e., only for Scribble syntax?)
		if (this.src.getName().equals(subj.getName()) && subj.ranges.contains(this.src.getParsedRange()))  // FIXME: factor out?
		{
			return af.ParamCoreLMultiChoices(this.dest, ParamCoreLActionKind.SEND_ALL, this.var, projs.keySet(), values.iterator().next());
		}
		else if (this.dest.getName().equals(subj.getName()) && subj.ranges.contains(this.dest.getParsedRange()))
		{
			return af.ParamCoreLMultiChoices(this.src, ParamCoreLActionKind.RECEIVE_ALL, this.var, projs.keySet(), values.iterator().next());
		}
		
		// src name != dest name
		//return merge(af, r, ranges, projs);
		return merge(af, subj, projs);
	}
		
	//private ParamCoreLType merge(ParamCoreAstFactory af, Role r, Set<ParamRange> ranges, Map<ParamCoreMessage, ParamCoreLType> projs) throws ParamCoreSyntaxException
	private ParamCoreLType merge(ParamCoreAstFactory af, ParamActualRole r, Map<ParamCoreMessage, ParamCoreLType> projs) throws ParamCoreSyntaxException
	{
		// "Merge"
		Set<ParamCoreLType> values = new HashSet<>(projs.values());
		if (values.size() > 1)
		{
			throw new ParamCoreSyntaxException("[param-core] Cannot project \n" + this + "\n onto " + r 
					//+ " for " + ranges
					+ ": cannot merge for: " + projs.keySet());
		}
		
		return values.iterator().next();
	}

	@Override
	public String toString()
	{
		ParamRange r = this.src.getParsedRange();
		return this.src.getName() + "[" + this.var + ":" + r.start + ".." + r.end + "]"
				+ this.kind + "*" + this.dest + casesToString();  // toString needed?
	}
	
	@Override
	public int hashCode()
	{
		int hash = 2339;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.src.hashCode();
		hash = 31 * hash + this.var.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof ParamCoreGMultiChoices))
		{
			return false;
		}
		ParamCoreGMultiChoices them = (ParamCoreGMultiChoices) obj;
		return super.equals(obj)  // Does canEquals
				&& this.src.equals(them.src) && this.var.equals(them.var);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ParamCoreGMultiChoices;
	}
}
