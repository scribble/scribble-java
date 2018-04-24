package org.scribble.ext.go.core.ast.local;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.type.Message;

public class RPCoreLCrossChoice extends RPCoreLChoice
{
	//public RPCoreLCrossChoice(RPIndexedRole role, RPCoreLActionKind kind, LinkedHashMap<RPCoreMessage, RPCoreLType> cases)
	public RPCoreLCrossChoice(RPIndexedRole role, RPCoreLActionKind kind, LinkedHashMap<Message, RPCoreLType> cases)
	{
		super(role, kind, cases);
		if (kind != RPCoreLActionKind.CROSS_SEND && kind != RPCoreLActionKind.CROSS_RECEIVE)
		{
			throw new RuntimeException("[param-core] Shouldn't get in here: " + kind);
		}
	}
	
	@Override
	public Set<RPIndexVar> getIndexVars()
	{
		Set<RPIndexVar> ivars = new HashSet<>();
		ivars.addAll(this.role.getIndexVars());
		this.cases.values().stream().forEach(c -> ivars.addAll(c.getIndexVars()));
		return ivars;
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
