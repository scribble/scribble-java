package org.scribble.ext.go.core.type;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ParamActualRole extends ParamRole
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final Set<ParamRange> coranges;
	
	public ParamActualRole(String name, Set<ParamRange> ranges, Set<ParamRange> coranges)
	{
		super(name, ranges);
		this.coranges = Collections.unmodifiableSet(coranges);
	}

	@Override
	public String toString()
	{
		// Duplicated from super to make braces mandatory
		String rs1 = "{" + this.ranges.stream().map(Object::toString).collect(Collectors.joining(", ")) + "}";
		String rs2 = "{" + this.coranges.stream().map(Object::toString).collect(Collectors.joining(", ")) + "}";
		return super.getLastElement() + rs1 + rs2;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7193;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.coranges.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof ParamActualRole))
		{
			return false;
		}
		ParamActualRole them = (ParamActualRole) obj;
		return super.equals(them)  // Does canEqual
				&& this.coranges.equals(them.coranges);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof ParamActualRole;
	}
}
