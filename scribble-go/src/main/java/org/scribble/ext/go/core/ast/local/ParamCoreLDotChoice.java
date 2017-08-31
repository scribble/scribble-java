package org.scribble.ext.go.core.ast.local;

import java.util.LinkedHashMap;

import org.scribble.ext.go.core.ast.ParamCoreMessage;
import org.scribble.ext.go.core.type.ParamRange;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.ext.go.type.index.ParamIndexExpr;

public class ParamCoreLDotChoice extends ParamCoreLChoice
{
	public final ParamIndexExpr offset;

	public ParamCoreLDotChoice(ParamRole role, ParamIndexExpr offset, ParamCoreLActionKind kind, LinkedHashMap<ParamCoreMessage, ParamCoreLType> cases)
	{
		super(role, kind, cases);
		if (kind != ParamCoreLActionKind.DOT_SEND && kind != ParamCoreLActionKind.DOT_RECEIVE)
		{
			throw new RuntimeException("[param-core] Shouldn't get in here: " + kind);
		}
		this.offset = offset;
	}

	@Override
	public String toString()
	{
		ParamRange g = this.role.ranges.iterator().next();
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
		if (!(obj instanceof ParamCoreLDotChoice))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ParamCoreLDotChoice;
	}
}
