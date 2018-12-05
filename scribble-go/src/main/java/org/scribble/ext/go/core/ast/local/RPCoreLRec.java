package org.scribble.ext.go.core.ast.local;

import java.util.Set;

import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreRec;
import org.scribble.ext.go.core.ast.RPCoreType;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.type.kind.Local;
import org.scribble.type.name.RecVar;

public class RPCoreLRec extends RPCoreRec<RPCoreLType, Local> implements RPCoreLType
{
	public RPCoreLRec(RecVar recvar, RPCoreLType body)
	{
		//super(recvar, annot, init, body);
		super(recvar, body);
	}

	@Override
	public RPCoreLType compactSingletonIvals(RPCoreAstFactory af, RPRoleVariant subj)
	{
		return af.ParamCoreLRec(this.recvar, this.body.compactSingletonIvals(af, subj));
	}
	
	@Override
	public RPCoreLType subs(RPCoreAstFactory af, RPCoreType<Local> old, RPCoreType<Local> neu)
	{
		if (this.equals(old))
		{
			return (RPCoreLType) neu;
		}
		else
		{
			return af.ParamCoreLRec(this.recvar, this.body.subs(af, old, neu));
		}
	}
	
	@Override
	public Set<RPIndexVar> getIndexVars()
	{
		return this.body.getIndexVars();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPCoreLRec))
		{
			return false;
		}
		return super.equals(obj);  // Does canEquals
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof RPCoreLRec;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 2389;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
}
