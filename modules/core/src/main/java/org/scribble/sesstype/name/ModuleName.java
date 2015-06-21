package org.scribble.sesstype.name;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.scribble.ast.Constants;
import org.scribble.sesstype.kind.ModuleKind;

// General name: simple or full (a central class for value comparison)
public class ModuleName extends QualifiedName<ModuleKind>
{
	private static final long serialVersionUID = 1L;
	
	protected static final ModuleName EMPTY_MODULENAME = new ModuleName();
	
	protected ModuleName(String... elems)
	{
		super(ModuleKind.KIND, elems);
	}

	public ModuleName(PackageName packname, ModuleName modname)
	{
		super(ModuleKind.KIND, compileModuleName(packname, modname));
	}

	public ModuleName(String modname)
	{
		super(ModuleKind.KIND, Name.compileElements(PackageName.EMPTY_PACKAGENAME.getElements(), modname));
	}

	public Path toPath()
	{
		String[] elems = getElements();
		String file = elems[0];
		for (int i = 1; i < elems.length; i++)
		{
			file += "/" + elems[i];
		}
		return Paths.get(file + "." + Constants.SCRIBBLE_FILE_EXTENSION);
	}
	
	public boolean isSimpleName()
	{
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
	
	private static String[] compileModuleName(PackageName packname, ModuleName modname)
	{
		return Name.compileElements(packname.getElements(), modname.getLastElement());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ModuleName))
		{
			return false;
		}
		ModuleName n = (ModuleName) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof ModuleName;
	}

	@Override
	public int hashCode()
	{
		int hash = 2797;
		hash = 31 * super.hashCode();
		return hash;
	}
}
