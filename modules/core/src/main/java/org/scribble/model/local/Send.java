package org.scribble.model.local;

import org.scribble.model.global.GModelAction;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class Send extends IOAction
{
	public Send(Role peer, MessageId<?> mid, Payload payload)
	{
		super(peer, mid, payload);
	}
	
	public Receive toDual(Role self)
	{
		return new Receive(self, this.mid, this.payload);
	}

	@Override
	public GModelAction toGlobal(Role self)
	{
		return new GModelAction(self, this.peer, this.mid, this.payload);
	}
	
	/*@Override
	public int hashCode()
	{
		int hash = 929;
		hash = 31 * hash + super.hashCode();
		return hash;
	}*/
	
	@Override
	public boolean isSend()
	{
		return true;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Send))
		{
			return false;
		}
		return ((Send) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof Send;
	}

	@Override
	protected String getCommSymbol()
	{
		return "!";
	}
}
