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
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Repeat;
import org.scribble.protocol.model.Role;

/**
 * This class provides the Repeat implementation of the
 * projector rule.
 */
public class RepeatProjectorRule implements ProjectorRule {

    /**
     * This method determines whether the projection rule is
     * appropriate for the supplied model object.
     * 
     * @param obj The model object to be projected
     * @return Whether the rule is relevant for the
     *                 model object
     */
    public boolean isSupported(ModelObject obj) {
        return (obj.getClass() == Repeat.class);
    }
    
    /**
     * This method projects the supplied model object based on the
     * specified role.
     * 
     * @param context The context
     * @param model The model object
     * @param role The role
     * @param l The model listener
     * @return The projected model object
     */
    public Object project(ProjectorContext context, ModelObject model,
                    Role role, Journal l) {
        Repeat ret=new Repeat();
        Repeat source=(Repeat)model;

        ret.derivedFrom(source);
        
        // Project the list of roles
        for (int i=0; i < source.getRoles().size(); i++) {    
            // Possibly don't project roles that are not relevant to the
            // current projected role
            
            //if (source.getRoles().get(i).getName().equals(role.getName()) == false) {
                ret.getRoles().add(new Role(source.getRoles().get(i)));
            //}
        }
        
        if (ret != null && source.getBlock() != null) {
            
            // Project the block
            Block b=(Block)context.project(source.getBlock(), role, l);
            
            if (b != null) {
                ret.setBlock(b);
                ret.getBlock().setParent(ret);
            } else {
                ret = null;
            }
        }
        
        return (ret);
    }
}
