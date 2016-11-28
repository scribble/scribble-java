package org.scribble.model.global.actions;

import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

public class SWrapServer extends SAction
{
	public SWrapServer(Role subj, Role obj)
	{
		super(subj, obj, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}
	
	@Override
	public boolean isAccept()
	{
		return true;
	}

	@Override
	public int hashCode()
	{
		int hash = 1087;
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
		if (!(o instanceof SWrapServer))
		{
			return false;
		}
		return ((SWrapServer) o).canEqual(this) && super.equals(o);
	}

	public boolean canEqual(Object o)
	{
		return o instanceof SWrapServer;
	}

	@Override
	protected String getCommSymbol()
	{
		return "(<<-)";
	}
}
