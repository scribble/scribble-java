package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.Kind;


// Simple name or qualified name
public abstract class MemberName<K extends Kind> extends QualifiedName<K>
{
	private static final long serialVersionUID = 1L;
	
	public MemberName(K kind, ModuleName modname, Name<K> membname)
	{
		super(kind, compileMemberName(modname, membname));
	}
	
	public MemberName(K kind, String simplename)
	{
		super(kind, Name.compileElements(ModuleName.EMPTY_MODULENAME.getElements(), simplename));
	}
	
	public ModuleName getPrefix()
	{
		return new ModuleName(getPrefixElements());
	}
	
	// Similar in ModuleName
	private static String[] compileMemberName(ModuleName modname, Name<? extends Kind> membname)
	{
		return Name.compileElements(modname.getElements(), membname.getLastElement());
	}
}
