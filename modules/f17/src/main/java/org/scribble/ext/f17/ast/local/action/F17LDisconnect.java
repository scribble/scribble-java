package org.scribble.ext.f17.ast.local.action;

import org.scribble.sesstype.name.Role;


public class F17LDisconnect extends F17LOutput  // "Output"
{

	public F17LDisconnect(Role self, Role peer)
	{
		super(self, peer);
	}
	
	@Override
	public F17LDisconnect toDual()
	{
		return this;  // Not reversing the role "order" (would need to revise hashCode/equals)
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "#";
	} 

	@Override
	public int hashCode()
	{
		int hash = 2269;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof F17LDisconnect))
		{
			return false;
		}
		return super.equals(obj);  // super does canEquals
	}
	
	@Override
	protected boolean canEquals(Object o)
	{
		return o instanceof F17LDisconnect;
	}
}
