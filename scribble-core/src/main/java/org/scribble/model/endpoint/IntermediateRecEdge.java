package org.scribble.model.endpoint;

import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.global.SModelFactory;
import org.scribble.model.global.actions.SAction;
import org.scribble.type.Payload;
import org.scribble.type.name.Op;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

// FIXME rename better (it's an "external rec" continue edge)
@Deprecated
class IntermediateRecEdge extends EAction
{
	public final EAction action;
	
	public IntermediateRecEdge(EModelFactory ef, EAction a, RecVar rv)
	{
		super(ef, Role.EMPTY_ROLE, new Op(rv.toString()), Payload.EMPTY_PAYLOAD);  // HACK
		this.action = a;
	}
	
	@Override
	public EAction toDual(Role self)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}

	@Override
	public SAction toGlobal(SModelFactory sf, Role self)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}

	@Override
	protected String getCommSymbol()
	{
		return "&";
	}
	
	@Override
	public int hashCode()
	{
		int hash = 6037;
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
		if (!(o instanceof IntermediateRecEdge))
		{
			return false;
		}
		return ((IntermediateRecEdge) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof IntermediateRecEdge;
	}
}
