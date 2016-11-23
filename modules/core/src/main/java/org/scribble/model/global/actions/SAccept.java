package org.scribble.model.global.actions;

import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class SAccept extends SAction
{
	public SAccept(Role subj, Role obj, MessageId<?> mid, Payload payload)
	//public GAccept(Role subj, Role obj)
	{
		super(subj, obj, mid, payload);
		//super(subj, obj, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}
	
	@Override
	public boolean isAccept()
	{
		return true;
	}

	@Override
	public int hashCode()
	{
		int hash = 967;
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
		if (!(o instanceof SAccept))
		{
			return false;
		}
		return ((SAccept) o).canEqual(this) && super.equals(o);
	}

	public boolean canEqual(Object o)
	{
		return o instanceof SAccept;
	}

	@Override
	protected String getCommSymbol()
	{
		//return "??";
		return "<<-";
	}
}
