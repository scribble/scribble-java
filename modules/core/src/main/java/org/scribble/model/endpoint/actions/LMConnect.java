package org.scribble.model.endpoint.actions;

import org.scribble.model.global.actions.GMConnect;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class LMConnect extends LMIOAction
{
	public LMConnect(Role peer, MessageId<?> mid, Payload payload)
	//public Connect(Role peer)
	{
		super(peer, mid, payload);
		//super(peer, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}
	
	@Override
	public LMAccept toDual(Role self)
	{
		//return new Accept(self);
		return new LMAccept(self, this.mid, this.payload);
	}

	@Override
	public GMConnect toGlobal(Role self)
	{
		//return new GConnect(self, this.peer);
		return new GMConnect(self, this.peer, this.mid, this.payload);
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
		if (!(o instanceof LMConnect))
		{
			return false;
		}
		return ((LMConnect) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof LMConnect;
	}

	@Override
	protected String getCommSymbol()
	{
		return "!!";
	}
}
