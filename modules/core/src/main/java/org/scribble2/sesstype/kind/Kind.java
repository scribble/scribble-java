package org.scribble2.sesstype.kind;

public abstract class Kind
{
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
		if (o == null | !(o instanceof Kind))
		{
			return false;
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		return this.getClass().toString();
	}
}
