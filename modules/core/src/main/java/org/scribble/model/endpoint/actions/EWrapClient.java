package org.scribble.model.endpoint.actions;

import org.scribble.model.global.actions.SWrapClient;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

// Duplicated from Disconnect
public class EWrapClient extends EAction
{
	public EWrapClient(Role peer)
	{
		super(peer, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);  // Must correspond with GWrap.UNIT_MESSAGE_SIG_NODE
	}
	
	@Override
	public EWrapServer toDual(Role self)
	{
		return new EWrapServer(self);
	}

	@Override
	public SWrapClient toGlobal(Role self)
	{
		return new SWrapClient(self, this.peer);
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
		if (!(o instanceof EWrapClient))
		{
			return false;
		}
		return ((EWrapClient) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof EWrapClient;
	}

	@Override
	protected String getCommSymbol()
	{
		return "(!!)";
	}
}
