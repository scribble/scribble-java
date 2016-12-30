package org.scribble.ext.f17.lts.action;

import org.scribble.ext.f17.ast.local.action.F17LAction;
import org.scribble.ext.f17.ast.local.action.F17LSend;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

public class F17LBot extends F17LSend
{
	public static final F17LBot BOT = new F17LBot();
	
	private F17LBot()
	{
		super(Role.EMPTY_ROLE, Role.EMPTY_ROLE, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}

	@Override
	public F17LAction toDual()
	{
		//throw new RuntimeException("Shouldn't get in here: " + this);
		return this;
	}
	
	@Override
	public String toString()
	{
		return "#";
	} 

	@Override
	public int hashCode()
	{
		int hash = 2273;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof F17LBot))
		{
			return false;
		}
		return super.equals(obj);
	}
	
	@Override
	protected boolean canEquals(Object o)
	{
		return o instanceof F17LBot;
	}
}