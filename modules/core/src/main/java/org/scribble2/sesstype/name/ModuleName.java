package org.scribble2.sesstype.name;

import java.util.Arrays;

// General name: simple or full (a central class for value comparison)
public class ModuleName extends QualifiedName
{
	private static final long serialVersionUID = 1L;
	
	protected static final ModuleName EMPTY_MODULENAME = new ModuleName();
	
	protected ModuleName(String... elems)
	{
		super(Kind.MODULE, elems);
	}

	public ModuleName(PackageName packname, String modname)
	{
		super(Kind.MODULE, compileModuleName(packname, modname));
	}

	public ModuleName(String modname)
	{
		this(PackageName.EMPTY_PACKAGENAME, modname);
	}

	/*// FIXME: use java.nio.file.Path
	public String toPath()
	{
		String[] elems = getElements();
		String file = elems[0];
		for (int i = 1; i < elems.length; i++)
		{
			file += "/" + elems[i];
		}
		return file + "." + Util.SCRIBBLE_FILE_EXTENSION;
	}*/
	
	public boolean isSimpleName()
	{
		//return !isEmpty() && !isPrefixed();
		return !isPrefixed();
	}

	@Override
	public ModuleName getSimpleName()
	{
		return new ModuleName(getLastElement());
	}

	public PackageName getPrefix()
	{
		return new PackageName(getPrefixElements());
	}

	public PackageName getPackageName()
	{
		return getPrefix();
	}
	
	private static String[] compileModuleName(PackageName packname, String modname)
	{
		return compileElements(packname, modname);
	}

	protected static String[] compileElements(CompoundName cn, String n)
	{
		String[] prefix = cn.getElements();
		String[] elems = Arrays.copyOf(prefix, prefix.length + 1);
		elems[elems.length - 1] = n;
		return elems;
	}
}
