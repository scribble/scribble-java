package org.scribble.model.local;

import org.scribble.model.global.GAccept;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class Accept extends IOAction
{
	public Accept(Role peer, MessageId<?> mid, Payload payload)
	//public Accept(Role peer)
	{
		super(peer, mid, payload);
		//super(peer, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}
	
	@Override
	public Connect toDual(Role self)
	{
		//return new Connect(self);
		return new Connect(self, this.mid, this.payload);
	}

	@Override
	public GAccept toGlobal(Role self)
	{
		return new GAccept(self, this.peer, this.mid, this.payload);
		//return new GAccept(self, this.peer);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 937;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
	
	@Override
	public boolean isAccept()
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
		if (!(o instanceof Accept))
		{
			return false;
		}
		return ((Accept) o).canEqual(this) && super.equals(o);
	}

	public boolean canEqual(Object o)
	{
		return o instanceof Accept;
	}

	@Override
	protected String getCommSymbol()
	{
		return "??";
	}
}
