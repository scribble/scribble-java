package org.scribble2.sesstype.kind;

public abstract class Kind
{
	@Override
	public int hashCode()
	{
		return super.hashCode();  // Nothing to hash in override -- singleton pattern for each Kind makes it work with the following equals -- actually, given singleton pattern simple pointer equality is enough
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || this.getClass() != o.getClass())
		{
			return false;
		}
		return true;
	}
}
