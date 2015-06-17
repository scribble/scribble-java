/*
 * Copyright 2009-11 www.scribble.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble.parser;

import org.scribble.ast.Module;
import org.scribble.context.DefaultModuleLoader;
import org.scribble.resources.Resource;
import org.scribble.resources.ResourceLocator;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.util.Pair;

// loading = ModuleName -> Module
//   ModuleName --> Path --ResourceLocator--> Resource --AntlrParser--> ANTLR --ScribParser--> ScribNode
// FIXME: should be in org.scribble.context -- here due to Maven dependency restrictions
public class ScribbleModuleLoader extends DefaultModuleLoader //implements ModuleLoader
{
	private ResourceLocator locator;
	private AntlrParser antlr;
	private ScribbleParser parser;
	
	public ScribbleModuleLoader(ResourceLocator locator, AntlrParser antlr, ScribbleParser parser)
	{
		this.locator = locator;
		this.antlr = antlr;
		this.parser = parser;
	}

	@Override
	public Pair<Resource, Module> loadModule(ModuleName modname)
	{
		Pair<Resource, Module> cached = super.loadModule(modname);
		if (cached != null)
		{
			return cached;
		}	
		Resource res = this.locator.getResource(modname.toPath());
		Module parsed = (Module) this.parser.parse(this.antlr.parseAntlrTree(res));
		checkModuleName(modname, res, parsed);
		registerModule(res, parsed);
		return new Pair<>(res, parsed);
	}

	private static void checkModuleName(ModuleName mn, Resource res, Module mod)
	{
		if (!mn.equals(mod.getFullModuleName()))
		{
			throw new RuntimeException("Invalid module name " + mod.getFullModuleName() + " at path: " + res.getPath());
		}
	}
}
