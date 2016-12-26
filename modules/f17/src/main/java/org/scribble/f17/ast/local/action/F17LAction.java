package org.scribble.f17.ast.local.action;

import org.scribble.f17.ast.F17Action;
import org.scribble.sesstype.name.Role;


public abstract class F17LAction extends F17Action
{
	public final Role src;
	
	public F17LAction(Role src)
	{
		super(src);
		this.src = src;
	}
	
	@Override
	public String toString()
	{
		return this.src.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + this.src.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof F17LAction))
		{
			return false;
		}
		F17LAction them = (F17LAction) obj;
		return them.canEquals(this) && this.src.equals(them.src);
	}

	protected abstract boolean canEquals(Object o);
}
