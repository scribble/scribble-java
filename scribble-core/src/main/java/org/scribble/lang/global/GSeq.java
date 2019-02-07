package org.scribble.lang.global;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.InteractionSeq;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.job.Job;
import org.scribble.lang.Seq;
import org.scribble.lang.SessType;
import org.scribble.type.kind.Global;

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
	public GSeq getInlined(Job job)
	{
		GInteractionSeq source = getSource();  // CHECKME: or empty source?
		List<SessType<Global>> elems = this.elems.stream()
				.map(x -> x.getInlined(job)).collect(Collectors.toList());
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
