package org.scribble.lang;

import java.util.Collections;
import java.util.List;

import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.Role;

public abstract class Choice<K extends ProtocolKind> implements SessType<K>
{
	public final Role subj;
	public final List<? extends Seq<K>> blocks;  // Pre: size > 0
			// CHECKME: rename?

	public Choice(Role subj, List<? extends Seq<K>> blocks)
	{
		this.subj = subj;
		this.blocks = Collections.unmodifiableList(blocks);
	}

	@Override
	public int hashCode()
	{
		int hash = 1487;
		hash = 31 * hash + this.subj.hashCode();
		hash = 31 * hash + this.blocks.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Choice))
		{
			return false;
		}
		Choice<?> them = (Choice<?>) o;
		return them.canEquals(this) && this.subj.equals(them.subj)
				&& this.blocks.equals(them.blocks);
	}
}
