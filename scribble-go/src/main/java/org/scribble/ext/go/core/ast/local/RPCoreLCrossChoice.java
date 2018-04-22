package org.scribble.ext.go.core.ast.local;

import java.util.LinkedHashMap;

import org.scribble.ext.go.core.ast.RPCoreMessage;
import org.scribble.ext.go.core.type.RPIndexedRole;

public class RPCoreLCrossChoice extends RPCoreLChoice
{
	public RPCoreLCrossChoice(RPIndexedRole role, RPCoreLActionKind kind, LinkedHashMap<RPCoreMessage, RPCoreLType> cases)
	{
		super(role, kind, cases);
		if (kind != RPCoreLActionKind.CROSS_SEND && kind != RPCoreLActionKind.CROSS_RECEIVE)
		{
			throw new RuntimeException("[param-core] Shouldn't get in here: " + kind);
		}
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7229;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPCoreLCrossChoice))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof RPCoreLCrossChoice;
	}
}
