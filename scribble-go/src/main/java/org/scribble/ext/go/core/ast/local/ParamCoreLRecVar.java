package org.scribble.ext.go.core.ast.local;

import org.scribble.ext.go.core.ast.ParamCoreRecVar;
import org.scribble.type.name.RecVar;

	
public class ParamCoreLRecVar extends ParamCoreRecVar implements ParamCoreLType
{
	public ParamCoreLRecVar(RecVar var)
	{
		super(var);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ParamCoreLRecVar))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}

	@Override
	public int hashCode()
	{
		int hash = 2417;
		hash = 31*hash + super.hashCode();
		return hash;
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ParamCoreLRecVar;
	}
}
