package org.scribble.ext.go.core.ast.local;

import org.scribble.ext.go.core.ast.ParamCoreEnd;


public class ParamCoreLEnd extends ParamCoreEnd implements ParamCoreLType
{
	public static final ParamCoreLEnd END = new ParamCoreLEnd();
	
	private ParamCoreLEnd()
	{
		
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ParamCoreLEnd))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ParamCoreLEnd;
	}

	@Override
	public int hashCode()
	{
		return 31*2383;
	}
}
