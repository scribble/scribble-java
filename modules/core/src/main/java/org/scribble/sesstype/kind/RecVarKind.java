package org.scribble.sesstype.kind;

public class RecVarKind extends AbstractKind
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
		return ((RecVarKind) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof RecVarKind;
	}
}
