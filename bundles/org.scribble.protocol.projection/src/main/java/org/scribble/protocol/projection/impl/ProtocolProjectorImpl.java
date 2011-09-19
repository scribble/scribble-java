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

import java.text.MessageFormat;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.ProtocolTools;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.projection.ProtocolProjector;

/**
 * This class provides an implementation of the protocol projector.
 *
 */
public class ProtocolProjectorImpl implements ProtocolProjector {

    /**
     * This method projects a 'global' protocol model to a specified
     * role's 'local' protocol model.
     * 
     * @param context The protocol context
     * @param model The 'global' protocol model
     * @param role The role to project
     * @param journal Journal for reporting issues
     * @return The 'local' protocol model
     */
    public ProtocolModel project(ProtocolTools context, ProtocolModel model,
                        Role role, Journal journal) {
        ProtocolModel ret=null;
        
        if (model == null || role == null) {
            throw new IllegalArgumentException("Model and/or role has not bee specified");
        }
        
        // Check that the supplied role has been defined within the model
        // being projected
        java.util.List<Role> roles=model.getRoles();
        int index=roles.indexOf(role);
        
        if (index == -1) {
            throw new IllegalArgumentException("Role '"+role.getName()
                    +"' is not defined within the protocol model");
        } else {
            // Obtain the role instance actually defined within the model,
            // as this can be used to locate the appropriate scope to be
            // projected
            role = roles.get(index);
        }
        
        // Check that role is defined within a role list, and its parent
        // link has not inadvertantly been reset
        /* GPB: TO INVESTIGATE
        if ((role.getParent() instanceof Introduces) == false) {
            throw new IllegalArgumentException("Role is not contained within a role list, " +
                    "and is therefore not the declared role");
        }
        */
        
        DefaultProjectorContext projectorContext=new DefaultProjectorContext(context);
        
        Object obj=projectorContext.project(model, role, journal);
        
        if (obj != null) {
            if (obj instanceof ProtocolModel) {
                ret = (ProtocolModel)obj;
            } else {
                String modelName=model.getProtocol().getName();
                
                if (model.getProtocol().getLocatedRole() != null) {
                    modelName += ","+model.getProtocol().getLocatedRole();
                }
                    
                journal.error(MessageFormat.format(
                        java.util.PropertyResourceBundle.getBundle("org.scribble.protocol.projection.Messages").getString(
                        "_NOT_PROJECTED_MODEL"), modelName), null);
            }
        }
        
        return (ret);
    }

}
