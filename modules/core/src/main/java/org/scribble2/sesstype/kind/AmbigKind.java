package org.scribble2.sesstype.kind;

public class AmbigKind extends Kind
{
	public static final AmbigKind KIND = new AmbigKind();
	
	protected AmbigKind()
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
		if (!(o instanceof AmbigKind))
		{
			return false;
		}
		return true;
	}
}
