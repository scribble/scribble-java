package org.scribble.sesstype.kind;

public class DataTypeKind extends AbstractKind implements PayloadTypeKind, ImportKind, ModuleMemberKind
{
	public static final DataTypeKind KIND = new DataTypeKind();
	
	protected DataTypeKind()
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
		if (!(o instanceof DataTypeKind))
		{
			return false;
		}
		return ((DataTypeKind) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof DataTypeKind;
	}
}
