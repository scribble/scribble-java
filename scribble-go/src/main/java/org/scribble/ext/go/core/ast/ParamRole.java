package org.scribble.ext.go.core.ast;

import org.scribble.ext.go.core.ast.global.ParamCoreGChoice;

public class ParamRole
{
	public final String name;
	public final int start;
	public final int end;
	
	public ParamRole(String name, int start, int end)
	{
		this.name = name;
		this.start = start;
		this.end = end;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7121;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.name.hashCode();
		hash = 31 * hash + this.start;
		hash = 31 * hash + this.end;
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof ParamCoreGChoice))
		{
			return false;
		}
		ParamRole them = (ParamRole) obj;
		return this.name.equals(them.name)
				&& this.start == them.start && this.end == them.end;
	}

	@Override
	public String toString()
	{
		return this.name + "[" + this.start + ".." + this.end + "]";
	}
}
