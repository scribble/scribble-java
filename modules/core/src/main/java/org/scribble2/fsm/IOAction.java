package org.scribble2.fsm;

import org.scribble2.sesstype.name.MessageId;
import org.scribble2.sesstype.name.Role;

public abstract class IOAction
{
	public Role peer;
	public MessageId mid;
	
	public IOAction(Role peer, MessageId mid)
	{
		this.peer = peer;
		this.mid = mid;
	}
	
	@Override
	public String toString()
	{
		return this.peer + getCommSymbol() + this.mid;
	}
	
	protected abstract String getCommSymbol();
	
	@Override
	public int hashCode()
	{
		int hash = 919;
		hash = 31 * hash + this.peer.hashCode();
		hash = 31 * hash + this.mid.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof IOAction))
		{
			return false;
		}
		IOAction ca = (IOAction) o;
		return this.peer.equals(ca.peer) && this.mid.equals(ca.mid);
	}
}
