package org.scribble.lang;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.type.kind.ProtocolKind;

// SessTypeBase is to SessType as ScribNodeBase is to ScribNode
public abstract class SessTypeBase<K extends ProtocolKind>
		implements SessType<K>
{
	private final ProtocolKindNode<K> source;  // Currently null for "generated" terms (cf. hasSource)

	public SessTypeBase(ProtocolKindNode<K> source)
	{
		@SuppressWarnings("unchecked")  // FIXME:
		ProtocolKindNode<K> clone = (ProtocolKindNode<K>) source.clone();  // ScribNodes are mutable
		this.source = clone;
	}

	@Override
	public boolean hasSource()
	{
		return this.source != null;
	}
	
	// Pre: hasSource
	@Override
	public ProtocolKindNode<K> getSource()
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
