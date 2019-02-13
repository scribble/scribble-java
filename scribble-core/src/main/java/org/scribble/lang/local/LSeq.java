package org.scribble.lang.local;

import java.util.LinkedList;
import java.util.List;

import org.scribble.ast.InteractionSeq;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.lang.Seq;
import org.scribble.lang.SType;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.type.kind.Local;
import org.scribble.type.name.Role;

public class LSeq extends Seq<Local>
		implements LType
{
	// GInteractionSeq or GBlock better as source?
	public LSeq(InteractionSeq<Local> source, List<? extends SType<Local>> elems)
	{
		super(source, elems);
	}

	@Override
	public LSeq reconstruct(InteractionSeq<Local> source,
			List<? extends SType<Local>> elems)
	{
		return new LSeq(source, elems);
	}

	@Override
	public LSeq substitute(Substitutions<Role> subs)
	{
		return (LSeq) super.substitute(subs);
	}

	@Override
	public LSeq getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		LInteractionSeq source = getSource();  // CHECKME: or empty source?
		List<SType<Local>> elems = new LinkedList<>();
		for (SType<Local> e : this.elems)
		{
			SType<Local> e1 = e.getInlined(i);//, stack);
			if (e1 instanceof LSeq)
			{
				elems.addAll(((LSeq) e1).elems);  // Inline GSeq's returned by GDo::getInlined
			}
			else
			{
				elems.add(e1);
			}
		}
		return reconstruct(source, elems);
	}

	@Override
	public LSeq unfoldAllOnce(STypeUnfolder<Local> u)
	{
		LInteractionSeq source = getSource();
		List<SType<Local>> elems = new LinkedList<>();
		for (SType<Local> e : this.elems)
		{
			SType<Local> e1 = e.unfoldAllOnce(u);
			if (e1 instanceof Seq<?>)
			{
				elems.addAll(((Seq<Local>) e1).elems);
			}
			else
			{
				elems.add(e1);
			}
		}
		return reconstruct(source, elems);
	}

	@Override
	public LInteractionSeq getSource()
	{
		return (LInteractionSeq) super.getSource();
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
		if (!(o instanceof LSeq))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LSeq;
	}
}
