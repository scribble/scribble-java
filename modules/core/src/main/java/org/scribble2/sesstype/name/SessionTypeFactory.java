package org.scribble2.sesstype.name;

import java.util.Arrays;

@Deprecated
public class SessionTypeFactory
{
	public SessionTypeFactory()
	{
	}

	public static ModuleName parseModuleName(String name)
	{
		String[] elems = name.split("\\.");
		return new ModuleName(elems);
	}

	// From fullname
	public static ProtocolName parseProtocolName(String name)
	{
		String[] elems = name.split("\\.");
		if (elems.length < 2)
		{
			throw new RuntimeException("Bad protocol full name: " + name);
		}
		ModuleName mn = new ModuleName(Arrays.copyOfRange(elems, 0, elems.length - 1));
		return new ProtocolName(mn, elems[elems.length - 1]);
	}
	
	public static Scope parseScope(String name)
	{
		String[] elems = name.split("\\.");
		return new Scope(elems);
	}
}
