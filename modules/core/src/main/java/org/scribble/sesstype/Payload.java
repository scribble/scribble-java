package org.scribble.sesstype;

import java.util.Collections;
import java.util.List;

import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.PayloadTypeKind;
import org.scribble.sesstype.name.PayloadType;

public class Payload
{
	public static final Payload EMPTY_PAYLOAD = new Payload(Collections.emptyList());
	
	public final List<PayloadType<? extends PayloadTypeKind>> elems;
	
	public Payload(List<PayloadType<? extends PayloadTypeKind>> payload)
	{
		this.elems = payload;
	}
	
	public boolean isEmpty()
	{
		return this.elems.isEmpty();
	}

	@Override
	public int hashCode()
	{
		int hash = 577;
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Payload))
		{
			return false;
		}
		return this.elems.equals(((Payload) o).elems);
	}
	
	@Override
	public String toString()
	{
		if (this.elems.isEmpty())
		{
			return "()";
		}
		String payload = "(" + this.elems.get(0);
		for (PayloadType<? extends Kind> pt : this.elems.subList(1, this.elems.size()))
		{
			payload+= ", " + pt;
		}
		return payload + ")";
	}
	
	/*@Override
	public boolean isParameter()
	{
		return false;
	}*/
}
