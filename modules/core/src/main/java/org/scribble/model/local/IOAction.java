package org.scribble.model.local;

import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public abstract class IOAction
{
	public final Role peer;
	public final MessageId<?> mid;
	public final Payload payload;  // Empty for MessageSigNames
	
	public IOAction(Role peer, MessageId<?> mid, Payload payload)
	{
		this.peer = peer;
		this.mid = mid;
		this.payload = payload;
	}
	
	@Override
	public String toString()
	{
		return this.peer + getCommSymbol() + this.mid + this.payload;
	}
	
	protected abstract String getCommSymbol();
	
	@Override
	public int hashCode()
	{
		int hash = 919;
		hash = 31 * hash + this.peer.hashCode();
		hash = 31 * hash + this.mid.hashCode();
		hash = 31 * hash + this.payload.hashCode();
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
		IOAction a = (IOAction) o;
		return a.canEqual(this) && 
				this.peer.equals(a.peer) && this.mid.equals(a.mid) && this.payload.equals(a.payload);
	}
	
	public abstract boolean canEqual(Object o);
}
