package org.scribble.ext.go.core.ast;

import org.scribble.ext.go.core.type.ParamRange;

public class ParamRole
{
	public final String name;
	public final ParamRange range;
	
	public ParamRole(String name, ParamRange range)
	{
		this.name = name;
		this.range = range;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7121;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.name.hashCode();
		hash = 31 * hash + this.range.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof ParamRole))
		{
			return false;
		}
		ParamRole them = (ParamRole) obj;
		return this.name.equals(them.name)
				&& this.range.equals(them.range);
	}

	@Override
	public String toString()
	{
		return this.name + this.range;
	}
}
