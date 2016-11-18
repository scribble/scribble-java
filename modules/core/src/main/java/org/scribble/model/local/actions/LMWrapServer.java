package org.scribble.model.local.actions;

import org.scribble.model.global.actions.GMWrapServer;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

// Duplicated from Disconnect
public class LMWrapServer extends LMIOAction
{
	public LMWrapServer(Role peer)
	{
		super(peer, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);  // Must correspond with GWrap.UNIT_MESSAGE_SIG_NODE
	}
	
	@Override
	public LMWrapClient toDual(Role self)
	{
		return new LMWrapClient(self);
	}

	@Override
	public GMWrapServer toGlobal(Role self)
	{
		return new GMWrapServer(self, this.peer);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1063;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
	
	@Override
	public boolean isWrapServer()
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
		if (!(o instanceof LMWrapServer))
		{
			return false;
		}
		return ((LMWrapServer) o).canEqual(this) && super.equals(o);
	}

	public boolean canEqual(Object o)
	{
		return o instanceof LMWrapServer;
	}

	@Override
	protected String getCommSymbol()
	{
		return "(??)";
	}
}
