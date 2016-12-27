package org.scribble.f17.ast.local.action;

import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;


// FIXME: make F17LInput/Output
public class F17LReceive extends F17LAction
{
	public final Role self;
	public final Role peer;  // FIXME: refactor into super
	public final Op op;
	public final Payload pay;
	
	public F17LReceive(Role self, Role dest, Op op, Payload pay)
	{
		super(self);  // this.subj == this.self
		this.self = self;
		this.peer = dest;
		this.op = op;
		this.pay = pay;
	}
	
	@Override
	public boolean isInput()
	{
		return true;
	}
	
	@Override
	public String toString()
	{
		return this.self + ":" + this.peer + "?" + this.op + "(" + this.pay + ")";
	} 

	@Override
	public int hashCode()
	{
		int hash = 37;
		hash = 31 * hash + peer.hashCode();
		hash = 31 * hash + this.op.hashCode();
		hash = 31 * hash + this.pay.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof F17LReceive))
		{
			return false;
		}
		F17LReceive them = (F17LReceive) obj;
		return super.equals(obj)  // super does canEquals
				&& this.peer.equals(them.peer) && this.op.equals(them.op) && this.pay.equals(them.pay);
	}
	
	@Override
	protected boolean canEquals(Object o)
	{
		return o instanceof F17LReceive;
	}
}
