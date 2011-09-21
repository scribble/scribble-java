/*
 * Copyright 2009 www.scribble.org
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

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.Interaction;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.util.RoleUtil;
import org.scribble.protocol.validation.ProtocolComponentValidatorRule;
import org.scribble.protocol.validation.ProtocolValidatorContext;

/**
 * This class provides the validation rule for the Interaction
 * model component.
 *
 */
public class InteractionValidatorRule implements ProtocolComponentValidatorRule {

    /**
     * This method determines whether the rule is applicable
     * for the supplied model object.
     * 
     * @param obj The object to check
     * @return Whether the rule can be used to validate the
     *                 supplied model object
     */
    public boolean isSupported(ModelObject obj) {
        return (obj.getClass() == org.scribble.protocol.model.Interaction.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public void validate(ProtocolValidatorContext pvc, ModelObject obj,
                    Journal logger) {
        Interaction elem=(Interaction)obj;
        
        // Identify definition and whether it has a located role
        Role locatedRole=null;
        boolean locatedRoleUsed=false;
        
        if (elem.getEnclosingProtocol() != null) {
            locatedRole = elem.getEnclosingProtocol().getLocatedRole();
        }

        // Check there are 'to' and 'from' roles defined
        if (elem.getFromRole() == null) {
            
            // Check if local model and 'to' is not the same as the
            // located role
            if (locatedRole == null || elem.getToRoles().size() == 0
                    || elem.getToRoles().contains(locatedRole)) {
            
                logger.error(MessageFormat.format(
                        java.util.PropertyResourceBundle.getBundle(
                            "org.scribble.protocol.Messages").getString(
                            "_INTERACTION_ROLE"), "from"), elem.getProperties());
            }
            
            if (locatedRole != null) {
                locatedRoleUsed = true;
            }
        } else {
            // Check that the role has been defined in scope
            java.util.Set<Role> roles=RoleUtil.getRolesInScope(elem);
            
            if (!roles.contains(elem.getFromRole())) {
                logger.error(MessageFormat.format(
                        java.util.PropertyResourceBundle.getBundle(
                                "org.scribble.protocol.Messages").getString(
                                "_UNKNOWN_ROLE"),
                                    elem.getFromRole().getName()),
                                    elem.getFromRole().getProperties());
            }
            
            // Check if 'from' role was the located role
            if (locatedRole != null && elem.getFromRole().equals(locatedRole)) {
                locatedRoleUsed = true;
            }
        }

        if (elem.getToRoles().size() == 0) {
            
            // Check if local model and 'from' is not the same as the
            // located role
            if (locatedRole == null || elem.getFromRole() == null
                    || locatedRole.equals(elem.getFromRole())) {
            
                logger.error(MessageFormat.format(
                        java.util.PropertyResourceBundle.getBundle(
                            "org.scribble.protocol.Messages").getString(
                            "_INTERACTION_ROLE"), "to"), elem.getProperties());
            }
            
            if (locatedRole != null) {
                locatedRoleUsed = true;
            }
        } else {
            // Check that the role has been defined in scope
            java.util.Set<Role> roles=RoleUtil.getRolesInScope(elem);
            
            for (Role r : elem.getToRoles()) {
                if (!roles.contains(r)) {
                    logger.error(MessageFormat.format(
                            java.util.PropertyResourceBundle.getBundle(
                                "org.scribble.protocol.Messages").getString(
                                "_UNKNOWN_ROLE"), r.getName()), r.getProperties());
                }
            }
            
            // Check if 'to' role was the located role
            if (locatedRole != null && elem.getToRoles().contains(locatedRole)) {
                locatedRoleUsed = true;
                
                // TODO: Should the located role be the only role here?
            }
        }
        
        if (locatedRole != null && !locatedRoleUsed) {
            logger.error(MessageFormat.format(
                    java.util.PropertyResourceBundle.getBundle(
                            "org.scribble.protocol.Messages").getString("_UNRELATED_TO_LOCATED_ROLE"),
                            locatedRole.getName()), elem.getProperties());
        }
    }
}
