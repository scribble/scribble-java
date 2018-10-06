package org.scribble.ext.go.core.ast.local;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreForeach;
import org.scribble.ext.go.core.ast.RPCoreType;
import org.scribble.ext.go.core.type.RPAnnotatedInterval;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.type.index.RPIndexInt;
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
	public RPCoreLType minimise(RPCoreAstFactory af, RPRoleVariant subj)
	{
		// FIXME: factor out with RPCoreLChoice
		int self = -1000;
		RPRoleVariant msubj = subj.minimise(self);
		if (msubj.isSingleton())
		{
			RPInterval ival = msubj.intervals.iterator().next();
			if (ival.start instanceof RPIndexInt)
			{
				self = ((RPIndexInt) ival.start).val;
			}
		}

		int y = self;
		Set<RPAnnotatedInterval> tmp = this.ivals.stream().map(x -> (RPAnnotatedInterval) x.minimise(y)).collect(Collectors.toSet());
			return af.RPCoreLForeach(this.roles, tmp, this.body.minimise(af, subj), this.seq.minimise(af, subj));
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
	public Set<RPIndexVar> getIndexVars()  // For user-supplied params to endpoint kind constructor, i.e., excluding RPForeachVar
	{
		Set<RPIndexVar> res = 
				//Stream.of(this.param).collect(Collectors.toSet());  
						// FIXME: shouldn't include bound foreach params (cf. RPCoreGForeach#getIndexVars) -- currently included for hacked Foreach method loop (RPCoreSTStateChanApiBuilder)
				new HashSet<>();
		res.addAll(this.body.getIndexVars());
		for (RPAnnotatedInterval iv : this.ivals)
		{
			//Set<RPIndexVar> tmp = new HashSet<>();
			res = res.stream()
					.filter(v -> !v.toString().equals(iv.var.toString()))  // HACK FIXME -- RPIndexVar distinguished from RPForeachVar (equals)
					.collect(Collectors.toSet());
			res.addAll(iv.start.getVars());  // CHECKME: already checked iv.var not in here?
			res.addAll(iv.end.getVars());
		}
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
