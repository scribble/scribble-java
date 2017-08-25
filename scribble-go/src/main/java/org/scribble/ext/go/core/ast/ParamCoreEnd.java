package org.scribble.ext.go.core.ast;

import org.scribble.type.kind.ProtocolKind;

public abstract class ParamCoreEnd<K extends ProtocolKind> implements ParamCoreType<K>
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
		return ((ParamCoreEnd<?>) obj).canEquals(this);
	}

	public abstract boolean canEquals(Object o);

	@Override
	public int hashCode()
	{
		return 31*2447;
	}
}
