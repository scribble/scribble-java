package org.scribble.model.global.actions;

import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

public class GMWrapClient extends GMIOAction
{
	public GMWrapClient(Role subj, Role obj)
	{
		super(subj, obj, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}
	
	@Override
	public boolean isConnect()
	{
		return true;
	}

	@Override
	public int hashCode()
	{
		int hash = 1069;
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
		if (!(o instanceof GMWrapClient))
		{
			return false;
		}
		return ((GMWrapClient) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof GMWrapClient;
	}

	@Override
	protected String getCommSymbol()
	{
		return "(->>)";
	}
}
