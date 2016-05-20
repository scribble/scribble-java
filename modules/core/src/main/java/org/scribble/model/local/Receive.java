package org.scribble.model.local;

import org.scribble.model.global.GModelAction;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class Receive extends IOAction
{
	public Receive(Role peer, MessageId<?> mid, Payload payload)
	{
		super(peer, mid, payload);
	}
	
	public Send toDual(Role self)
	{
		return new Send(self, this.mid, this.payload);
	}

	@Override
	public GModelAction toGlobal(Role self)
	{
		return new GModelAction(this.peer, self, this.mid, this.payload);
	}
	
	/*@Override
	public int hashCode()
	{
		int hash = 937;
		hash = 31 * hash + super.hashCode();
		return hash;
	}*/
	
	@Override
	public boolean isReceive()
	{
		return true;
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
