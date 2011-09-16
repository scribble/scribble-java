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
import org.scribble.protocol.model.Parallel;
import org.scribble.protocol.model.Role;

/**
 * This class provides the Parallel implementation of the
 * projector rule.
 */
public class ParallelProjectorRule implements ProjectorRule {

    /**
     * This method determines whether the projection rule is
     * appropriate for the supplied model object.
     * 
     * @param obj The model object to be projected
     * @return Whether the rule is relevant for the
     *                 model object
     */
    public boolean isSupported(ModelObject obj) {
        return (obj.getClass() == Parallel.class);
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
        Object ret=null;
        Parallel parallel=new Parallel();
        Parallel source=(Parallel)model;
        
        parallel.derivedFrom(source);

        for (int i=0; i < source.getPaths().size(); i++) {
            Block block=(Block)
                    context.project(source.getPaths().get(i),
                            role, l);
            
            if (block != null) {
                parallel.getPaths().add(block);
            }
        }
        
        // Check if parallel has atleast one path
        if (parallel.getPaths().size() > 0) {
            ret = parallel;
            
            // If parallel only has one path, then
            // return block
            if (parallel.getPaths().size() == 1) {
                ret = parallel.getPaths().get(0);
            }
        }
        
        return (ret);
    }
}
