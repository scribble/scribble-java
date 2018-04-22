package org.scribble.ext.go.core.type;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class RPRoleVariant extends RPIndexedRole
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final Set<RPInterval> coranges;
	
	public RPRoleVariant(String name, Set<RPInterval> ranges, Set<RPInterval> coranges)
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
		if (!(obj instanceof RPRoleVariant))
		{
			return false;
		}
		RPRoleVariant them = (RPRoleVariant) obj;
		return super.equals(them)  // Does canEqual
				&& this.coranges.equals(them.coranges);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof RPRoleVariant;
	}
}
