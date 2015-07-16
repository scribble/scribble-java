package org.scribble.sesstype;

import java.util.Arrays;

import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.PackageName;

public class SessionTypeFactory
{
	public SessionTypeFactory()
	{

	}

	/*public static ModuleName parseModuleName(String name)
	{
		String[] elems = name.split("\\.");
		return new ModuleName(elems);
	}*/

	// From fullname
	public static GProtocolName parseGlobalProtocolName(String name)
	{
		String[] elems = name.split("\\.");
		if (elems.length < 2)
		{
			throw new RuntimeException("Bad protocol full name: " + name);
		}
		String membname = elems[elems.length - 1];
		ModuleName modname = new ModuleName(elems[elems.length - 2]);
		if (elems.length > 2)
		{
			PackageName packname = new PackageName(Arrays.copyOfRange(elems, 0, elems.length - 2));
			modname = new ModuleName(packname, modname);
		}
		GProtocolName gpn = new GProtocolName(membname);
		return new GProtocolName(modname, gpn);
	}
	
	/*public static Scope parseScope(String name)
	{
		String[] elems = name.split("\\.");
		return new Scope(elems);
	}*/
}
