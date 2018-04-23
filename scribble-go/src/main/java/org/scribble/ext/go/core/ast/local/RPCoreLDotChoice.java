package org.scribble.ext.go.core.ast.local;

import java.util.LinkedHashMap;
import java.util.Set;

import org.scribble.ext.go.core.ast.RPCoreMessage;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexVar;

public class RPCoreLDotChoice extends RPCoreLChoice
{
	public final RPIndexExpr offset;

	public RPCoreLDotChoice(RPIndexedRole role, RPIndexExpr offset, RPCoreLActionKind kind, LinkedHashMap<RPCoreMessage, RPCoreLType> cases)
	{
		super(role, kind, cases);
		if (kind != RPCoreLActionKind.DOT_SEND && kind != RPCoreLActionKind.DOT_RECEIVE)
		{
			throw new RuntimeException("[param-core] Shouldn't get in here: " + kind);
		}
		this.offset = offset;
	}
	
	@Override
	public Set<RPIndexVar> getIndexVars()
	{
		throw new RuntimeException("[param] Shouldn't get in here: " + this);
	}

	@Override
	public String toString()
	{
		RPInterval g = this.role.intervals.iterator().next();
		return this.role.getName() + "[" + this.offset + ":" + g.start + ".." + g.end + "]"
				+ this.kind + casesToString();
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7237;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.offset.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPCoreLDotChoice))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof RPCoreLDotChoice;
	}
}
