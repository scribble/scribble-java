package org.scribble.model.local;

import org.scribble.model.global.GDisconnect;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

public class Disconnect extends IOAction
{
	public Disconnect(Role peer)
	{
		super(peer, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);  // Must correspond with GDisconnect.UNIT_MESSAGE_SIG_NODE
	}
	
	@Override
	public Disconnect toDual(Role self)
	{
		return new Disconnect(self);  // return this?
	}

	@Override
	public GDisconnect toGlobal(Role self)
	{
		return new GDisconnect(self, this.peer);
	}
	
	@Override
	public boolean isDisconnect()
	{
		return true;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1009;
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
		if (!(o instanceof Disconnect))
		{
			return false;
		}
		return ((Disconnect) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof Disconnect;
	}

	@Override
	protected String getCommSymbol()
	{
		//return "\u00A1\u00A1";
		return "-/-";
	}
}
