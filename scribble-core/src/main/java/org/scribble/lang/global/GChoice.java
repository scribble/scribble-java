package org.scribble.lang.global;

import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.lang.Choice;
import org.scribble.lang.Seq;
import org.scribble.lang.SessTypeUnfolder;
import org.scribble.lang.Substitutions;
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
	public GChoice substitute(Substitutions<Role> subs)
	{
		List<GSeq> blocks = this.blocks.stream().map(x -> x.substitute(subs))
				.collect(Collectors.toList());
		return reconstruct(getSource(), subs.apply(this.subj), blocks);
	}

	@Override
	public GChoice getInlined(GTypeTranslator t, Deque<SubprotoSig> stack)
	{
		org.scribble.ast.global.GChoice source = getSource();  // CHECKME: or empty source?
		List<GSeq> blocks = this.blocks.stream().map(x -> x.getInlined(t, stack))
				.collect(Collectors.toList());
		return reconstruct(source, this.subj, blocks);
	}

	@Override
	public GChoice unfoldAllOnce(SessTypeUnfolder<Global, ? extends Seq<Global>> u)
	{
		org.scribble.ast.global.GChoice source = getSource();  // CHECKME: or empty source?
		List<GSeq> blocks = this.blocks.stream().map(x -> x.unfoldAllOnce(u))
				.collect(Collectors.toList());
		return reconstruct(source, this.subj, blocks);
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
