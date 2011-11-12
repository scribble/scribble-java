/*
 * Copyright 2009-11 www.scribble.org
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
import org.scribble.protocol.model.Introduces;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.util.RoleUtil;
import org.scribble.protocol.validation.ProtocolComponentValidatorRule;
import org.scribble.protocol.validation.ProtocolValidatorContext;

/**
 * This class provides the validation rule for the Introduces
 * model component.
 *
 */
public class IntroducesValidatorRule implements ProtocolComponentValidatorRule {

    /**
     * This method determines whether the rule is applicable
     * for the supplied model object.
     * 
     * @param obj The object to check
     * @return Whether the rule can be used to validate the
     *                 supplied model object
     */
    public boolean isSupported(ModelObject obj) {
        return (obj.getClass() == Introduces.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public void validate(ProtocolValidatorContext pvc, ModelObject obj,
                    Journal logger) {
        Introduces elem=(Introduces)obj;
        
        // Check whether introduced roles already exist
        java.util.Set<Role> inScope=RoleUtil.getRolesInScope(elem);
        
        for (Role r : elem.getIntroducedRoles()) {
            if (inScope.contains(r)) {
                logger.error(MessageFormat.format(
                        java.util.PropertyResourceBundle.getBundle(
                        "org.scribble.protocol.Messages").getString("_ROLE_ALREADY_DEFINED"),
                        r.getName()), r.getProperties());
            }
        }
    }
}
