package org.scribble.model.endpoint.actions;

import org.scribble.model.global.actions.SWrapServer;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

// Duplicated from Disconnect
public class EWrapServer extends EAction
{
	public EWrapServer(Role peer)
	{
		super(peer, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);  // Must correspond with GWrap.UNIT_MESSAGE_SIG_NODE
	}
	
	@Override
	public EWrapClient toDual(Role self)
	{
		return new EWrapClient(self);
	}

	@Override
	public SWrapServer toGlobal(Role self)
	{
		return new SWrapServer(self, this.peer);
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
		if (!(o instanceof EWrapServer))
		{
			return false;
		}
		return ((EWrapServer) o).canEqual(this) && super.equals(o);
	}

	public boolean canEqual(Object o)
	{
		return o instanceof EWrapServer;
	}

	@Override
	protected String getCommSymbol()
	{
		return "(??)";
	}
}
