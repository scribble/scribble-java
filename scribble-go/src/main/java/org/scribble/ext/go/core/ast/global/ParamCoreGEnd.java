package org.scribble.ext.go.core.ast.global;

import java.util.Collections;
import java.util.Set;

import org.scribble.ext.go.core.ast.ParamCoreAstFactory;
import org.scribble.ext.go.core.ast.ParamCoreEnd;
import org.scribble.ext.go.core.ast.ParamRole;
import org.scribble.ext.go.core.ast.local.ParamCoreLEnd;
import org.scribble.type.name.Role;


public class ParamCoreGEnd extends ParamCoreEnd implements ParamCoreGType
{
	public static final ParamCoreGEnd END = new ParamCoreGEnd();
	
	private ParamCoreGEnd()
	{
		
	}
	
	@Override
	public Set<ParamRole> getParamRoles()
	{
		return Collections.emptySet();
	}

	@Override
	public ParamCoreLEnd project(ParamCoreAstFactory af, Role r)
	{
		return af.ParamCoreLEnd();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ParamCoreGEnd))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ParamCoreGEnd;
	}

	@Override
	public int hashCode()
	{
		return 31*2381;
	}
}
