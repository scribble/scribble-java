package org.scribble.model.local;

import org.scribble.model.global.GSend;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class Send extends IOAction
{
	/*protected static final Set<Send> SENDS = new HashSet<>();
	
	public static Send get(Role peer, MessageId<?> mid, Payload payload)
	{
		Send send = new Send(peer, mid, payload, true);
		for (Send s : Send.SENDS)  // FIXME: hashmap
		{
			if (s.equiv(send))
			{
				return s;
			}
		}
		Send.SENDS.add(send);
		return send;
	}
	
	public Send(Role peer, MessageId<?> mid, Payload payload, boolean hack)
	{
		super(peer, mid, payload);
	}*/

	public Send(Role peer, MessageId<?> mid, Payload payload)
	{
		super(peer, mid, payload);
		//Send.SENDS.add(this);
	}
	
	@Override
	public Receive toDual(Role self)
	{
		return new Receive(self, this.mid, this.payload);
		//return Receive.get(self, this.mid, this.payload);
	}

	@Override
	//public GModelAction toGlobal(Role self)
	public GSend toGlobal(Role self)
	{
		//return new GModelAction(self, this.peer, this.mid, this.payload);
		////return GModelAction.get(self, this.peer, this.mid, this.payload);
		return new GSend(self, this.peer, this.mid, this.payload);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 953;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
	
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
