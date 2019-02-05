package org.scribble.lang.global;

import java.util.List;

import org.scribble.lang.Protocol;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public class GProtocol extends Protocol<Global> implements GType
{
	public GProtocol(List<Role> roles, //List<?> params, 
			GSeq body)
	{
		super(roles, body);
	}

	@Override
	public int hashCode()
	{
		int hash = 11;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GProtocol))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GProtocol;
	}
}
