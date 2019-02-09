package org.scribble.lang.global;

import java.util.Deque;

import org.scribble.job.Job;
import org.scribble.lang.Recursion;
import org.scribble.type.SubprotoSig;
import org.scribble.type.kind.Global;
import org.scribble.type.name.RecVar;

public class GRecursion extends Recursion<Global, GSeq> implements GType
{
	public GRecursion(org.scribble.ast.Recursion<Global> source, RecVar recvar,
			GSeq body)
	{
		super(source, recvar, body);
	}
	
	@Override
	public GRecursion reconstruct(org.scribble.ast.Recursion<Global> source,
			RecVar recvar, GSeq block)
	{
		return new GRecursion(source, recvar, block);
	}

	@Override
	public GRecursion getInlined(Job job, Deque<SubprotoSig> stack)
	{
		org.scribble.ast.global.GRecursion source =
				(org.scribble.ast.global.GRecursion) getSource();  // CHECKME: or empty source?
		GSeq body = this.body.getInlined(job, stack);
		return new GRecursion(source, this.recvar, body);
	}
	
	@Override
	public org.scribble.ast.global.GRecursion getSource()
	{
		return (org.scribble.ast.global.GRecursion) super.getSource();
	}

	@Override
	public int hashCode()
	{
		int hash = 2309;
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
		if (!(o instanceof GRecursion))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GRecursion;
	}
}
