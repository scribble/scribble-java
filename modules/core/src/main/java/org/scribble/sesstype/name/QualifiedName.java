package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.Kind;



public abstract class QualifiedName<K extends Kind> extends Name<K>
{
	private static final long serialVersionUID = 1L;

	//public QualifiedName(KindEnum kind, String... elems)
	public QualifiedName(K kind, String... elems)
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
	public abstract Name<? extends Kind> getPrefix();
	public abstract Name<K> getSimpleName();
}
