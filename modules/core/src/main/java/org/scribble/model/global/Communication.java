package org.scribble.model.global;

import org.scribble.model.local.IOAction;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

@Deprecated
public class Communication extends IOAction implements PathElement
{
	//private static int counter = 1;

	//public final int id;
	public final Role src;
	
	public Communication(Role src, Role dest, MessageId<?> mid, Payload payload)
	{
		super(dest, mid, payload);
		//this.id = counter++;
		this.src = src;
	}
	
	/*@Override
	public int hashCode()
	{
		//return 827 * this.id;
		return 827 * this.src.hashCode() + super.hashCode();
	}*/
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Communication))
		{
			return false;
		}
		Communication tmp = (Communication) o;
		//return this.id == tmp.id ;
		return tmp.canEqual(this) && tmp.src.equals(this.src) && super.equals(o);
	}

	@Override
	public String toString()
	{
		//return this.id + ":" + this.src + ":" + this.action + " " + this.deps.stream().map((d) -> d.id).collect(Collectors.toList());
		return this.src + "->" + super.toString();
	}

	@Override
	protected String getCommSymbol()
	{
		return ": ";
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof Communication;
	}

	@Override
	//public GModelAction toGlobal(Role self)
	public GIOAction toGlobal(Role self)
	{
		throw new RuntimeException("TODO: " + this + ", " + self);
	}

	@Override
	public IOAction toDual(Role self)
	{
		throw new RuntimeException("TODO: " + this + ", " + self);
	}
}