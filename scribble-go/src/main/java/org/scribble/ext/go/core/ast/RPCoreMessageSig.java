package org.scribble.ext.go.core.ast;

import org.scribble.type.Payload;
import org.scribble.type.name.Op;

@Deprecated
public class RPCoreMessageSig implements RPCoreMessage
{
	public final Op op;
	public final Payload pay;
	
	public RPCoreMessageSig(Op op, Payload pay)
	{
		this.op = op;
		this.pay = pay;
	}
	
	@Override
	public String toString()
	{
		return this.op.toString() + this.pay.toString();
	}

	@Override
	public int hashCode()
	{
		int hash = 43;
		hash = 31 * hash + this.op.hashCode();
		hash = 31 * hash + this.pay.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}
		if (!(obj instanceof RPCoreMessageSig))
		{
			return false;
		}
		RPCoreMessageSig them = (RPCoreMessageSig) obj;
		return //them.canEquals(this) && 
				this.op.equals(them.op) && this.pay.equals(them.pay);
	}
}
