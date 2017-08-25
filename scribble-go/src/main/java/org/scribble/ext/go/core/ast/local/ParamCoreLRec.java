package org.scribble.ext.go.core.ast.local;

import org.scribble.ext.go.core.ast.ParamCoreRec;
import org.scribble.type.kind.Local;
import org.scribble.type.name.RecVar;

public class ParamCoreLRec extends ParamCoreRec<ParamCoreLType, Local> implements ParamCoreLType
{
	public ParamCoreLRec(RecVar recvar, ParamCoreLType body)
	{
		//super(recvar, annot, init, body);
		super(recvar, body);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof ParamCoreLRec))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ParamCoreLRec;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 2389;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
}
