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
package org.scribble.context;

import org.scribble.ast.Module;
import org.scribble.resources.Resource;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.util.Pair;


/**
 * This class provides a default implementation of the module loader interface.
 *
 */
public class DefaultModuleLoader implements ModuleLoader
{
  // A caching mechanism? -- not currently used for anything meaningful
	// FIXME: redundant? Modules recorded (and updated) in MainContext
	// ModuleName is full module name
	private java.util.Map<ModuleName, Pair<Resource, Module>> _modules=new java.util.HashMap<>();

	/**
	 * This method registers the supplied module.
	 * 
	 * @param module The module
	 */
	public void registerModule(Resource res, Module module)
	{
		_modules.put(module.getFullModuleName(), new Pair<>(res, module));
	}

	/**
	 * {@inheritDoc}
	 */
	public Pair<Resource, Module> loadModule(ModuleName modname)
	{
		return (_modules.get(modname));
	}
}
