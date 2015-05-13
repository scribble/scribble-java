package org.scribble2.sesstype.kind;

public class AmbiguousKind extends Kind
{
	public static final AmbiguousKind KIND = new AmbiguousKind();
	
	protected AmbiguousKind()
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
		if (!(o instanceof AmbiguousKind))
		{
			return false;
		}
		return true;
	}
}
