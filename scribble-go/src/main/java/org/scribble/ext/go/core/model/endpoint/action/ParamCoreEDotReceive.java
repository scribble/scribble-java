package org.scribble.ext.go.core.model.endpoint.action;

import org.scribble.ext.go.core.model.endpoint.ParamCoreEModelFactory;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.model.global.SModelFactory;
import org.scribble.model.global.actions.SReceive;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;

public class ParamCoreEDotReceive extends EReceive implements ParamCoreEAction
{
	
	public ParamCoreEDotReceive(ParamCoreEModelFactory ef, ParamRole peer, MessageId<?> mid, Payload payload)
	{
		super(ef, peer, mid, payload);
	}

	@Override
	public ParamRole getPeer()
	{
		return (ParamRole) this.peer;
	}
	
	@Override
	public ParamCoreEDotSend toDual(Role self)
	{
		throw new RuntimeException("[param-core] Shouldn't get in here: " + this);
	}

	@Override
	public SReceive toGlobal(SModelFactory sf, Role self)
	{
		throw new RuntimeException("[param-core] Shouldn't get in here: " + this);
	}

	@Override
	protected String getCommSymbol()
	{
		return "?=";
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7213;
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
		if (!(o instanceof ParamCoreEDotReceive))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof ParamCoreEDotReceive;
	}
}
