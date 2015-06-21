package org.scribble.sesstype.kind;

public class ScopeKind extends AbstractKind
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
		return ((ScopeKind) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof ScopeKind;
	}
}
