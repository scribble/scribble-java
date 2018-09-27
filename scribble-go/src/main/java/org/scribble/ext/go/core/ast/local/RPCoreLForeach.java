package org.scribble.ext.go.core.ast.local;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreForeach;
import org.scribble.ext.go.core.ast.RPCoreType;
import org.scribble.ext.go.core.type.RPAnnotatedInterval;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.type.kind.Local;
import org.scribble.type.name.Role;

public class RPCoreLForeach extends RPCoreForeach<RPCoreLType, Local> implements RPCoreLType
{
	public RPCoreLForeach(//Role role, RPForeachVar param, RPIndexExpr start, RPIndexExpr end, 
			Set<Role> roles, Set<RPAnnotatedInterval> ivals,
			RPCoreLType body, RPCoreLType cont)
	{
		super(//role, param, start, end, 
				roles, ivals,
				body, cont);
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
			return af.RPCoreLForeach(//this.role, this.param, this.start, this.end,
					this.roles, this.ivals,
					this.body.subs(af, old, neu), this.seq.subs(af, old, neu));
		}
	}
	
	@Override
	public Set<RPIndexVar> getIndexVars()
	{
		Set<RPIndexVar> res = 
				//Stream.of(this.param).collect(Collectors.toSet());  
						// FIXME: shouldn't include bound foreach params (cf. RPCoreGForeach#getIndexVars) -- currently included for hacked Foreach method loop (RPCoreSTStateChanApiBuilder)
				new HashSet<>();
		for (RPAnnotatedInterval iv : this.ivals)
		{
			Set<RPIndexVar> tmp = new HashSet<>();
			tmp.addAll(iv.start.getVars());
			tmp.addAll(iv.end.getVars());
			tmp = tmp.stream()
					.filter(v -> !v.toString().equals(iv.var.toString()))  // HACK FIXME -- RPIndexVar distinguished from RPForeachVar (equals)
					.collect(Collectors.toSet());
			res.addAll(tmp);
		}
		res.addAll(this.body.getIndexVars());
		return res;
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
