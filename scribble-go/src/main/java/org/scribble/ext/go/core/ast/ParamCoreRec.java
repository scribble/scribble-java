package org.scribble.ext.go.core.ast;

import org.scribble.type.name.RecVar;

public abstract class ParamCoreRec<B extends ParamCoreType> implements ParamCoreType
{
	public final RecVar recvar;  // FIXME: RecVarNode?  (Cf. AssrtCoreAction.op/pay)
	public final B body;
	
	public ParamCoreRec(RecVar recvar, B body)
	{
		this.recvar = recvar;
		this.body = body;
	}
	
	@Override
	public String toString()
	{
		return "mu " + this.recvar + this.body;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof ParamCoreRec))
		{
			return false;
		}
		ParamCoreRec<?> them = (ParamCoreRec<?>) obj;
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
