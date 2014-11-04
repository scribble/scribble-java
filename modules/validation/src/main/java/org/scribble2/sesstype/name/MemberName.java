package org.scribble2.sesstype.name;


// Simple name or qualified name
public abstract class MemberName extends QualifiedName //SerializableSimpleName
{
	private static final long serialVersionUID = 1L;
	
	public MemberName(Kind kind, ModuleName modname, String membname)
	{
		super(kind, compileMemberName(modname, membname));
	}
	
	public MemberName(Kind kind, String simplename)
	{
		this(kind, ModuleName.EMPTY_MODULENAME, simplename);
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
	private static String[] compileMemberName(ModuleName modname, String membname)
	{
		return ModuleName.compileElements(modname, membname);
	}
}
