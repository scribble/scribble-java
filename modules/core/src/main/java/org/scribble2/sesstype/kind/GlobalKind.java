package org.scribble2.sesstype.kind;

public class GlobalKind extends Kind
{
	public static final GlobalKind KIND = new GlobalKind();
	
	protected GlobalKind()
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
		if (o == null | !(o instanceof GlobalKind))
		{
			return false;
		}
		return true;
	}
}
