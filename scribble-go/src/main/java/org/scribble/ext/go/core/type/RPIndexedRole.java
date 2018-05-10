package org.scribble.ext.go.core.type;

import java.util.Collections;
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
	
	public RPIndexedRole(String name, Set<RPInterval> intervals)
	{
		super(name);
		this.intervals = Collections.unmodifiableSet(intervals);
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
