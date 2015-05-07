package org.scribble2.sesstype.kind;

public class PackageKind implements Kind
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
		return true;
	}
}
