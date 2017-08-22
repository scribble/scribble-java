package org.scribble.ext.go.core.ast.local;

import java.util.Map;

import org.scribble.ext.go.core.ast.ParamCoreMessage;
import org.scribble.ext.go.core.ast.ParamCoreChoice;
import org.scribble.ext.go.core.ast.ParamRole;
import org.scribble.type.kind.Local;

public class ParamCoreLChoice extends ParamCoreChoice<ParamCoreLType, Local> implements ParamCoreLType
{
	public final ParamCoreLActionKind kind;
	
	public ParamCoreLChoice(ParamRole role, ParamCoreLActionKind kind, Map<ParamCoreMessage, ParamCoreLType> cases)
	{
		super(role, cases);
		this.kind = kind;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 2399;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.kind.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof ParamCoreLChoice))
		{
			return false;
		}
		return super.equals(obj) && this.kind.equals(((ParamCoreLChoice) obj).kind);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ParamCoreLChoice;
	}

	@Override
	public String toString()
	{
		return this.role.toString() + this.kind + casesToString();
	}
}
