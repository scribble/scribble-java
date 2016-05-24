package org.scribble.model.global;

import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

public class GAccept extends GIOAction
{
	//public GAccept(Role subj, Role obj, MessageId<?> mid, Payload payload)
	public GAccept(Role subj, Role obj)
	{
		//super(subj, obj, mid, payload);
		super(subj, obj, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}
	
	@Override
	public boolean isAccept()
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
		if (!(o instanceof GAccept))
		{
			return false;
		}
		return ((GAccept) o).canEqual(this) && super.equals(o);
	}

	public boolean canEqual(Object o)
	{
		return o instanceof GAccept;
	}

	@Override
	protected String getCommSymbol()
	{
		return "??";
	}
}
