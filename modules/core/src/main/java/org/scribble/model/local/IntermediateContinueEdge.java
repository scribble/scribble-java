package org.scribble.model.local;

import org.scribble.model.global.actions.GMIOAction;
import org.scribble.model.local.actions.LMIOAction;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;

public class IntermediateContinueEdge extends LMIOAction
{
	/*public IntermediateContinueEdge()
	{
		super(Role.EMPTY_ROLE, Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD);
	}*/
	public IntermediateContinueEdge(RecVar rv)
	{
		super(Role.EMPTY_ROLE, new Op(rv.toString()), Payload.EMPTY_PAYLOAD);  // Hacky
	}
	
	@Override
	public LMIOAction toDual(Role self)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}

	@Override
	public GMIOAction toGlobal(Role self)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1021;
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
		if (!(o instanceof IntermediateContinueEdge))
		{
			return false;
		}
		return ((IntermediateContinueEdge) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof IntermediateContinueEdge;
	}

	@Override
	protected String getCommSymbol()
	{
		return "#";
	}
}
