package org.scribble.lang.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.lang.Choice;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.type.kind.Local;
import org.scribble.type.name.Role;

public class LChoice extends Choice<Local, LSeq> implements LType
{
	public LChoice(org.scribble.ast.Choice<Local> source, Role subj,
			List<LSeq> blocks)
	{
		super(source, subj, blocks);
	}
	
	@Override
	public LChoice reconstruct(org.scribble.ast.Choice<Local> source, Role subj,
			List<LSeq> blocks)
	{
		return new LChoice(source, subj, blocks);
	}

	@Override
	public LChoice substitute(Substitutions<Role> subs)
	{
		List<LSeq> blocks = this.blocks.stream().map(x -> x.substitute(subs))
				.collect(Collectors.toList());
		return reconstruct(getSource(), subs.apply(this.subj), blocks);
	}

	@Override
	public LChoice getInlined(STypeInliner i )//GTypeTranslator t, Deque<SubprotoSig> stack)
	{
		org.scribble.ast.local.LChoice source = getSource();  // CHECKME: or empty source?
		List<LSeq> blocks = this.blocks.stream().map(x -> x.getInlined(i))
				.collect(Collectors.toList());
		return reconstruct(source, this.subj, blocks);
	}

	@Override
	public LChoice unfoldAllOnce(STypeUnfolder<Local> u)
	{
		org.scribble.ast.local.LChoice source = getSource();  // CHECKME: or empty source?
		List<LSeq> blocks = this.blocks.stream().map(x -> x.unfoldAllOnce(u))
				.collect(Collectors.toList());
		return reconstruct(source, this.subj, blocks);
	}
	
	@Override
	public org.scribble.ast.local.LChoice getSource()
	{
		return (org.scribble.ast.local.LChoice) super.getSource();
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
		if (!(o instanceof LChoice))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LChoice;
	}
}
