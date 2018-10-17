package org.scribble.ext.go.core.model.endpoint.action;

import org.scribble.ext.go.core.model.endpoint.RPCoreEModelFactory;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.model.global.SModelFactory;
import org.scribble.model.global.actions.SReceive;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;

public class RPCoreECrossReceive extends EReceive implements RPCoreEAction
{
	
	public RPCoreECrossReceive(RPCoreEModelFactory ef, RPIndexedRole peer, MessageId<?> mid, Payload payload)
	{
		super(ef, peer, mid, payload);
	}

	@Override
	public RPIndexedRole getPeer()
	{
		return (RPIndexedRole) this.peer;
	}
	
	@Override
	public RPCoreECrossSend toDual(Role self)
	{
		throw new RuntimeException("[param-core] Shouldn't get in here: " + this);
	}

	@Override
	public SReceive toGlobal(SModelFactory sf, Role self)
	{
		throw new RuntimeException("[param-core] Shouldn't get in here: " + this);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 6763;
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
		if (!(o instanceof RPCoreECrossReceive))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof RPCoreECrossReceive;
	}
}
