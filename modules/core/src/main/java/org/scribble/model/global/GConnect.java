package org.scribble.model.global;

import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

public class GConnect extends GIOAction
{
	//public GConnect(Role subj, Role obj, MessageId<?> mid, Payload payload)
	public GConnect(Role subj, Role obj)
	{
		//super(subj, obj, mid, payload);
		super(subj, obj, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}
	
	@Override
	public boolean isConnect()
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
		if (!(o instanceof GConnect))
		{
			return false;
		}
		return ((GConnect) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof GConnect;
	}

	@Override
	protected String getCommSymbol()
	{
		return "!!";
	}
}
