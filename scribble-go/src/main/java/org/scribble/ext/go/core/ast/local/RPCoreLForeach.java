package org.scribble.ext.go.core.ast.local;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreForeach;
import org.scribble.ext.go.core.ast.RPCoreType;
import org.scribble.ext.go.type.index.RPForeachVar;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.type.kind.Local;
import org.scribble.type.name.Role;

public class RPCoreLForeach extends RPCoreForeach<RPCoreLType, Local> implements RPCoreLType
{
	public RPCoreLForeach(Role role, RPForeachVar param, RPIndexExpr start, RPIndexExpr end, RPCoreLType body, RPCoreLType cont)
	{
		super(role, param, start, end, body, cont);
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
			return af.RPCoreLForeach(this.role, this.param, this.start, this.end,
					this.body.subs(af, old, neu), this.seq.subs(af, old, neu));
		}
	}
	
	@Override
	public Set<RPIndexVar> getIndexVars()
	{
		Set<RPIndexVar> ivars = 
				//Stream.of(this.param).collect(Collectors.toSet());  
						// FIXME: shouldn't include bound foreach params (cf. RPCoreGForeach#getIndexVars) -- currently included for hacked Foreach method loop (RPCoreSTStateChanApiBuilder)
				new HashSet<>();
		ivars.addAll(this.start.getVars());
		ivars.addAll(this.end.getVars());
		ivars.addAll(this.body.getIndexVars());
		return ivars.stream()
				.filter(v -> !v.toString().equals(this.param.toString()))  // HACK FIXME -- RPIndexVar distinguished from RPForeachVar (equals)
				.collect(Collectors.toSet());
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
