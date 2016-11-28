package org.scribble.model.endpoint.actions;

import org.scribble.model.global.actions.SReceive;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class EReceive extends EAction
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

	public EReceive(Role peer, MessageId<?> mid, Payload payload)
	{
		super(peer, mid, payload);
		//Receive.RECEIVES.add(this);
	}
	
	@Override
	public ESend toDual(Role self)
	{
		return new ESend(self, this.mid, this.payload);
		//return Send.get(self, this.mid, this.payload);
	}

	@Override
	//public GModelAction toGlobal(Role self)
	public SReceive toGlobal(Role self)
	{
		//return new GModelAction(this.peer, self, this.mid, this.payload);
		////return GModelAction.get(this.peer, self, this.mid, this.payload);
		return new SReceive(self, this.peer, this.mid, this.payload);

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
		if (!(o instanceof EReceive))
		{
			return false;
		}
		return ((EReceive) o).canEqual(this) && super.equals(o);
	}

	public boolean canEqual(Object o)
	{
		return o instanceof EReceive;
	}

	@Override
	protected String getCommSymbol()
	{
		return "?";
	}
}
