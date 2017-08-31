package org.scribble.ext.go.core.model.endpoint.action;

import org.scribble.ext.go.core.model.endpoint.ParamCoreEModelFactory;
import org.scribble.ext.go.core.type.ParamRange;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.ext.go.type.index.ParamIndexExpr;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.model.global.SModelFactory;
import org.scribble.model.global.actions.SReceive;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;

public class ParamCoreEDotReceive extends EReceive implements ParamCoreEAction
{
	public final ParamIndexExpr offset;
	
	public ParamCoreEDotReceive(ParamCoreEModelFactory ef, ParamRole peer, ParamIndexExpr offset, MessageId<?> mid, Payload payload)
	{
		super(ef, peer, mid, payload);
		this.offset = offset;
	}

	@Override
	public ParamRole getPeer()
	{
		return (ParamRole) this.peer;
	}
	
	@Override
	public ParamCoreEDotSend toDual(Role self)
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
		ParamRole peer = getPeer();
		ParamRange g = peer.ranges.iterator().next();
		return peer.getName() + "[" + this.offset + ":" + g.start + ".." + g.end + "]"
				+ getCommSymbol() + this.mid + this.payload;
	}

	@Override
	public String toStringWithMessageIdHack()
	{
		String m = this.mid.isMessageSigName() ? "^" + this.mid : this.mid.toString();  // HACK
		ParamRole peer = getPeer();
		ParamRange g = peer.ranges.iterator().next();
		return peer.getName() + "[" + this.offset + ":" + g.start + ".." + g.end + "]"
				+ getCommSymbol() + m + this.payload;
	}

	@Override
	protected String getCommSymbol()
	{
		return "?=";
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7213;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.offset.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ParamCoreEDotReceive))
		{
			return false;
		}
		return super.equals(o)  // Does canEquals
				&& this.offset.equals(((ParamCoreEDotSend) o).offset);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof ParamCoreEDotReceive;
	}
}
