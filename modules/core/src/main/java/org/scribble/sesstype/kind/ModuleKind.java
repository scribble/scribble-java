package org.scribble.sesstype.kind;

public class ModuleKind extends Kind
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
		return true;
	}
}
