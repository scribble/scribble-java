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

import org.scribble.context.ModuleContext;
import org.scribble.logging.IssueLogger;
import org.scribble.model.ModelObject;
import org.scribble.model.RoleDecl;
import org.scribble.model.global.GBlock;
import org.scribble.model.global.GParallel;
import org.scribble.model.local.LBlock;
import org.scribble.model.local.LParallel;

/**
 * This class implements the GParallel projection rule.
 *
 */
public class GParallelProjectionRule implements ProjectionRule {

	/**
	 * {@inheritDoc}
	 */
	public Object project(ModuleContext context, ModelObject mobj,
									RoleDecl role, IssueLogger logger) {
		LParallel projected=null;
		GParallel source=(GParallel)mobj;
		
		if (source.isRoleInvolved(role)) {
			projected = new LParallel();
			
			projected.derivedFrom(source);
	        
			for (GBlock path : source.getPaths()) {
				ProjectionRule rule=ProjectionRuleFactory.getProjectionRule(path);
				
				if (rule != null) {
					LBlock lb=(LBlock)rule.project(context, path, role, logger);
					
					if (lb != null) {
						projected.getPaths().add(lb);
					}
				}				
			}
		}
        
        return (projected);
	}
}
