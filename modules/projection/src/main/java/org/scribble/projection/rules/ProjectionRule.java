/*
 * Copyright 2009-14 www.scribble.org
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
package org.scribble.projection.rules;

import org.scribble.common.logging.ScribbleLogger;
import org.scribble.common.module.ModuleContext;
import org.scribble.model.ModelObject;
import org.scribble.model.RoleDecl;

/**
 * This interface represents a projection rule.
 *
 */
public interface ProjectionRule {

	/**
	 * This method projects the supplied model object
	 * and reports any relevant issues to the supplied logger.
	 * 
	 * @param context The module context
	 * @param mobj The model object
	 * @param role The role to project
	 * @param logger The logger
	 * @return The projected object, or list of objects
	 */
	public Object project(ModuleContext context, ModelObject mobj,
					RoleDecl role, ScribbleLogger logger);
	
}
