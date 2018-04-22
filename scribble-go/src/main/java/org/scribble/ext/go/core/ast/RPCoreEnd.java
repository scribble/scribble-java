package org.scribble.ext.go.core.ast;

import org.scribble.type.kind.ProtocolKind;

public abstract class RPCoreEnd<K extends ProtocolKind> implements RPCoreType<K>
{
	@Override 
	public String toString()
	{
		return "end";
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof RPCoreEnd))
		{
			return false;
		}
		return ((RPCoreEnd<?>) obj).canEquals(this);
	}

	public abstract boolean canEquals(Object o);

	@Override
	public int hashCode()
	{
		return 31*2447;
	}
}
