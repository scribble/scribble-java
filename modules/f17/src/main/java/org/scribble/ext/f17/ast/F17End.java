package org.scribble.ext.f17.ast;


public abstract class F17End implements F17Type
{
	/*@Override
	public Set<RecVar> freeVariables()
	{
		return new java.util.HashSet<RecVar>();
	}
	
	@Override
	public Set<Role> roles()
	{
		return new java.util.HashSet<Role>();
	}*/
	
	@Override 
	public String toString()
	{
		return "end";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof F17End))
		{
			return false;
		}
		return ((F17End) obj).canEquals(this);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof F17End;
	}

	@Override
	public int hashCode()
	{
		return 997;
	}
}
