package org.scribble.ext.go.core.ast.local;

import java.util.LinkedHashMap;

import org.scribble.ext.go.core.ast.ParamCoreMessage;
import org.scribble.ext.go.core.type.ParamRole;

public class ParamCoreLCrossChoice extends ParamCoreLChoice
{
	public ParamCoreLCrossChoice(ParamRole role, ParamCoreLActionKind kind, LinkedHashMap<ParamCoreMessage, ParamCoreLType> cases)
	{
		super(role, kind, cases);
		if (kind != ParamCoreLActionKind.CROSS_SEND && kind != ParamCoreLActionKind.CROSS_RECEIVE)
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
		if (!(obj instanceof ParamCoreLCrossChoice))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ParamCoreLCrossChoice;
	}
}
