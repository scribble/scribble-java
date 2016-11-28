package org.scribble.model.endpoint.actions;

import org.scribble.model.global.actions.SAccept;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class EAccept extends EAction
{
	public EAccept(Role peer, MessageId<?> mid, Payload payload)
	//public Accept(Role peer)
	{
		super(peer, mid, payload);
		//super(peer, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}
	
	@Override
	public EConnect toDual(Role self)
	{
		//return new Connect(self);
		return new EConnect(self, this.mid, this.payload);
	}

	@Override
	public SAccept toGlobal(Role self)
	{
		return new SAccept(self, this.peer, this.mid, this.payload);
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
		if (!(o instanceof EAccept))
		{
			return false;
		}
		return ((EAccept) o).canEqual(this) && super.equals(o);
	}

	public boolean canEqual(Object o)
	{
		return o instanceof EAccept;
	}

	@Override
	protected String getCommSymbol()
	{
		return "??";
	}
}
