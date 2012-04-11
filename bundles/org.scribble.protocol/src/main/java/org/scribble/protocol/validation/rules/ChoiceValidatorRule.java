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
import java.util.Collections;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.Block;
import org.scribble.protocol.model.Choice;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.util.InteractionUtil;
import org.scribble.protocol.util.RoleUtil;
import org.scribble.protocol.validation.ProtocolComponentValidatorRule;
import org.scribble.protocol.validation.ProtocolValidatorContext;

/**
 * This class provides the validation rule for the Choice
 * model component.
 *
 */
public class ChoiceValidatorRule implements ProtocolComponentValidatorRule {

    /**
     * This method determines whether the rule is applicable
     * for the supplied model object.
     * 
     * @param obj The object to check
     * @return Whether the rule can be used to validate the
     *                 supplied model object
     */
    public boolean isSupported(ModelObject obj) {
        return (obj.getClass() == org.scribble.protocol.model.Choice.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public void validate(ProtocolValidatorContext pvc, ModelObject obj,
                    Journal logger) {
        Choice elem=(Choice)obj;
        
        // Identify definition and whether it has a located role
        Role locatedRole=null;
        
        if (elem.getEnclosingProtocol() != null) {
            locatedRole = elem.getEnclosingProtocol().getLocatedRole();
        }

        if (elem.getRole() != null) {
            
            // Check that the role has been defined in scope
            java.util.Set<Role> roles=RoleUtil.getRolesInScope(elem);
            
            if (!roles.contains(elem.getRole())) {
                logger.error(MessageFormat.format(
                        java.util.PropertyResourceBundle.getBundle("org.scribble.protocol.Messages").getString("_UNKNOWN_ROLE"),
                        elem.getRole().getName()), obj.getProperties());
            }
        }

        if (locatedRole != null && elem.getRole() != null && !elem.getRole().equals(locatedRole)) {
            logger.error(MessageFormat.format(
                    java.util.PropertyResourceBundle.getBundle(
                            "org.scribble.protocol.Messages").getString("_UNRELATED_TO_LOCATED_ROLE"),
                            locatedRole.getName()), obj.getProperties());
        }
        
        checkForAmbiguity(elem, logger);
    }
    
    /**
     * This method checks for ambiguity in the choce paths.
     * 
     * @param choice The choice
     * @param l The journal
     */
    protected static void checkForAmbiguity(Choice choice, Journal l) {
        // Confirm each path has distinct initial interactions
        java.util.List<ModelObject> interactions=new java.util.Vector<ModelObject>();
        
        for (Block path : choice.getPaths()) {
            java.util.List<ModelObject> initial=InteractionUtil.getInitialInteractions(path);
        
            if (!Collections.disjoint(interactions, initial)) {
                l.error(MessageFormat.format(
                        java.util.PropertyResourceBundle.getBundle("org.scribble.protocol.Messages").
                                getString("_AMBIGUOUS_CHOICE"), (Object[])null), choice.getProperties());
            } else {
                interactions.addAll(initial);
            }
        }
    }
}
