package org.scribble.ext.go.core.ast.global;

import java.util.Collections;
import java.util.Set;

import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreEnd;
import org.scribble.ext.go.core.ast.RPCoreSyntaxException;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.main.GoJob;
import org.scribble.type.kind.Global;


public class RPCoreGEnd extends RPCoreEnd<Global> implements RPCoreGType
{
	public static final RPCoreGEnd END = new RPCoreGEnd();
	
	private RPCoreGEnd()
	{
		
	}
	
	@Override
	public boolean isWellFormed(GoJob job, GProtocolDecl gpd)
	{
		return true;
	}
	
	@Override
	public Set<RPIndexedRole> getParamRoles()
	{
		return Collections.emptySet();
	}

	@Override
	//public ParamCoreLEnd project(ParamCoreAstFactory af, Role r, Set<ParamRange> ranges)
	public RPCoreLType project(RPCoreAstFactory af, RPRoleVariant subj) throws RPCoreSyntaxException
	{
		return af.ParamCoreLEnd();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof RPCoreGEnd))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof RPCoreGEnd;
	}

	@Override
	public int hashCode()
	{
		return 31*2381;
	}
}