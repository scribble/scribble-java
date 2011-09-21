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
import org.scribble.protocol.model.Do;
import org.scribble.protocol.model.Interrupt;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Role;

/**
 * This class provides the Do implementation of the
 * projector rule.
 */
public class DoProjectorRule implements ProjectorRule {

    /**
     * This method determines whether the projection rule is
     * appropriate for the supplied model object.
     * 
     * @param obj The model object to be projected
     * @return Whether the rule is relevant for the
     *                 model object
     */
    public boolean isSupported(ModelObject obj) {
        return (obj.getClass() == Do.class);
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
    public Object project(ProtocolProjectorContext context, ModelObject model,
                    Role role, Journal l) {
        Do ret=new Do();
        Do source=(Do)model;

        ret.derivedFrom(source);
        
        ret.setBlock((Block)context.project(source.getBlock(), role, l));

        for (int i=0; i < source.getInterrupts().size(); i++) {
            Interrupt c=(Interrupt)
                    context.project(source.getInterrupts().get(i),
                            role, l);
            
            if (c != null) {
                ret.getInterrupts().add(c);
            }
        }
        
        // Shouldn't project try/escape if main block has no activities
        if (ret.getBlock().getContents().size() == 0) {
            ret = null;
        }
        
        return (ret);
    }
}
