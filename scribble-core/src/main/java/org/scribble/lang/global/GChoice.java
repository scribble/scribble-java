package org.scribble.lang.global;

import java.util.List;

import org.scribble.lang.Choice;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public class GChoice extends Choice<Global> implements GType
{
	public GChoice(Role subj, List<GSeq> blocks)
	{
		super(subj, blocks);
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
		if (!(o instanceof GChoice))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GChoice;
	}
}
