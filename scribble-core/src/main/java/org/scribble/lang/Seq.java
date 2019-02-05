package org.scribble.lang;

import java.util.Collections;
import java.util.List;

import org.scribble.type.kind.ProtocolKind;

public abstract class Seq<K extends ProtocolKind> implements SessType<K>
{
	public final List<? extends SessType<K>> elems;

	public Seq(List<? extends SessType<K>> elems)
	{
		this.elems = Collections.unmodifiableList(elems);
	}

	@Override
	public int hashCode()
	{
		int hash = 1483;
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof Seq))
		{
			return false;
		}
		Seq<?> them = (Seq<?>) obj;
		return them.canEquals(this) && this.elems.equals(them.elems);
	}
}
