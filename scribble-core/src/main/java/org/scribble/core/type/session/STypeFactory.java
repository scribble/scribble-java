/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.core.type.session;

import java.util.Arrays;

import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.ModuleName;
import org.scribble.core.type.name.PackageName;
import org.scribble.core.type.session.global.GTypeFactory;
import org.scribble.core.type.session.local.LTypeFactory;

public class STypeFactory
{
	public final GTypeFactory global;
	public final LTypeFactory local;

	public STypeFactory(GTypeFactory global, LTypeFactory local)
	{
		this.global = global;
		this.local = local;
	}
	
	// N.B. targetted by generated Session API
	// From fullname
	public static GProtoName parseGlobalProtocolName(String name)
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
		GProtoName gpn = new GProtoName(membname);
		return new GProtoName(modname, gpn);
	}
	
	
	
	
	
	

	/*public static ModuleName parseModuleName(String name)
	{
		String[] elems = name.split("\\.");
		return new ModuleName(elems);
	}*/
}
