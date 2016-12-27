package org.scribble.f17.ast.local.action;

import org.scribble.f17.ast.F17Action;
import org.scribble.sesstype.name.Role;


public abstract class F17LAction extends F17Action
{
	public F17LAction(Role self)  // FIXME: here self==subj, but formally dest==subj
	{
		super(self);
	}
	
	public boolean isOutput()
	{
		return false;
	}
	
	public boolean isInput()
	{
		return false;
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof F17LAction))
		{
			return false;
		}
		return super.equals(obj);
	}

	protected abstract boolean canEquals(Object o);
}
