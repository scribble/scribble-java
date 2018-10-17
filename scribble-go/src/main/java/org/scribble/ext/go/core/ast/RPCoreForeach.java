package org.scribble.ext.go.core.ast;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ext.go.core.type.RPAnnotatedInterval;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.Role;

public abstract class RPCoreForeach<B extends RPCoreType<K>, K extends ProtocolKind> implements RPCoreType<K>
{
	/*public final Role role;
	public final RPForeachVar param;
	// FIXME: use RPInterval
	public final RPIndexExpr start;      // Gives the Scribble choices-subj range // Cf. ParamCoreGChoice singleton src
	public final RPIndexExpr end;  // this.dest == super.role -- arbitrary?*/
	
	// FIXME: record original role-ival bindings -- to check valid foreach index usage per rolename in WF 
	public final Set<Role> roles;
	public final Set<RPAnnotatedInterval> ivals;  // FIXME: co-intervals?

	public final B body;
	public final B seq;
	
	// Pre: ivals vars are distinct
	public RPCoreForeach(//Role role, RPForeachVar param, RPIndexExpr start, RPIndexExpr end, 
			Set<Role> roles, Set<RPAnnotatedInterval> ivals,
			B body, B cont)
	{
		/*this.role = role;
		this.param = param;
		this.start = start;
		this.end = end;*/
		this.roles = new HashSet<>(roles);
		this.ivals = new HashSet<>(ivals);
		this.body = body;
		this.seq = cont;
	}
	
	@Override
	public String toString()
	{
		return "foreach " //+ this.role + "[" + param + ":" + this.start + "," + this.end + "] do " 
				+ "{" + this.roles.stream().map(r -> r.toString()).collect(Collectors.joining(", ")) + "}"
				+ "{" + this.ivals.stream().map(x -> x.var + ":" + x.start + ".." + x.end).collect(Collectors.joining(", ")) + "} do "
				+ this.body + " ; " + this.seq;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPCoreForeach))
		{
			return false;
		}
		RPCoreForeach<?, ?> them = (RPCoreForeach<?, ?>) obj;
		return them.canEquals(this) && this.roles.equals(them.roles) 
				//&& this.param.equals(them.param) && this.start.equals(them.start) && this.end.equals(them.end)
				&& this.ivals.equals(them.ivals)
				&& this.body.equals(them.body) && this.seq.equals(them.seq);  // FIXME: check B kind is equal?
	}
	
	@Override
	public abstract boolean canEquals(Object o);
	
	@Override
	public int hashCode()
	{
		final int prime = 4271;
		int result = 1;
		/*result = prime * result + this.role.hashCode();
		result = prime * result + this.param.hashCode();
		result = prime * result + this.start.hashCode();
		result = prime * result + this.end.hashCode();*/
		result = prime * result + this.roles.hashCode();
		result = prime * result + this.ivals.hashCode();
		result = prime * result + this.body.hashCode();
		result = prime * result + this.seq.hashCode();
		return result;
	}
}
