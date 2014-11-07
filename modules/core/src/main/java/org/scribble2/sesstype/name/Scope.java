package org.scribble2.sesstype.name;

// Should be a compound name?
public class Scope extends CompoundName
{
	private static final long serialVersionUID = 1L;

	public static final String IMPLICIT_SCOPE_PREFIX = "__scope";
	public static final Scope EMPTY_SCOPE = new Scope();
	public static final Scope ROOT_SCOPE = new Scope("__root");

	protected Scope(String... elems)
	//public Scope(String... elems)  // For runtime sockets
	{
		super(Kind.SCOPE, elems);
	}
	
	public Scope(Scope prefix, SimpleName name)
	{
		this(compileScope(prefix, name));
	}
	
	// Also done by QualifiedName
	public Scope getPrefix()
	{
		return new Scope(getPrefixElements());
	}

	public SimpleName getSimpleName()
	{
		return new SimpleName(Kind.SCOPE, getLastElement());
	}
	
	private static String[] compileScope(Scope prefix, SimpleName name)
	{
		String[] tmp = prefix.getElements();
		String[] elems = new String[tmp.length + 1];
		System.arraycopy(tmp, 0, elems, 0, tmp.length);
		elems[elems.length - 1] = name.toString();
		return elems;
	}
}
