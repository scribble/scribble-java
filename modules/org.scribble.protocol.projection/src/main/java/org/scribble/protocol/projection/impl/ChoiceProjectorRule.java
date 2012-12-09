/*
 * Copyright 2009-10 www.scribble.org
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
package org.scribble.protocol.projection.impl;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.Block;
import org.scribble.protocol.model.Choice;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.projection.util.ChoiceMergingUtil;

/**
 * This class provides the Choice implementation of the
 * projector rule.
 */
public class ChoiceProjectorRule implements ProjectorRule {

    /**
     * System property: should choice constructs be permitted to have empty paths
     * and therefore be optional?
     */
    public static final String ALLOW_OPTIONAL = "scribble.choice.allowOptional";
    
    /**
     * This method determines whether the projection rule is
     * appropriate for the supplied model object.
     * 
     * @param obj The model object to be projected
     * @return Whether the rule is relevant for the
     *                 model object
     */
    public boolean isSupported(ModelObject obj) {
        return (obj.getClass() == Choice.class);
    }
    
    /**
     * This method projects the supplied model object based on the
     * specified role.
     * 
     * @param context The projector context
     * @param model The model object
     * @param role The role
     * @param l The model listener
     * @return The projected model object
     */
    public Object project(ProtocolProjectorContext context, ModelObject model,
                    Role role, Journal l) {
        Choice projected=new Choice();
        Choice source=(Choice)model;
        boolean optional=false;
        
        projected.derivedFrom(source);
        
        // Only project role if the same as the located role. Otherwise description
        // should just indicate that the decision is being made at another role
        if (source.getRole() != null && source.getRole().equals(role)) {
            projected.setRole(new Role(source.getRole()));
        }
        
        for (int i=0; i < source.getPaths().size(); i++) {
            Block block=(Block)
                    context.project(source.getPaths().get(i), role,
                            l);
            
            if (block != null) {
               if (block.getContents().size() == 1
                        && block.getContents().get(0) instanceof Choice
                        && isSameRole(projected, (Choice)block.getContents().get(0))) {
                    projected.getPaths().addAll(((Choice)block.getContents().get(0)).getPaths());
                } else {
                    projected.getPaths().add(block);
                }
            } else {
                optional = true;
            }
        }
        
        return (ChoiceMergingUtil.merge(projected, role, l, optional));
    }
    
    /**
     * Check whether roles are the same.
     * 
     * @param c1 First choice
     * @param c2 Second choice
     * @return Whether roles are same
     */
    protected boolean isSameRole(Choice c1, Choice c2) {
        if (c1.getRole() == null && c2.getRole() == null) {
            return (true);
        } else if (c1.getRole() == null || c2.getRole() == null) {
            return (false);
        } else {
            return (c1.getRole().equals(c2.getRole()));
        }
    }
}
