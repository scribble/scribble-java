package org.scribble.model.global.actions;

import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class GMReceive extends GMIOAction
{
	public GMReceive(Role subj, Role obj, MessageId<?> mid, Payload payload)
	{
		super(subj, obj, mid, payload);
	}
	
	@Override
	public boolean isReceive()
	{
		return true;
	}

	@Override
	public int hashCode()
	{
		int hash = 977;
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
		if (!(o instanceof GMReceive))
		{
			return false;
		}
		return ((GMReceive) o).canEqual(this) && super.equals(o);
	}

	public boolean canEqual(Object o)
	{
		return o instanceof GMReceive;
	}

	@Override
	protected String getCommSymbol()
	{
		return "?";
	}
}
