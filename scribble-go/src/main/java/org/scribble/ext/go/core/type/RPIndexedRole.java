package org.scribble.ext.go.core.type;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.type.name.Role;

// FIXME: make distinct kind?  i.e., don't extend Role? -- yes: ParamRole is not a Role (Role should actually be a ParamRole in that sense)
public class RPIndexedRole extends Role
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//public final ParamRange range;
	public final Set<RPInterval> intervals;  // size >= 1 -- size == 1 for parsed syntax
			// FIXME: this set was meant for multidim nat intervals, but multidim should be factored into the RPInterval itself
			// No, above is wrong -- this set is actually for multiple intervals, but currently only non-singleton at local level (i.e, variants)
	
	public RPIndexedRole(String name, Set<? extends RPInterval> intervals)
	{
		super(name);
		/*if (intervals.size() != 1) 
			// FIXME: this set was meant for multidim nat intervals, but multidim should be factored into the RPInterval itself
			// No: above is true for parsed globals, but Set needed for variants (subclass of this)
		{
			throw new RuntimeException("TODO: " + intervals);
		}*/
		this.intervals = Collections.unmodifiableSet(intervals);
	}
	
	// FIXME: rename
	public RPIndexedRole minimise(int self)
	{
		Set<RPInterval> ivals = this.intervals.stream().map(x -> x.minimise(self)).collect(Collectors.toSet());
		Optional<RPInterval> o = ivals.stream().filter(x -> x.isSingleton()).findFirst();
		if (o.isPresent())  // FIXME: more general interval "merge"?
		{
			ivals = Stream.of(o.get()).collect(Collectors.toSet());
		}
		return new RPIndexedRole(this.getLastElement(), ivals);
	}
	
	// FIXME: currently just a syntactic singleton check on a single interval -- false negatives possible in general case (multiple intervals)
	public boolean isSingleton()
	{
		/*if (this.intervals.size() != 1)
		{
			return false;
		}
		RPInterval ival = this.intervals.stream().findFirst().get();
		return ival.isSingleton();*/
		return this.intervals.stream().anyMatch(x -> x.isSingleton());
	}

	public Set<RPIndexVar> getIndexVars()
	{
		return this.intervals.stream()
				.flatMap(d -> Stream.of(d.start.getVars(), d.end.getVars()).flatMap(vs -> vs.stream()))
				.collect(Collectors.toSet());
	}
	
	public RPInterval getParsedRange()
	{
		if (this.intervals.size() > 1)
		{
			throw new RuntimeException("[param-core] Shouldn't get in here: " + this.intervals);
		}
		return this.intervals.iterator().next();
	}
	
	// FIXME
	public Role getName()
	{
		return new Role(this.getLastElement());
	}

	@Override
	public String toString()
	{
		String rs = this.intervals.stream().map(Object::toString).collect(Collectors.joining(", "));
		if (this.intervals.size() > 1)
		{
			rs = "{" + rs + "}";
		}
		return super.toString() + rs;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7121;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.intervals.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPIndexedRole))
		{
			return false;
		}
		RPIndexedRole them = (RPIndexedRole) obj;
		return super.equals(them)  // Does canEqual
				&& this.intervals.equals(them.intervals);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof RPIndexedRole;
	}
}
