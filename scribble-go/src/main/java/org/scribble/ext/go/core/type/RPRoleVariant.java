package org.scribble.ext.go.core.type;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.type.index.RPIndexVar;

public class RPRoleVariant extends RPIndexedRole
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final Set<RPInterval> cointervals;
	
	public RPRoleVariant(String name, Set<RPInterval> ranges, Set<RPInterval> coranges)
	{
		super(name, ranges);
		this.cointervals = Collections.unmodifiableSet(coranges);
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

	@Override
	public String toString()
	{
		// Duplicated from super to make rs1 braces mandatory 
		String rs1 = "{" + this.intervals.stream().map(Object::toString).collect(Collectors.joining(", ")) + "}";
		String rs2 = this.cointervals.isEmpty()
				? ""
				: "{" + this.cointervals.stream().map(Object::toString).collect(Collectors.joining(", ")) + "}";
		return super.getLastElement() + rs1 + rs2;
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
