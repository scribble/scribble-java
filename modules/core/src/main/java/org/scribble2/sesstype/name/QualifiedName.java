package org.scribble2.sesstype.name;



public abstract class QualifiedName extends CompoundName
{
	private static final long serialVersionUID = 1L;

	public QualifiedName(Kind kind, String... elems)
	{
		super(kind, elems);
	}

	@Override
	public boolean isEmpty()
	{
		return super.isEmpty();
	}

	@Override
	public boolean isPrefixed()
	{
		return super.isPrefixed();
	}
	
	// Also done by Scope
	public abstract CompoundName getPrefix();
	public abstract Name getSimpleName();
}
