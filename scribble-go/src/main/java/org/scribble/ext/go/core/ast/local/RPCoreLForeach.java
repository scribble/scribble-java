package org.scribble.ext.go.core.ast.local;

import java.util.Set;

import org.scribble.ext.go.core.ast.RPCoreForeach;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.type.kind.Local;
import org.scribble.type.name.Role;

public class RPCoreLForeach extends RPCoreForeach<RPCoreLType, Local> implements RPCoreLType
{
	public RPCoreLForeach(Role role, RPIndexVar var, RPIndexExpr src, RPIndexExpr dest, RPCoreLType body, RPCoreLType cont)
	{
		super(role, var, src, dest, body, cont);
	}
	
	@Override
	public Set<RPIndexVar> getIndexVars()
	{
		//return this.body.getIndexVars();
		throw new RuntimeException("TODO: ");
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPCoreLForeach))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof RPCoreLForeach;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 4283;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
}
