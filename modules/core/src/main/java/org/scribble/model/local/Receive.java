package org.scribble.model.local;

import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class Receive extends IOAction
{
	public Receive(Role peer, MessageId<?> mid, Payload payload)
	{
		super(peer, mid, payload);
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
		return ((Receive) o).canEqual(this) && super.equals(o);
	}

	public boolean canEqual(Object o)
	{
		return o instanceof Receive;
	}

	@Override
	protected String getCommSymbol()
	{
		return "?";
	}
}
