package org.scribble2.sesstype.kind;

public class RecVarKind extends Kind
{
	public static final RecVarKind KIND = new RecVarKind();
	
	protected RecVarKind()
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
		if (!(o instanceof RecVarKind))
		{
			return false;
		}
		return true;
	}
}
