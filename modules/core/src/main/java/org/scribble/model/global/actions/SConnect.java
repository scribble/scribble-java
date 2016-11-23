package org.scribble.model.global.actions;

import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class SConnect extends SAction
{
	public SConnect(Role subj, Role obj, MessageId<?> mid, Payload payload)
	//public GConnect(Role subj, Role obj)
	{
		super(subj, obj, mid, payload);
		//super(subj, obj, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}
	
	@Override
	public boolean isConnect()
	{
		return true;
	}

	@Override
	public int hashCode()
	{
		int hash = 971;
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
		if (!(o instanceof SConnect))
		{
			return false;
		}
		return ((SConnect) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof SConnect;
	}

	@Override
	protected String getCommSymbol()
	{
		//return "!!";
		return "->>";
	}
}
