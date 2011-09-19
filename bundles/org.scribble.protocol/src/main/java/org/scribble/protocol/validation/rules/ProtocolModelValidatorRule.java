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
package org.scribble.protocol.validation.rules;

import java.text.MessageFormat;

import org.scribble.common.logging.CachedJournal;
import org.scribble.common.logging.Journal;
import org.scribble.protocol.ProtocolTools;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.validation.ProtocolComponentValidatorRule;

/**
 * This class provides the validation rule for the ProtocolModel
 * model component.
 *
 */
public class ProtocolModelValidatorRule implements ProtocolComponentValidatorRule {

    /**
     * This method determines whether the rule is applicable
     * for the supplied model object.
     * 
     * @param obj The object to check
     * @return Whether the rule can be used to validate the
     *                 supplied model object
     */
    public boolean isSupported(ModelObject obj) {
        // Check model object is ProtocolModel and is global
        return (obj.getClass() == ProtocolModel.class
                && !((ProtocolModel)obj).isLocated());
    }
    
    /**
     * This method validates the supplied model object.
     * 
     * @param context The protocol context
     * @param obj The model object being validated
     * @param logger The logger
     */
    public void validate(ProtocolTools context, ModelObject obj,
                    Journal logger) {
        ProtocolModel elem=(ProtocolModel)obj;
        java.util.List<Role> unprojectable=new java.util.Vector<Role>();
        
        for (Role role : elem.getRoles()) {
            CachedJournal l=new CachedJournal();
            context.getProtocolProjector().project(context, elem, role, l);
            
            if (l.hasErrors()) {
                unprojectable.add(role);
            }
        }
        
        if (unprojectable.size() > 0) {
            String roleNames="";
            for (int i=0; i < unprojectable.size(); i++) {
                if (i > 0) {
                    if (i == unprojectable.size()-1) {
                        roleNames += " & ";
                    } else {
                        roleNames += ", ";
                    }
                }
                roleNames += unprojectable.get(i).getName();
            }
            
            logger.error(MessageFormat.format(
                    java.util.PropertyResourceBundle.getBundle(
                            "org.scribble.protocol.Messages").getString("_UNPROJECTABLE_ROLES"),
                            roleNames), obj.getProperties());
        }
    }
}
