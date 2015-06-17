package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.ScopeKind;

// Should be a compound name?
//public class Scope extends Name
public class Scope extends Name<ScopeKind>
{
	private static final long serialVersionUID = 1L;

	public static final String IMPLICIT_SCOPE_PREFIX = "__scope";
	public static final Scope EMPTY_SCOPE = new Scope();
	public static final Scope ROOT_SCOPE = new Scope("__root");

	protected Scope(String... elems)
	//public Scope(String... elems)  // For runtime sockets
	{
		//super(KindEnum.SCOPE, elems);
		super(ScopeKind.KIND, elems);
	}
	
	public Scope(String name)
	{
		this(new String[] { name });
	}

	//public Scope(Scope prefix, SimpleName name)
	public Scope(Scope prefix, Name<ScopeKind> name)
	{
		this(compileScope(prefix, name));
	}
	
	// Also done by QualifiedName
	public Scope getPrefix()
	{
		return new Scope(getPrefixElements());
	}

	//public SimpleName getSimpleName()
	public Scope getSimpleName()
	{
		//return new SimpleName(KindEnum.SCOPE, getLastElement());
		return new Scope(getLastElement());
	}
	
	//private static String[] compileScope(Scope prefix, SimpleName name)
	private static String[] compileScope(Scope prefix, Name<ScopeKind> name)
	{
		String[] tmp = prefix.getElements();
		String[] elems = new String[tmp.length + 1];
		System.arraycopy(tmp, 0, elems, 0, tmp.length);
		elems[elems.length - 1] = name.toString();
		return elems;
	}
}
