package org.scribble2.sesstype;

import java.util.List;

import org.scribble2.sesstype.name.Operator;
import org.scribble2.sesstype.name.PayloadType;
import org.scribble2.sesstype.name.Scope;

@Deprecated
public class ScopedMessageSignature extends MessageSignature //implements ScopedMessage
{
	public final Scope scope;
	
	public ScopedMessageSignature(Scope scope, Operator op, List<PayloadType> payload)
	{
		//super(op, payload);
		super(scope, op , null);
		this.scope = scope;
	}

	//@Override
	public Scope getScope()
	{
		return this.scope;
	}

	@Override
	public int hashCode()
	{
		int hash = 3191;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.scope.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (!super.equals(o))
		{
			return false;
		}
		ScopedMessageSignature ms = (ScopedMessageSignature) o;
		return this.scope.equals(ms.scope);
	}
	
	@Override
	public String toString()
	{
		return this.scope + ":" + super.toString();
	}
}
