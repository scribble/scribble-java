package org.scribble.ext.go.core.type;

public class ParamRange
{
	public final int start;
	public final int end;  // Inclusive
	
	public ParamRange(int start, int end)
	{
		this.start = start;
		this.end = end;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7151;
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
		if (!(obj instanceof ParamRange))
		{
			return false;
		}
		ParamRange them = (ParamRange) obj;
		return this.start == them.start && this.end == them.end;
	}

	@Override
	public String toString()
	{
		return "[" + this.start + ".." + this.end + "]";
	}
}
