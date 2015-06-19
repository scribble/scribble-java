package org.scribble.sesstype.kind;

public class SigKind extends Kind
{
	public static final SigKind KIND = new SigKind();
	
	protected SigKind()
	{

	}
	
	/*@Override
	public String toString()
	{
		return "sig";
	}*/

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