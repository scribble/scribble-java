package org.scribble2.sesstype.name;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.scribble2.model.Constants;
import org.scribble2.sesstype.kind.ModuleKind;

// General name: simple or full (a central class for value comparison)
public class ModuleName extends QualifiedName<ModuleKind>
{
	private static final long serialVersionUID = 1L;
	
	protected static final ModuleName EMPTY_MODULENAME = new ModuleName();
	
	protected ModuleName(String... elems)
	{
		//super(KindEnum.MODULE, elems);
		super(ModuleKind.KIND, elems);
	}

	//public ModuleName(PackageName packname, String modname)
	public ModuleName(PackageName packname, ModuleName modname)
	{
		//super(KindEnum.MODULE, compileModuleName(packname, modname));
		super(ModuleKind.KIND, compileModuleName(packname, modname));
	}

	public ModuleName(String modname)
	{
		//this(PackageName.EMPTY_PACKAGENAME, modname);
		super(ModuleKind.KIND, Name.compileElements(PackageName.EMPTY_PACKAGENAME.getElements(), modname));
	}

	// FIXME: use java.nio.file.Path
	//public String toPath()
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
	
	//private static String[] compileModuleName(PackageName packname, String modname)
	private static String[] compileModuleName(PackageName packname, ModuleName modname)
	{
		//return compileElements(packname, modname);
		return Name.compileElements(packname.getElements(), modname.getLastElement());
	}
}
