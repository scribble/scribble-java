package org.scribble2.fsm;

import org.scribble2.sesstype.name.MessageId;
import org.scribble2.sesstype.name.Role;

public class Receive extends IOAction
{
	public Receive(Role peer, MessageId mid)
	{
		super(peer, mid);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 937;
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
		if (!(o instanceof Receive))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	protected String getCommSymbol()
	{
		return "?";
	}
}
