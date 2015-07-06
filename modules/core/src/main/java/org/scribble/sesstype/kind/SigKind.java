package org.scribble.sesstype.kind;


// Following sesstype.Arg hierarchy
public class SigKind extends AbstractKind implements NonRoleParamKind, MessageIdKind, //ArgKind
		ModuleMemberKind
{
	public static final SigKind KIND = new SigKind();
	
	protected SigKind()
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
		if (!(o instanceof SigKind))
		{
			return false;
		}
		return ((SigKind) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof SigKind;
	}
}
