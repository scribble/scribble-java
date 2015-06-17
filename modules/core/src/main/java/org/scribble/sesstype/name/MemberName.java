package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.Kind;


// Simple name or qualified name
public abstract class MemberName<K extends Kind> extends QualifiedName<K> //SerializableSimpleName
{
	private static final long serialVersionUID = 1L;
	
	//public MemberName(KindEnum kind, ModuleName modname, String membname)
	public MemberName(K kind, ModuleName modname, Name<K> membname)
	{
		super(kind, compileMemberName(modname, membname));
	}
	
	//public MemberName(KindEnum kind, String simplename)
	public MemberName(K kind, String simplename)
	{
		//this(kind, ModuleName.EMPTY_MODULENAME, simplename);
		super(kind, Name.compileElements(ModuleName.EMPTY_MODULENAME.getElements(), simplename));
	}
	
	/*@Override
	public MemberName getSimpleName()
	{
		return new MemberName(this.kind, ModuleName.EMPTY_MODULENAME, getLastElement());
	}*/

	//public ModuleName getModuleQualifier()
	public ModuleName getPrefix()
	{
		/*CompoundName modname = getPrefix();
		if (modname.isPrefixed())
		{
			PackageName packname = new PackageName(modname.getPrefix().getElements());
			return new ModuleName(packname, modname.getSimpleName().toString());
		}
		return new ModuleName(modname.toString());*/
		//return new ModuleName(modname.getElements());
		return new ModuleName(getPrefixElements());
	}
	
	// Similar in ModuleName
	//private static String[] compileMemberName(ModuleName modname, String membname)
	private static String[] compileMemberName(ModuleName modname, Name<? extends Kind> membname)
	{
		return Name.compileElements(modname.getElements(), membname.getLastElement());
	}
}
