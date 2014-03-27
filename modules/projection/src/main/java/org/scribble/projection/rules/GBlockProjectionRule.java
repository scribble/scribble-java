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
import org.scribble.model.global.GActivity;
import org.scribble.model.global.GBlock;
import org.scribble.model.local.LActivity;
import org.scribble.model.local.LBlock;

/**
 * This class implements the GBlock projection rule.
 *
 */
public class GBlockProjectionRule implements ProjectionRule {

	/**
	 * {@inheritDoc}
	 */
	public Object project(ModuleContext context, ModelObject mobj,
						RoleDecl role, IssueLogger logger) {
		LBlock projected=new LBlock();
		GBlock source=(GBlock)mobj;

		projected.derivedFrom(source);
        
		for (GActivity act : source.getContents()) {
			ProjectionRule rule=ProjectionRuleFactory.getProjectionRule(act);
			
			if (rule != null) {
				Object local=rule.project(context, act, role, logger);
				
				if (local != null) {
					if (local instanceof LActivity) {
						projected.getContents().add((LActivity)local);
					} else if (local instanceof java.util.List) {
						for (Object lact : (java.util.List<?>)local) {
							if (lact instanceof LActivity) {
								projected.getContents().add((LActivity)lact);
							}
						}
					}
				}
			}			
		}
	
		return (projected);
	}
	
}
