package org.scribble.sesstype.kind;

public class ModuleKind extends AbstractKind implements ImportKind
{
	public static final ModuleKind KIND = new ModuleKind();
	
	protected ModuleKind()
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
		if (!(o instanceof ModuleKind))
		{
			return false;
		}
		return ((ModuleKind) o).canEqual(this);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof ModuleKind;
	}
}
