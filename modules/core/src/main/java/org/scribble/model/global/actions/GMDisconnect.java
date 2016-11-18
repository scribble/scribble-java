package org.scribble.model.global.actions;

import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

public class GMDisconnect extends GMIOAction
{
	public GMDisconnect(Role subj, Role obj)
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
		int hash = 1013;
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
		if (!(o instanceof GMDisconnect))
		{
			return false;
		}
		return ((GMDisconnect) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof GMDisconnect;
	}

	@Override
	protected String getCommSymbol()
	{
		//return "\u00A1\u00A1abc";
		return "-/-";
	}
}
