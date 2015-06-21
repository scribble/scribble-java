package org.scribble.sesstype.kind;

public class RoleKind extends AbstractKind implements ParamKind
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
		return ((RoleKind) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof RoleKind;
	}
}
