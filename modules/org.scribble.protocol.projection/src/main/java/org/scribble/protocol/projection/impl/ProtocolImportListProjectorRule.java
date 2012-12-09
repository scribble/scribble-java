/*
 * Copyright 2009-10 www.scribble.org
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
package org.scribble.protocol.projection.impl;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.ProtocolImport;
import org.scribble.protocol.model.ProtocolImportList;
import org.scribble.protocol.model.Role;

/**
 * This class provides the ImportList implementation of the
 * projector rule.
 */
public class ProtocolImportListProjectorRule implements ProjectorRule {

    /**
     * This method determines whether the projection rule is
     * appropriate for the supplied model object.
     * 
     * @param obj The model object to be projected
     * @return Whether the rule is relevant for the
     *                 model object
     */
    public boolean isSupported(ModelObject obj) {
        return (obj.getClass() == ProtocolImportList.class);
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
        ProtocolImportList ret=new ProtocolImportList();
        ProtocolImportList source=(ProtocolImportList)model;
        
        ret.derivedFrom(source);
        
        for (int i=0; i < source.getProtocolImports().size(); i++) {
            ProtocolImport p=(ProtocolImport)context.project(source.getProtocolImports().get(i), role, l);

            if (p != null) {
                ret.getProtocolImports().add(p);
            }
        }
        
        return (ret);
    }
}
