package org.scribble.ext.go.core.ast;


public abstract class ParamCoreEnd implements ParamCoreType
{
	@Override 
	public String toString()
	{
		return "end";
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ParamCoreEnd))
		{
			return false;
		}
		return ((ParamCoreEnd) obj).canEquals(this);
	}

	public abstract boolean canEquals(Object o);

	@Override
	public int hashCode()
	{
		return 31*2447;
	}
}
