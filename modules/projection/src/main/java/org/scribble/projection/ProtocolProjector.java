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
package org.scribble.projection;

import org.scribble.common.logging.ScribbleLogger;
import org.scribble.common.module.DefaultModuleContext;
import org.scribble.common.module.ModuleCache;
import org.scribble.common.module.ModuleLoader;
import org.scribble.common.resources.Resource;
import org.scribble.model.Module;
import org.scribble.projection.rules.ProjectionRule;
import org.scribble.projection.rules.ProjectionRuleFactory;

/**
 * This class is responsible for projecting a protocol module.
 *
 */
public class ProtocolProjector {

	/**
	 * This method validates the supplied module, reporting
	 * any issues to the logger.
	 * 
	 * @param resource The resource
	 * @param module The module
	 * @param loader The module loader
	 * @param logger The logger
	 * @return The set of modules representing the local projections
	 */
	@SuppressWarnings("unchecked")
	public java.util.Set<Module> project(Resource resource, Module module,
							ModuleLoader loader, ScribbleLogger logger) {
		java.util.Set<Module> ret=null;
		ProjectionRule rule=ProjectionRuleFactory.getProjectionRule(module);
		
		if (rule != null) {
            DefaultModuleContext context=new DefaultModuleContext(resource,
            				module, loader, new ModuleCache());
            
			ret = (java.util.Set<Module>)rule.project(context, module, null, logger);
		}
		
		return (ret);
	}
}
