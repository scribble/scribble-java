package org.scribble2.sesstype.kind;

public class SigKind implements Kind
{
	public static final SigKind KIND = new SigKind();
	
	protected SigKind()
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
		if (!(o instanceof SigKind))
		{
			return false;
		}
		return true;
	}
}
