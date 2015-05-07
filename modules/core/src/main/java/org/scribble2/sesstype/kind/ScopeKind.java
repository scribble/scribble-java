package org.scribble2.sesstype.kind;

public class ScopeKind implements Kind
{
	public static final ScopeKind KIND = new ScopeKind();
	
	protected ScopeKind()
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
		if (!(o instanceof ScopeKind))
		{
			return false;
		}
		return true;
	}
}
