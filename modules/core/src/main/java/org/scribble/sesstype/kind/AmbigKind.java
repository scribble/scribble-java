package org.scribble.sesstype.kind;


public class AmbigKind extends AbstractKind implements NonRoleArgKind//, PayloadTypeKind
{
	public static final AmbigKind KIND = new AmbigKind();
	
	protected AmbigKind()
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
		if (!(o instanceof AmbigKind))
		{
			return false;
		}
		return ((AmbigKind) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof AmbigKind;
	}
}
