package org.scribble2.sesstype.kind;

public class DataTypeKind extends Kind
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
		return true;
	}
}
