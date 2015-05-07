package org.scribble2.sesstype.kind;

public class Local extends ProtocolKind
{
	public static final Local KIND = new Local();
	
	protected Local()
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
		if (o == null | !(o instanceof Local))
		{
			return false;
		}
		return true;
	}
}
