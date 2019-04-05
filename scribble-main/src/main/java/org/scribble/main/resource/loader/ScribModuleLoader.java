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
package org.scribble.main.resource.loader;

import org.scribble.ast.AstFactory;
import org.scribble.ast.Module;
import org.scribble.core.type.name.ModuleName;
import org.scribble.main.ScribAntlrWrapper;
import org.scribble.main.resource.Resource;
import org.scribble.main.resource.locator.ResourceLocator;
import org.scribble.util.Pair;
import org.scribble.util.ScribParserException;
import org.scribble.util.ScribException;

// loading: ModuleName -> Module
//   ModuleName --> Path --ResourceLocator--> Resource --ScribAntlrWrapper--> Module
public class ScribModuleLoader extends DefaultModuleLoader
{
	private ResourceLocator locator;
	private ScribAntlrWrapper antlr;

	public ScribModuleLoader(ResourceLocator locator, ScribAntlrWrapper antlr)
	{
		this.locator = locator;
		this.antlr = antlr;
	}

	@Override
	public Pair<Resource, Module> loadModule(ModuleName fullname, AstFactory af)
			throws ScribParserException, ScribException
	{
		Pair<Resource, Module> cached = super.loadModule(fullname, af);
		if (cached != null)
		{
			return cached;
		}

		Resource res = this.locator.getResource(fullname.toPath());
		Module m = this.antlr.parse(res);  // Does del decoration
		ScribModuleLoader.checkModuleName(fullname, res, m);
		registerModule(res, m);
		return new Pair<>(res, m);
	}

	// Check module loaded from fullname.toPath has the expected mod decl
	private static void checkModuleName(ModuleName fullname, Resource res,
			Module m)
	{
		if (!fullname.equals(m.getFullModuleName()))
		{
			throw new RuntimeException("Invalid module name "
					+ m.getFullModuleName() + " at location: " + res.getLocation());
		}
	}
}
