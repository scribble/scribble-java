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
package org.scribble.main;

import org.scribble.ast.AstFactory;
import org.scribble.ast.Module;
import org.scribble.main.resource.Resource;
import org.scribble.type.name.ModuleName;
import org.scribble.util.Pair;
import org.scribble.util.ScribParserException;


/**
 * This interface is responsible for loading modules.
 *
 * loading = sesstype.ModuleName -> Pair<Resource, Module>
 *   ModuleName --> Path --ResourceLocator--> Resource --AntlrParser--> ANTLR --ScribParser--> ScribNode
 *   
 * FIXME: Path should be abstracted, e.g. URI (Path is tied to DirectoryResourceLocator)
 */
public interface ModuleLoader
{
	/**
	 * This method loads the module associated with the specified name.
	 * 
	 * @param module The module name
	 * @return The module, or null if not found
	 * @throws ScribParserException 
	 */
	public Pair<Resource, Module> loadModule(ModuleName modname, AstFactory af) throws ScribParserException, ScribbleException;
}

