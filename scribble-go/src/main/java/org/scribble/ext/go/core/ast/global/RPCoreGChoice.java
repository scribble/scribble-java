package org.scribble.ext.go.core.ast.global;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreChoice;
import org.scribble.ext.go.core.ast.RPCoreSyntaxException;
import org.scribble.ext.go.core.ast.local.RPCoreLActionKind;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPIndexVar;
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
	public boolean isWellFormed(GoJob job, GProtocolDecl gpd)
	{
		// src (i.e., choice subj) range size=1 for non-unary choices enforced by ParamScribble.g syntax
		// Directed choice check by ParamCoreGProtocolDeclTranslator ensures all dests (including ranges) are (syntactically) the same
		
		RPInterval srcRange = this.src.getParsedRange();
		RPInterval destRange = this.dest.getParsedRange();
		Set<RPIndexVar> vars = Stream.of(srcRange, destRange).flatMap(r -> r.getVars().stream()).collect(Collectors.toSet());
		
		/*// CHECKME: is range size>0 already ensured by syntax?
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
		};*/
		Function<RPInterval, String> foo1 = r ->  // FIXME: factor out with above
				  "(assert "
				+ (vars.isEmpty() ? "" : "(exists (" + vars.stream().map(v -> "(" + v.name + " Int)").collect(Collectors.joining(" ")) + ") (and (and")
				+ vars.stream().map(v -> " (>= " + v + " 1)").collect(Collectors.joining(""))  // FIXME: lower bound constant -- replace by global invariant
				+ (vars.isEmpty() ? "" : ")")
				+ " (> " + r.start.toSmt2Formula() + " " + r.end.toSmt2Formula() + ")"
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


		if (this.kind == RPCoreGActionKind.CROSS_TRANSFER)
		{
			if (this.cases.size() > 1)
			{
				String bar = "(assert "
						+ (vars.isEmpty() ? "" : "(exists (" + vars.stream().map(v -> "(" + v.name + " Int)").collect(Collectors.joining(" ")) + ") (and ")
						+ vars.stream().map(v -> " (>= " + v + " 1)").collect(Collectors.joining(""))  // FIXME: lower bound constant -- replace by global invariant
						+ "(not (= (- " + srcRange.end.toSmt2Formula() + " " + srcRange.start.toSmt2Formula() + ") 0))"
						+ (vars.isEmpty() ? "" : "))")
						+ ")";

				job.debugPrintln("\n[param-core] [WF] Checking singleton choice-subject for " + this.src + ":\n  " + bar); 

				if (Z3Wrapper.checkSat(job, gpd, bar))
				{
					return false;
				}
			}
		}
		
		
		if (this.src.getName().equals(this.dest.getName()))
		{
			if (this.kind == RPCoreGActionKind.CROSS_TRANSFER)
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
		}

		return true;
	}

	@Override
	public RPCoreGActionKind getKind()
	{
		return (RPCoreGActionKind) this.kind;
	}
	
	@Override
	public Set<RPIndexedRole> getParamRoles()
	{
		Set<RPIndexedRole> res = Stream.of(this.src, this.dest).collect(Collectors.toSet());
		this.cases.values().forEach(c -> res.addAll(c.getParamRoles()));
		return res;
	}

	@Override
	//public ParamCoreLType project(ParamCoreAstFactory af, Role r, Set<ParamRange> ranges) throws ParamCoreSyntaxException
	public RPCoreLType project(RPCoreAstFactory af, RPRoleVariant subj) throws RPCoreSyntaxException
	{
		//LinkedHashMap<RPCoreMessage, RPCoreLType> projs = new LinkedHashMap<>();
		LinkedHashMap<Message, RPCoreLType> projs = new LinkedHashMap<>();
		//for (Entry<RPCoreMessage, RPCoreGType> e : this.cases.entrySet())
		for (Entry<Message, RPCoreGType> e : this.cases.entrySet())
		{
			//RPCoreMessage a = e.getKey();
			Message a = e.getKey();
			//projs.put(a, e.getValue().project(af, r, ranges));
			projs.put(a, e.getValue().project(af, subj));
					// N.B. local actions directly preserved from globals -- so core-receive also has assertion (cf. ParamGActionTransfer.project, currently no ParamLReceive)
					// FIXME: receive assertion projection -- should not be the same as send?
			
			if (a instanceof MessageSig)
			{
				//for (PayloadElemType<?> pet : a.pay.elems)
				for (PayloadElemType<?> pet : ((MessageSig) a).payload.elems)
				{
					if (pet instanceof GDelegationType)
					{
						GDelegationType gdt = (GDelegationType) pet;  // Payload types come from ParamCoreGProtocolDeclTranslator#parsePayload (toMessage)
						System.out.println("aaa: " + this + ", " + gdt.getGlobalProtocol() + ", " + gdt.getRole());
						
						// cf. GDelegationElem#project
						
						//new LProtocolName();  // FIXME: need actual role (not just role name)
					}
				}
			}
			else
			{
				throw new RuntimeException("[rp-core] TODO: " + a);
			}
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
		
	//private ParamCoreLType merge(ParamCoreAstFactory af, Role r, Set<ParamRange> ranges, Map<ParamCoreMessage, ParamCoreLType> projs) throws ParamCoreSyntaxException
	private RPCoreLType merge(RPCoreAstFactory af, RPRoleVariant r, 
			//Map<RPCoreMessage, RPCoreLType> projs) throws RPCoreSyntaxException
			Map<Message, RPCoreLType> projs) throws RPCoreSyntaxException
	{
		// "Merge"
		Set<RPCoreLType> values = new HashSet<>(projs.values());
		if (values.size() > 1)
		{
			throw new RPCoreSyntaxException("[param-core] Cannot project \n" + this + "\n onto " + r 
					//+ " for " + ranges
					+ ": cannot merge for: " + projs.keySet());
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
