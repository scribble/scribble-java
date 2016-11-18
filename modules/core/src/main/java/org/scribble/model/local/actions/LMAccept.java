package org.scribble.model.local.actions;

import org.scribble.model.global.actions.GMAccept;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class LMAccept extends LMIOAction
{
	public LMAccept(Role peer, MessageId<?> mid, Payload payload)
	//public Accept(Role peer)
	{
		super(peer, mid, payload);
		//super(peer, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}
	
	@Override
	public LMConnect toDual(Role self)
	{
		//return new Connect(self);
		return new LMConnect(self, this.mid, this.payload);
	}

	@Override
	public GMAccept toGlobal(Role self)
	{
		return new GMAccept(self, this.peer, this.mid, this.payload);
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
		if (!(o instanceof LMAccept))
		{
			return false;
		}
		return ((LMAccept) o).canEqual(this) && super.equals(o);
	}

	public boolean canEqual(Object o)
	{
		return o instanceof LMAccept;
	}

	@Override
	protected String getCommSymbol()
	{
		return "??";
	}
}
