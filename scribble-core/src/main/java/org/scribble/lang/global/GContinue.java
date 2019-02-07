package org.scribble.lang.global;

import org.scribble.job.Job;
import org.scribble.lang.Continue;
import org.scribble.type.kind.Global;
import org.scribble.type.name.RecVar;

public class GContinue extends Continue<Global> implements GType
{
	public GContinue(org.scribble.ast.Continue<Global> source, RecVar recvar)
	{
		super(source, recvar);
	}
	
	@Override
	public GContinue reconstruct(org.scribble.ast.Continue<Global> source,
			RecVar recvar)
	{
		return new GContinue(source, recvar);
	}

	@Override
	public GContinue getInlined(Job job)
	{
		org.scribble.ast.global.GContinue source =
				(org.scribble.ast.global.GContinue) getSource();  // CHECKME: or empty source?
		return new GContinue(source, this.recvar);
	}
	
	@Override
	public org.scribble.ast.global.GContinue getSource()
	{
		return (org.scribble.ast.global.GContinue) super.getSource();
	}

	@Override
	public int hashCode()
	{
		int hash = 3457;
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
		if (!(o instanceof GContinue))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GContinue;
	}
}
