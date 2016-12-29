package org.scribble.ext.f17.ast.local.action;

import org.scribble.ext.f17.ast.F17AstFactory;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;


public class F17LConnect extends F17LOutput
{
	public final Op op;
	public final Payload pay;
	
	public F17LConnect(Role self, Role peer, Op op, Payload pay)
	{
		super(self, peer);
		this.op = op;
		this.pay = pay;
	}
	
	@Override
	public F17LAccept toDual()
	{
		return F17AstFactory.FACTORY.LAccept(this.peer, this.self, this.op, this.pay);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "!!" + this.op + this.pay;
	} 

	@Override
	public int hashCode()
	{
		int hash = 23;
		hash = 31 * hash + super.hashCode();
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
		if (!(obj instanceof F17LConnect))
		{
			return false;
		}
		F17LConnect them = (F17LConnect) obj;
		return super.equals(obj)  // super does canEquals (and checks self/peer)
				&& this.op.equals(them.op) && this.pay.equals(them.pay);
	}
	
	@Override
	protected boolean canEquals(Object o)
	{
		return o instanceof F17LConnect;
	}
}
