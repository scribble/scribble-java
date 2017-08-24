package org.scribble.ext.go.core.type;

import org.scribble.ext.go.type.name.ParamRoleParam;

public class ParamRange
{
	public final ParamRoleParam start;
	public final ParamRoleParam end;  // Inclusive
	
	public ParamRange(ParamRoleParam start, ParamRoleParam end)
	{
		this.start = start;
		this.end = end;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7151;
		hash = 31 * hash + this.start.hashCode();
		hash = 31 * hash + this.end.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof ParamRange))
		{
			return false;
		}
		ParamRange them = (ParamRange) obj;
		return this.start.equals(them.start) && this.end.equals(them.end);
	}

	@Override
	public String toString()
	{
		return "[" + this.start + ((this.start == this.end) ? "" : ".." + this.end) + "]";
	}
}
