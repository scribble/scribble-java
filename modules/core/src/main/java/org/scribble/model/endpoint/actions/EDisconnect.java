package org.scribble.model.endpoint.actions;

import org.scribble.model.global.actions.SDisconnect;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

public class EDisconnect extends EAction
{
	public EDisconnect(Role peer)
	{
		super(peer, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);  // Must correspond with GDisconnect.UNIT_MESSAGE_SIG_NODE
	}
	
	@Override
	public EDisconnect toDual(Role self)
	{
		return new EDisconnect(self);  // return this?
	}

	@Override
	public SDisconnect toGlobal(Role self)
	{
		return new SDisconnect(self, this.peer);
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
		if (!(o instanceof EDisconnect))
		{
			return false;
		}
		return ((EDisconnect) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof EDisconnect;
	}

	@Override
	protected String getCommSymbol()
	{
		//return "\u00A1\u00A1";
		return "-/-";
	}
}
