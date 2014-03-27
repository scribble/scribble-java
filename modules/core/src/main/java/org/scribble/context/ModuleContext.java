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

import org.scribble.model.ModelObject;
import org.scribble.model.Module;
import org.scribble.resources.Resource;

/**
 * This interface represents the validation context.
 *
 */
public interface ModuleContext {

	/**
	 * This method returns the resource associated with the module.
	 * 
	 * @return The resource
	 */
	public Resource getResource();

	/**
	 * This method imports and returns the named module.
	 * 
	 * @param module The module name
	 * @return The module, or null if not found
	 */
	public Module importModule(String module);

	/**
	 * This method returns member associated with a name. If the supplied
	 * name is fully qualified, then all but the last component of the name
	 * will identify the module associated with the member. If only a simple
	 * name is provided, then the member will be obtained from the module
	 * associated with the context.
	 * 
	 * @param name The (optionally fully qualified) member name, e.g. [<module>.]<member>
	 * @return The member, or null if not found
	 */
	public ModelObject getMember(String name);
	
	/**
	 * This method returns a member associated with a specified
	 * named module. If the module name is not supplied, then the member
	 * will be obtained from the module associated with the context.
	 * 
	 * @param module The optional module name
	 * @param member The member name
	 * @return The member, or null if not found
	 */
	public ModelObject getMember(String module, String member);
	
}
