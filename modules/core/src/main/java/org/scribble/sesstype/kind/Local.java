package org.scribble.sesstype.kind;

public class Local extends AbstractKind implements ProtocolKind, PayloadTypeKind
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
		if (!(o instanceof Local))
		{
			return false;
		}
		return ((Local) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof Local;
	}
}
