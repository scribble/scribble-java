package org.scribble.lang;

import org.scribble.ast.ScribNodeBase;
import org.scribble.type.kind.ProtocolKind;

// SessTypeBase is to SessType as ScribNodeBase is to ScribNode
public abstract class SessTypeBase<K extends ProtocolKind> implements SessType<K>
{
	private final ScribNodeBase source;

	public SessTypeBase(ScribNodeBase source)
	{
		this.source = source;
	}

	@Override
	public boolean hasSource()
	{
		return this.source != null;
	}
	
	// Pre: hasSource
	@Override
	public ScribNodeBase getSource()
	{
		return this.source;
	}

	// Does *not* include this.source -- equals/hashCode is for "surface-level" syntactic equality of SessType only
	@Override
	public int hashCode()
	{
		int hash = 1871;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof SessTypeBase))
		{
			return false;
		}
		SessTypeBase<?> them = (SessTypeBase<?>) o;
		return them.canEquals(this);
	}
}
