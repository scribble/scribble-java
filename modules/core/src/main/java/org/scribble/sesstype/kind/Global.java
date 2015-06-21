package org.scribble.sesstype.kind;

public class Global extends AbstractKind implements ProtocolKind
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
		if (!(o instanceof Global))
		{
			return false;
		}
		return ((Global) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof Global;
	}
}
