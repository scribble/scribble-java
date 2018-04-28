package org.scribble.ext.go.core.ast;

import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.Role;

public abstract class RPCoreForeach<B extends RPCoreType<K>, K extends ProtocolKind> implements RPCoreType<K>
{
	public final Role role;
	public final RPIndexVar var;

	// FIXME: use RPInterval
	public final RPIndexExpr start;      // Gives the Scribble choices-subj range // Cf. ParamCoreGChoice singleton src
	public final RPIndexExpr end;  // this.dest == super.role -- arbitrary?

	public final B body;
	public final B seq;
	
	public RPCoreForeach(Role role, RPIndexVar var, RPIndexExpr start, RPIndexExpr end, B body, B cont)
	{
		this.role = role;
		this.var = var;
		this.start = start;
		this.end = end;
		this.body = body;
		this.seq = cont;
	}
	
	@Override
	public String toString()
	{
		return "foreach " + this.role + "[" + var + ":" + this.start + "," + this.end + "] do " + this.body + " ; " + this.seq;
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
		return them.canEquals(this) && this.role.equals(them.role) 
				&& this.var.equals(them.var) && this.start.equals(them.start)
				&& this.end.equals(them.end) && this.body.equals(them.body) && this.seq.equals(them.seq);  // FIXME: check B kind is equal?
	}
	
	@Override
	public abstract boolean canEquals(Object o);
	
	@Override
	public int hashCode()
	{
		final int prime = 4271;
		int result = 1;
		result = prime * result + this.role.hashCode();
		result = prime * result + this.var.hashCode();
		result = prime * result + this.start.hashCode();
		result = prime * result + this.end.hashCode();
		result = prime * result + this.body.hashCode();
		result = prime * result + this.seq.hashCode();
		return result;
	}
}
