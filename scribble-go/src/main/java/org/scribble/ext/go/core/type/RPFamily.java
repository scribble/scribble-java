package org.scribble.ext.go.core.type;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

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
