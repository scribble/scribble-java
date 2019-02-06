package org.scribble.lang;

import java.util.Collections;
import java.util.List;

import org.scribble.ast.InteractionSeq;
import org.scribble.type.kind.ProtocolKind;

public abstract class Seq<K extends ProtocolKind> extends SessTypeBase<K>
		implements SessType<K>
{
	public final List<? extends SessType<K>> elems;

	public Seq(InteractionSeq<K> source, List<? extends SessType<K>> elems)
	{
		super(source);
		this.elems = Collections.unmodifiableList(elems);
	}

	@Override
	public int hashCode()
	{
		int hash = 1483;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Seq))
		{
			return false;
		}
		Seq<?> them = (Seq<?>) o;
		return super.equals(this)  // Does canEquals
				&& this.elems.equals(them.elems);
	}
}
