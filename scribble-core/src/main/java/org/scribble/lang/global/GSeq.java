package org.scribble.lang.global;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.scribble.ast.InteractionSeq;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.lang.Seq;
import org.scribble.lang.SessType;
import org.scribble.lang.SessTypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public class GSeq extends Seq<Global>
		implements GType
{
	// GInteractionSeq or GBlock better as source?
	public GSeq(InteractionSeq<Global> source, List<? extends SessType<Global>> elems)
	{
		super(source, elems);
	}

	@Override
	public GSeq reconstruct(InteractionSeq<Global> source,
			List<? extends SessType<Global>> elems)
	{
		return new GSeq(source, elems);
	}

	@Override
	public GSeq substitute(Substitutions<Role> subs)
	{
		return (GSeq) super.substitute(subs);
	}

	@Override
	public GSeq getInlined(GTypeTranslator t, Deque<SubprotoSig> stack)
	{
		GInteractionSeq source = getSource();  // CHECKME: or empty source?
		List<SessType<Global>> elems = new LinkedList<>();
		for (SessType<Global> e : this.elems)
		{
			SessType<Global> e1 = e.getInlined(t, stack);
			if (e1 instanceof GSeq)
			{
				elems.addAll(((GSeq) e1).elems);  // Inline GSeq's returned by GDo::getInlined
			}
			else
			{
				elems.add(e1);
			}
		}
		return reconstruct(source, elems);
	}

	@Override
	public GSeq unfoldAllOnce(SessTypeUnfolder<Global, ? extends Seq<Global>> u)
	{
		GInteractionSeq source = getSource();
		List<SessType<Global>> elems = new LinkedList<>();
		for (SessType<Global> e : this.elems)
		{
			SessType<Global> e1 = e.unfoldAllOnce(u);
			if (e1 instanceof Seq<?>)
			{
				elems.addAll(((Seq<Global>) e1).elems);
			}
			else
			{
				elems.add(e1);
			}
		}
		return reconstruct(source, elems);
	}

	@Override
	public GInteractionSeq getSource()
	{
		return (GInteractionSeq) super.getSource();
	}
	
	@Override
	public int hashCode()
	{
		int hash = 29;
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
		if (!(o instanceof GSeq))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GSeq;
	}
}
