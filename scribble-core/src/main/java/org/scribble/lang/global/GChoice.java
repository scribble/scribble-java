package org.scribble.lang.global;

import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.job.Job;
import org.scribble.lang.Choice;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public class GChoice extends Choice<Global, GSeq> implements GType
{
	public GChoice(org.scribble.ast.Choice<Global> source, Role subj,
			List<GSeq> blocks)
	{
		super(source, subj, blocks);
	}
	
	@Override
	public GChoice reconstruct(org.scribble.ast.Choice<Global> source, Role subj,
			List<GSeq> blocks)
	{
		return new GChoice(source, subj, blocks);
	}

	@Override
	public GChoice getInlined(Job job, Deque<SubprotoSig> stack)
	{
		org.scribble.ast.global.GChoice source =
				(org.scribble.ast.global.GChoice) getSource();  // CHECKME: or empty source?
		List<GSeq> collect = this.blocks.stream().map(x -> x.getInlined(job, stack))
				.collect(Collectors.toList());
		return new GChoice(source, this.subj, collect);
	}
	
	@Override
	public org.scribble.ast.global.GChoice getSource()
	{
		return (org.scribble.ast.global.GChoice) super.getSource();
	}

	@Override
	public int hashCode()
	{
		int hash = 3067;
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
