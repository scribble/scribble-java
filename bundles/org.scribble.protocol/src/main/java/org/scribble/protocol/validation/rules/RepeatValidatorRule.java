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

import org.scribble.common.logging.Journal;
import org.scribble.protocol.ProtocolTools;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Repeat;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.util.RoleUtil;
import org.scribble.protocol.validation.ProtocolComponentValidatorRule;

/**
 * This class provides the validation rule for the Repeat
 * model component.
 *
 */
public class RepeatValidatorRule implements ProtocolComponentValidatorRule {

    /**
     * This method determines whether the rule is applicable
     * for the supplied model object.
     * 
     * @param obj The object to check
     * @return Whether the rule can be used to validate the
     *                 supplied model object
     */
    public boolean isSupported(ModelObject obj) {
        return (obj.getClass() == org.scribble.protocol.model.Repeat.class);
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
        Repeat elem=(Repeat)obj;
        
        // Check that the role has been defined in scope
        java.util.Set<Role> roles=RoleUtil.getRolesInScope(elem);
        
        for (Role role : elem.getRoles()) {
            if (!roles.contains(role)) {
                logger.error(MessageFormat.format(
                        java.util.PropertyResourceBundle.getBundle("org.scribble.protocol.Messages").getString("_UNKNOWN_ROLE"),
                        role.getName()), obj.getProperties());
            }
        }
    }
}
