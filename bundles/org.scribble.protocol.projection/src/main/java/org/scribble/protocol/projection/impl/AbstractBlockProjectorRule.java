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

import java.util.logging.Logger;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.Activity;
import org.scribble.protocol.model.Block;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.util.ActivityUtil;

/**
 * This class provides the Block implementation of the
 * projector rule.
 */
public abstract class AbstractBlockProjectorRule implements ProjectorRule {

    private static final Logger LOG=Logger.getLogger(AbstractBlockProjectorRule.class.getName());
    
    /**
     * This method creates a new block of the appropriate
     * type.
     * 
     * @return The block
     */
    protected abstract Block createBlock();
    
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
    public ModelObject project(ProtocolProjectorContext context, ModelObject model,
                    Role role, Journal l) {
        Block ret=createBlock();
        Block source=(Block)model;
        
        ret.derivedFrom(source);
        
        context.pushState();
        
        for (int i=0; i < source.getContents().size(); i++) {
            Object act=context.project(source.getContents().get(i), role, l);
            
            if (act instanceof Activity) {        
                ret.getContents().add((Activity)act);
            } else if (act instanceof java.util.List) {
                
                for (Object a2 : (java.util.List<?>)act) {
                    if (a2 instanceof Activity) {
                        ret.getContents().add((Activity)a2);        
                    } else {
                        LOG.severe("Unexpected element returns from block projection: "+a2);
                    }
                }
            }
        }

        context.popState();
        
        // Only return block if it contains atleast one behavioural activity
        if (isFilterOutEmptyContent()) {
            int behaviourCount=0;
            for (Activity act : ret.getContents()) {
                if (ActivityUtil.isBehaviour(act)) {
                    behaviourCount++;
                }
            }
            if (behaviourCount == 0) {
                ret = null;
            }
        }
        
        return (ret);
    }
    
    /**
     * This method determines whether a block with empty content should be
     * filtered out.
     * 
     * @return Whether an empty block should be filtered out
     */
    protected boolean isFilterOutEmptyContent() {
        return (true);
    }
}
