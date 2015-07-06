package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.RoleKind;


public class Role extends AbstractName<RoleKind>
{
	public static final Role EMPTY_ROLE = new Role();

	private static final long serialVersionUID = 1L;

	protected Role()
	{
		super(RoleKind.KIND);
	}

	public Role(String text)
	{
		super(RoleKind.KIND, text);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Role))
		{
			return false;
		}
		Role n = (Role) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof Role;
	}

	@Override
	public int hashCode()
	{
		int hash = 2741;
		hash = 31 * super.hashCode();
		return hash;
	}
}
