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
package org.scribble.common.module;

import org.scribble.model.Module;

/**
 * This interface is responsible for loading modules.
 *
 */
public interface ModuleLoader {

	/**
	 * This method loads the module associated with the specified name.
	 * 
	 * @param module The module name
	 * @return The module, or null if not found
	 */
	public Module loadModule(String module);
	
}
