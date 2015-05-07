package org.scribble2.sesstype.kind;

public class RoleKind implements Kind
{
	public static final RoleKind KIND = new RoleKind();
	
	protected RoleKind()
	{

	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this)
		{
			return true;
		}
		if (!(o instanceof RoleKind))
		{
			return false;
		}
		return true;
	}
}
