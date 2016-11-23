package org.scribble.model.endpoint.actions;

import org.scribble.model.global.actions.SConnect;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class EConnect extends EAction
{
	public EConnect(Role peer, MessageId<?> mid, Payload payload)
	//public Connect(Role peer)
	{
		super(peer, mid, payload);
		//super(peer, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}
	
	@Override
	public EAccept toDual(Role self)
	{
		//return new Accept(self);
		return new EAccept(self, this.mid, this.payload);
	}

	@Override
	public SConnect toGlobal(Role self)
	{
		//return new GConnect(self, this.peer);
		return new SConnect(self, this.peer, this.mid, this.payload);
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
		if (!(o instanceof EConnect))
		{
			return false;
		}
		return ((EConnect) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof EConnect;
	}

	@Override
	protected String getCommSymbol()
	{
		return "!!";
	}
}
