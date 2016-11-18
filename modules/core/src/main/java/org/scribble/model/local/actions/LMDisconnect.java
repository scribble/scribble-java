package org.scribble.model.local.actions;

import org.scribble.model.global.actions.GMDisconnect;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

public class LMDisconnect extends LMIOAction
{
	public LMDisconnect(Role peer)
	{
		super(peer, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);  // Must correspond with GDisconnect.UNIT_MESSAGE_SIG_NODE
	}
	
	@Override
	public LMDisconnect toDual(Role self)
	{
		return new LMDisconnect(self);  // return this?
	}

	@Override
	public GMDisconnect toGlobal(Role self)
	{
		return new GMDisconnect(self, this.peer);
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
		if (!(o instanceof LMDisconnect))
		{
			return false;
		}
		return ((LMDisconnect) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof LMDisconnect;
	}

	@Override
	protected String getCommSymbol()
	{
		//return "\u00A1\u00A1";
		return "-/-";
	}
}
