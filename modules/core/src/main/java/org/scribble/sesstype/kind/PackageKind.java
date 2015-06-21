package org.scribble.sesstype.kind;

public class PackageKind extends AbstractKind
{
	public static final PackageKind KIND = new PackageKind();
	
	protected PackageKind()
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
		if (!(o instanceof PackageKind))
		{
			return false;
		}
		return ((PackageKind) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof PackageKind;
	}
}
