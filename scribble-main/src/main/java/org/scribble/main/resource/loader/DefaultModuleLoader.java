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

import org.scribble.ast.Module;
import org.scribble.core.type.name.ModuleName;
import org.scribble.main.resource.Resource;
import org.scribble.util.Pair;
import org.scribble.util.ScribException;
import org.scribble.util.ScribParserException;


/**
 * This class provides a default implementation of the module loader interface.
 *
 */
public abstract class DefaultModuleLoader implements ModuleLoader
{
	// CHECKME: redundant? Modules recorded (and updated) in MainContext
	private java.util.Map<ModuleName, Pair<Resource, Module>> _modules=new java.util.HashMap<>();

	/**
	 * This method registers the supplied module.
	 * 
	 * @param m The module
	 */
	public void registerModule(Resource res, Module m)
	{
		_modules.put(m.getFullModuleName(), new Pair<>(res, m));
	}

	/**
	 * {@inheritDoc}
	 * @throws ScribParserException 
	 */
	@Override
	public Pair<Resource, Module> loadModule(ModuleName fullname)
			throws ScribParserException, ScribException
	{
		return (_modules.get(fullname));
	}
}
