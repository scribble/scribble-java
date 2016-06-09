package org.scribble.model.local;

import org.scribble.model.global.GWrapClient;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

// Duplicated from Disconnect
public class WrapClient extends IOAction
{
	public WrapClient(Role peer)
	{
		super(peer, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);  // Must correspond with GWrap.UNIT_MESSAGE_SIG_NODE
	}
	
	@Override
	public WrapServer toDual(Role self)
	{
		return new WrapServer(self);
	}

	@Override
	public GWrapClient toGlobal(Role self)
	{
		return new GWrapClient(self, this.peer);
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
		if (!(o instanceof WrapClient))
		{
			return false;
		}
		return ((WrapClient) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof WrapClient;
	}

	@Override
	protected String getCommSymbol()
	{
		return "(!!)";
	}
}
