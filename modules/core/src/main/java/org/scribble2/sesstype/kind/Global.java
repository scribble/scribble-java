package org.scribble2.sesstype.kind;

public class Global extends ProtocolKind
{
	public static final Global KIND = new Global();
	
	protected Global()
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
		if (o == null | !(o instanceof Global))
		{
			return false;
		}
		return true;
	}
}
