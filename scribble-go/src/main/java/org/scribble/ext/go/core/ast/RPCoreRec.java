package org.scribble.ext.go.core.ast;

import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.RecVar;

public abstract class RPCoreRec<B extends RPCoreType<K>, K extends ProtocolKind> implements RPCoreType<K>
{
	public final RecVar recvar;  // FIXME: RecVarNode?  (Cf. AssrtCoreAction.op/pay)
	public final B body;
	
	public RPCoreRec(RecVar recvar, B body)
	{
		this.recvar = recvar;
		this.body = body;
	}
	
	@Override
	public String toString()
	{
		return "mu " + this.recvar + "." + this.body;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPCoreRec))
		{
			return false;
		}
		RPCoreRec<?, ?> them = (RPCoreRec<?, ?>) obj;
		return them.canEquals(this) && this.recvar.equals(them.recvar) 
				&& this.body.equals(them.body);  // FIXME: check B kind is equal?
	}
	
	@Override
	public abstract boolean canEquals(Object o);
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + this.recvar.hashCode();
		result = prime * result + this.body.hashCode();
		return result;
	}
}
