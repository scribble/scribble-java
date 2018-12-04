package org.scribble.ext.go.core.type;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.ast.global.RPGProtocolHeader;
import org.scribble.ext.go.type.annot.RPAnnotExpr;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.ext.go.util.Smt2Translator;
import org.scribble.ext.go.util.Z3Wrapper;

public class RPRoleVariant extends RPIndexedRole
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final
	Comparator<RPRoleVariant> COMPARATOR = new Comparator<RPRoleVariant>()
			{
				@Override
				public int compare(RPRoleVariant i1, RPRoleVariant i2)
				{
					return i1.toString().compareTo(i2.toString());
				}
			};
	
	public final Set<RPInterval> cointervals;
	
	public RPRoleVariant(String name, Set<RPInterval> ranges, Set<RPInterval> coranges)
	{
		super(name, ranges);
		this.cointervals = Collections.unmodifiableSet(coranges);
	}
	
	public boolean isValid(Smt2Translator smt2t)
	{
		Set<RPInterval> cand = this.intervals;
		Set<RPInterval> coset = this.cointervals;
		String z3 = makePhiSmt2(smt2t, false);
		List<RPIndexVar> vars = Stream.concat(
					cand.stream().flatMap(c -> c.getIndexVars().stream()),
					coset.stream().flatMap(c -> c.getIndexVars().stream())
				).distinct().collect(Collectors.toList());
		if (!vars.isEmpty())
		{
			//z3 = "(exists (" + vars.stream().map(p -> "(" + p + " Int)").collect(Collectors.joining(" ")) + ") " + z3 + ")";
			List<String> tmp = vars.stream().map(v -> smt2t.makeLte(smt2t.getDefaultBaseValue(), v.toSmt2Formula(smt2t))).collect(Collectors.toList());

			if (smt2t.global.header instanceof RPGProtocolHeader)
			{
				// TODO FIXME: WIP
				RPAnnotExpr annot = RPAnnotExpr.parse(((RPGProtocolHeader) smt2t.global.header).annot);
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
		
		smt2t.job.debugPrintln("\n[rp-core] Variant candidate (" //+ i++ + "/" + size + "): " 
				+ cand);
		smt2t.job.debugPrintln("[rp-core] Co-set: " + coset);
		smt2t.job.debugPrintln("[rp-core] Running Z3 on:\n" + z3);
		
		boolean isSat = Z3Wrapper.checkSat(smt2t.job, smt2t.global, z3);
		/*if (isSat)
		{
			//protoRoles.get(r).add(cand);
			variants.get(r).add(new RPRoleVariant(r.toString(), cand, coset));
		}*/
		smt2t.job.debugPrintln("[rp-core] Checked sat: " + isSat);
		return isSat;
	}

	// Doesn't include "assert", nor exists-bind index vars (but does exists-bind self)
	public String makePhiSmt2(Smt2Translator smt2t, boolean not)
	{
		Set<RPInterval> cand = this.intervals;
		Set<RPInterval> coset = this.cointervals; 

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
			}*/
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
		}*/

		String z3 = //"(exists ((self Int))" + z3 + ")";
				smt2t.makeExists(Stream.of("self").collect(Collectors.toList()), smt2t.makeAnd(cs));  // CHECKME: need explicit self >= 1?
		if (not)
		{
			z3 = smt2t.makeNot(z3);
		}
		dom.add(z3);
		z3 = smt2t.makeAnd(dom);

		return z3;
	}

	// self is irrelevant -- variants don't contain self
	@Override
	public RPRoleVariant minimise(int self)
	{
		Set<RPInterval> ivals = this.intervals.stream().map(x -> x.minimise(self)).collect(Collectors.toSet());
		Optional<RPInterval> o = ivals.stream().filter(x -> x.isSingleton()).findFirst();
		if (o.isPresent())  // FIXME: more general interval "merge"?
		{
			ivals = Stream.of(o.get()).collect(Collectors.toSet());
			return new RPRoleVariant(this.getLastElement(), ivals, Collections.emptySet());
		}
		Set<RPInterval> coivals = this.cointervals.stream().map(x -> x.minimise(self)).collect(Collectors.toSet());
		return new RPRoleVariant(this.getLastElement(), ivals, coivals);
	}
	
	/*@Override
	public boolean isSingleton()
	{
		return super.isSingleton();
	}*/

	@Override
	public Set<RPIndexVar> getIndexVars()
	{
		Set<RPIndexVar> ivars = super.getIndexVars();
		ivars.addAll(this.cointervals.stream()
				.flatMap(d -> Stream.of(d.start.getVars(), d.end.getVars()).flatMap(vs -> vs.stream()))
				.collect(Collectors.toSet()));
		return ivars;
	}

	// if equals, then toString.equals
	@Override
	public String toString()
	{
		// Duplicated from super to make rs1 braces mandatory 
		/*String rs1 = "{" + this.intervals.stream().map(Object::toString).collect(Collectors.joining(", ")) + "}";
		String rs2 = this.cointervals.isEmpty()
				? ""
				: "{" + this.cointervals.stream().map(Object::toString).collect(Collectors.joining(", ")) + "}";*/
		String rs1 = "{" + this.intervals.stream().sorted(RPInterval.COMPARATOR).map(Object::toString).collect(Collectors.joining(", ")) + "}";
		String rs2 = this.cointervals.isEmpty()
				? ""
				: "{" + this.cointervals.stream().sorted(RPInterval.COMPARATOR).map(Object::toString).collect(Collectors.joining(", ")) + "}";
		return getName() + rs1 + rs2;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7193;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.cointervals.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPRoleVariant))
		{
			return false;
		}
		RPRoleVariant them = (RPRoleVariant) obj;
		return super.equals(them)  // Does canEqual
				&& this.cointervals.equals(them.cointervals);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof RPRoleVariant;
	}
}
