package org.scribble2.sesstype.kind;

public class LocalKind extends Kind
{
	public static final LocalKind KIND = new LocalKind();
	
	protected LocalKind()
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
		if (o == null | !(o instanceof LocalKind))
		{
			return false;
		}
		return true;
	}
}
