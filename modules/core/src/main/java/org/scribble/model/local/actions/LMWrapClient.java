package org.scribble.model.local.actions;

import org.scribble.model.global.actions.GMWrapClient;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

// Duplicated from Disconnect
public class LMWrapClient extends LMIOAction
{
	public LMWrapClient(Role peer)
	{
		super(peer, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);  // Must correspond with GWrap.UNIT_MESSAGE_SIG_NODE
	}
	
	@Override
	public LMWrapServer toDual(Role self)
	{
		return new LMWrapServer(self);
	}

	@Override
	public GMWrapClient toGlobal(Role self)
	{
		return new GMWrapClient(self, this.peer);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1061;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
	
	@Override
	public boolean isWrapClient()
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
		if (!(o instanceof LMWrapClient))
		{
			return false;
		}
		return ((LMWrapClient) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof LMWrapClient;
	}

	@Override
	protected String getCommSymbol()
	{
		return "(!!)";
	}
}
