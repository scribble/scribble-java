package org.scribble.ext.go.core.ast.local;

import java.util.Collections;
import java.util.Set;

import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreEnd;
import org.scribble.ext.go.core.ast.RPCoreType;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.type.kind.Local;


public class RPCoreLEnd extends RPCoreEnd<Local> implements RPCoreLType
{
	public static final RPCoreLEnd END = new RPCoreLEnd();
	
	private RPCoreLEnd()
	{
		
	}

	@Override
	public RPCoreLType compactSingletonIvals(RPCoreAstFactory af, RPRoleVariant subj)
	{
		return this;
	}
	
	@Override
	public RPCoreLType subs(RPCoreAstFactory af, RPCoreType<Local> old, RPCoreType<Local> neu) 
	{
		if (this.equals(old))
		{
			return (RPCoreLType) neu;
		}
		return this;
	}
	
	@Override
	public Set<RPIndexVar> getIndexVars()
	{
		return Collections.emptySet();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof RPCoreLEnd))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof RPCoreLEnd;
	}

	@Override
	public int hashCode()
	{
		return 31*2383;
	}
}
