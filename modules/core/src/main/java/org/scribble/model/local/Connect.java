package org.scribble.model.local;

import org.scribble.model.global.GConnect;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class Connect extends IOAction
{
	public Connect(Role peer, MessageId<?> mid, Payload payload)
	//public Connect(Role peer)
	{
		super(peer, mid, payload);
		//super(peer, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}
	
	@Override
	public Accept toDual(Role self)
	{
		//return new Accept(self);
		return new Accept(self, this.mid, this.payload);
	}

	@Override
	public GConnect toGlobal(Role self)
	{
		//return new GConnect(self, this.peer);
		return new GConnect(self, this.peer, this.mid, this.payload);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 929;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
	
	@Override
	public boolean isConnect()
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
		if (!(o instanceof Connect))
		{
			return false;
		}
		return ((Connect) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof Connect;
	}

	@Override
	protected String getCommSymbol()
	{
		return "!!";
	}
}
