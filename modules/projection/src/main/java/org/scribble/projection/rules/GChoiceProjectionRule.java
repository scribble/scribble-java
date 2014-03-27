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
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;
import org.scribble.model.global.GChoice;
import org.scribble.model.local.LBlock;
import org.scribble.model.local.LChoice;
import org.scribble.projection.util.ChoiceMergingUtil;

/**
 * This class implements the GChoice projection rule.
 *
 */
public class GChoiceProjectionRule implements ProjectionRule {

	/**
	 * {@inheritDoc}
	 */
	public Object project(ModuleContext context, ModelObject mobj,
						RoleDecl role, IssueLogger logger) {
		// TODO: NOTE this implementation differs from the spec currently, which
		// does not take into account merging. Using the merging approach
		// from the previous implementation.
		
		LChoice projected=new LChoice();
		GChoice source=(GChoice)mobj;
		
		projected.derivedFrom(source);
        
        projected.setRole(new Role(source.getRole()));
        
        boolean f_missingBranch=false;
        
        for (int i=0; i < source.getPaths().size(); i++) {
			ProjectionRule rule=ProjectionRuleFactory.getProjectionRule(source.getPaths().get(i));
			
			if (rule != null) {
				LBlock block=(LBlock)rule.project(context, source.getPaths().get(i), role, logger);

				if (block != null) {
					if (block.getContents().size() == 1
							&& block.getContents().get(0) instanceof LChoice
							&& isSameRole(projected, (LChoice)block.getContents().get(0))) {
						projected.getPaths().addAll(((LChoice)block.getContents().get(0)).getPaths());
					} else {
						projected.getPaths().add(block);
					}
				} else {
					f_missingBranch = true;
				}
			}
        }
        
        if (f_missingBranch) {
        	
        	if (projected.getPaths().size() > 0) {
        		// TODO: REPORT ERROR - not projected to all paths
        	} else {
        		return null;
        	}	
        }
        
        return (ChoiceMergingUtil.merge(projected, role, logger));
	}
	
    /**
	 * Check whether roles are the same.
	 *
	 * @param c1 First choice
	 * @param c2 Second choice
	 * @return Whether roles are same
	 */
    protected boolean isSameRole(LChoice c1, LChoice c2) {
        if (c1.getRole() == null && c2.getRole() == null) {
            return (true);
        } else if (c1.getRole() == null || c2.getRole() == null) {
            return (false);
        } else {
            return (c1.getRole().equals(c2.getRole()));
        }
    }
}
