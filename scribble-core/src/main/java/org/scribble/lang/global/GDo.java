package org.scribble.lang.global;

import java.util.List;

import org.scribble.lang.Do;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public class GDo extends Do<Global> implements GType
{
	public GDo(List<Role> roles)
	{
		super(roles);
	}

	@Override
	public int hashCode()
	{
		int hash = 193;
		hash = 31 * hash + this.roles.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GDo))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GDo;
	}
}
