package org.scribble.model.local;

import org.scribble.model.global.GReceive;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class Receive extends IOAction
{
	/*protected static final Set<Receive> RECEIVES = new HashSet<>();
	
	public static Receive get(Role peer, MessageId<?> mid, Payload payload)
	{
		Receive receive = new Receive(peer, mid, payload, true);
		for (Receive r : Receive.RECEIVES)  // FIXME: hashmap
		{
			if (r.equiv(receive))
			{
				return r;
			}
		}
		Receive.RECEIVES.add(receive);
		return receive;
	}

	private Receive(Role peer, MessageId<?> mid, Payload payload, boolean hack)
	{
		super(peer, mid, payload);
	}*/

	public Receive(Role peer, MessageId<?> mid, Payload payload)
	{
		super(peer, mid, payload);
		//Receive.RECEIVES.add(this);
	}
	
	@Override
	public Send toDual(Role self)
	{
		return new Send(self, this.mid, this.payload);
		//return Send.get(self, this.mid, this.payload);
	}

	@Override
	//public GModelAction toGlobal(Role self)
	public GReceive toGlobal(Role self)
	{
		//return new GModelAction(this.peer, self, this.mid, this.payload);
		////return GModelAction.get(this.peer, self, this.mid, this.payload);
		return new GReceive(self, this.peer, this.mid, this.payload);

	}
	
	@Override
	public int hashCode()
	{
		int hash = 947;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
	
	@Override
	public boolean isReceive()
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
		if (!(o instanceof Receive))
		{
			return false;
		}
		return ((Receive) o).canEqual(this) && super.equals(o);
	}

	public boolean canEqual(Object o)
	{
		return o instanceof Receive;
	}

	@Override
	protected String getCommSymbol()
	{
		return "?";
	}
}
