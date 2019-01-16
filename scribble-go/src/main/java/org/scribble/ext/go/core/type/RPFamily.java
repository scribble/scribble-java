package org.scribble.ext.go.core.type;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ext.go.ast.global.RPGProtocolHeader;
import org.scribble.ext.go.type.annot.RPAnnotExpr;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.ext.go.util.Smt2Translator;
import org.scribble.ext.go.util.Z3Wrapper;

public class RPFamily
{
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	public static final Comparator<RPFamily> COMPARATOR = 
			new Comparator<RPFamily>()
			{
				@Override
				public int compare(RPFamily f1, RPFamily f2)
				{
					return (f1.variants.stream().sorted(RPRoleVariant.COMPARATOR)
							.map(x -> x.toString()).collect(Collectors.joining("_"))
									+ "_not_"
									+ f1.covariants.stream().sorted(RPRoleVariant.COMPARATOR).map(x -> x.toString()).collect(Collectors.joining("_"))
							)
							.compareTo(
									  f2.variants.stream().sorted(RPRoleVariant.COMPARATOR).map(x -> x.toString()).collect(Collectors.joining("_"))
									+ "_not_"
									+ f2.covariants.stream().sorted(RPRoleVariant.COMPARATOR).map(x -> x.toString()).collect(Collectors.joining("_"))
							);
				}
			};

	public final Set<RPRoleVariant> variants;
	public final Set<RPRoleVariant> covariants;
	
	public RPFamily(Set<RPRoleVariant> variants, Set<RPRoleVariant> covariants)
	{
		this.variants = Collections.unmodifiableSet(variants);
		this.covariants = Collections.unmodifiableSet(covariants);
	}

	public boolean isValid(Smt2Translator smt2t, Set<RPIndexVar> vars)
	{
		String smt2 = makeXiSmt2(smt2t, vars);
			
		smt2t.job.debugPrintln("\n[rp-core] Family candidate (" //+ i++ + "/" + size + "): " 
				+ this.variants);
		smt2t.job.debugPrintln("[rp-core] Co-set: " + this.covariants);
		smt2t.job.debugPrintln("[rp-core] Running Z3 on:\n" + smt2);
			
		boolean isSat = Z3Wrapper.checkSat(smt2t.job, smt2t.global, smt2);
		smt2t.job.debugPrintln("[rp-core] Checked sat: " + isSat);
		return isSat;
	}

	// makeFamilyCheck
	public String makeXiSmt2(Smt2Translator smt2t, Set<RPIndexVar> vars)
	{
		Set<RPRoleVariant> cand = this.variants;
		Set<RPRoleVariant> coset = this.covariants;

		List<String> cs = new LinkedList<>();
		cs.addAll(vars.stream().map(x -> smt2t.makeGte(x.toSmt2Formula(smt2t), smt2t.getDefaultBaseValue())).collect(Collectors.toList()));  // FIXME: generalise, parameter domain annotations
		/*cs.addAll(cand.stream().map(v -> makePhiSmt2(v.intervals, v.cointervals, smt2t, false)).collect(Collectors.toList()));
		cs.addAll(coset.stream().map(v -> makePhiSmt2(v.intervals, v.cointervals, smt2t, true)).collect(Collectors.toList()));*/
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
	}

	/*@Override
	public Set<RPIndexVar> getIndexVars()
	{
		Set<RPIndexVar> ivars = super.getIndexVars();
		ivars.addAll(this.cointervals.stream()
				.flatMap(d -> Stream.of(d.start.getVars(), d.end.getVars()).flatMap(vs -> vs.stream()))
				.collect(Collectors.toSet()));
		return ivars;
	}*/

	// if equals, then toString.equals
	@Override
	public String toString()
	{
		return "("
				+ "[" + this.variants.stream().sorted(RPRoleVariant.COMPARATOR).map(Object::toString).collect(Collectors.joining(", ")) + "]"
				+ ", " 
				+ "[" + this.covariants.stream().sorted(RPRoleVariant.COMPARATOR).map(Object::toString).collect(Collectors.joining(", ")) + "]"
				+ ")";
		/*String res = this.variants.stream()
						.sorted(RPRoleVariant.COMPARATOR)
						.map(Object::toString)
						.collect(Collectors.joining("_"));
		return this.covariants.isEmpty()
				? res
				: "_not_" + this.covariants.stream()
						.sorted(RPRoleVariant.COMPARATOR)
						.map(Object::toString)
						.collect(Collectors.joining("_"));*/
	}
	
	@Override
	public int hashCode()
	{
		int hash = 2027;
		hash = 31 * hash + this.variants.hashCode();
		hash = 31 * hash + this.covariants.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPFamily))
		{
			return false;
		}
		RPFamily them = (RPFamily) obj;
		return them.canEqual(this)
				&& this.variants.equals(them.variants) && this.covariants.equals(them.covariants);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof RPFamily;
	}
}
