package org.scribble.ext.go.core.ast;

import org.scribble.type.kind.ProtocolKind;

public abstract class RPCoreCont<K extends ProtocolKind> implements RPCoreType<K>
{
	@Override 
	public String toString()
	{
		return "cont";
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof RPCoreCont))
		{
			return false;
		}
		return ((RPCoreCont<?>) obj).canEquals(this);
	}

	public abstract boolean canEquals(Object o);

	@Override
	public int hashCode()
	{
		return 31*2777;
	}
}
