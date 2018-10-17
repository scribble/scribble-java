package org.scribble.ext.go.core.model.endpoint.action;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ext.go.core.model.endpoint.RPCoreEModelFactory;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.model.global.SModelFactory;
import org.scribble.model.global.actions.SReceive;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;

public class RPCoreEMultiChoicesReceive extends EReceive implements RPCoreEAction
{
	public final List<MessageId<?>> mids;
	public final List<Payload> pays;  // mids.size() == pays.size()
	
	public RPCoreEMultiChoicesReceive(RPCoreEModelFactory ef, RPIndexedRole peer, List<MessageId<?>> mids, List<Payload> pays)
	{
		super(ef, peer, null, null);  // null OK?
		this.mids = Collections.unmodifiableList(mids);
		this.pays = Collections.unmodifiableList(pays);
	}

	@Override
	public RPIndexedRole getPeer()
	{
		return (RPIndexedRole) this.peer;
	}
	
	@Override
	public RPCoreECrossSend toDual(Role self)
	{
		throw new RuntimeException("[param-core] Shouldn't get in here: " + this);
	}

	@Override
	public SReceive toGlobal(SModelFactory sf, Role self)
	{
		throw new RuntimeException("[param-core] Shouldn't get in here: " + this);
	}
	
	@Override
	public String toString()
	{
		Iterator<Payload> pays = this.pays.iterator();
		String ms = "{" + this.mids.stream().map(m -> m.toString() + pays.next().toString()).collect(Collectors.joining(", ")) + "}";
		return this.peer + getCommSymbol() + ms;
	}

	@Override
	public String toStringWithMessageIdHack()
	{
		Iterator<Payload> pays = this.pays.iterator();
		String ms = "{" + this.mids.stream().map(m -> (m.isMessageSigName() ? "^" + m : m.toString())
				+ pays.next().toString()).collect(Collectors.joining(", ")) + "}";
		return this.obj + getCommSymbol() + ms;
	}

	@Override
	protected String getCommSymbol()
	{
		return "?*";
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7219;
		hash = 31 * hash + this.obj.hashCode();
		hash = 31 * hash + this.mids.hashCode();  // N.B. excluding this.mid, this.payload
		hash = 31 * hash + this.pays.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RPCoreEMultiChoicesReceive))
		{
			return false;
		}
		RPCoreEMultiChoicesReceive them = (RPCoreEMultiChoicesReceive) o;
		return them.canEqual(this) &&   // N.B. don't call super: this.mid, this.pay == null
				this.obj.equals(them.obj) && this.mids.equals(them.mids) && this.pays.equals(them.pays);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof RPCoreEMultiChoicesReceive;
	}
}
