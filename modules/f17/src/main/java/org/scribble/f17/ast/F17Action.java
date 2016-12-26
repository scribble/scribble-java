package org.scribble.f17.ast;

import org.scribble.sesstype.name.Role;


public abstract class F17Action
{
	public final Role subj;
	
	public F17Action(Role src)
	{
		this.subj = src;
	}
	
	@Override
	public String toString()
	{
		return this.subj.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + this.subj.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof F17Action))
		{
			return false;
		}
		F17Action them = (F17Action) obj;
		return them.canEquals(this) && this.subj.equals(them.subj);
	}

	protected abstract boolean canEquals(Object o);
}
