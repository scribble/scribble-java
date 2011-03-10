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
import org.scribble.protocol.model.Choice;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.util.RoleUtil;
import org.scribble.protocol.validation.ProtocolComponentValidatorRule;

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
	 * @return Whether the rule can be used to validate the
	 * 				supplied model object
	 */
	public boolean isSupported(ModelObject obj) {
		return(obj.getClass() == org.scribble.protocol.model.Choice.class);
	}
	
	/**
	 * This method validates the supplied model object.
	 * 
	 * @param obj The model object being validated
	 * @param logger The logger
	 */
	public void validate(ModelObject obj,
					Journal logger) {
		Choice elem=(Choice)obj;
		
		// Identify definition and whether it has a located role
		Role locatedRole=null;
		boolean locatedRoleUsed=false;
		
		if (elem.enclosingProtocol() != null) {
			locatedRole = elem.enclosingProtocol().getRole();
		}

		// Check there are 'to' and 'from' roles defined
		if (elem.getFromRole() == null) {
			
			// Check if local model and 'to' is not the same as the
			// located role
			if (locatedRole == null || elem.getToRole() == null ||
					locatedRole.equals(elem.getToRole())) {
			
				logger.error(MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
							"org.scribble.protocol.Messages").getString(
							"_CHOICE_ROLE"), "from"), obj.getProperties());
			}
			
			if (locatedRole != null) {
				locatedRoleUsed = true;
			}
		} else {
			// Check that the role has been defined in scope
			java.util.Set<Role> roles=RoleUtil.getRolesInScope(elem);
			
			if (roles.contains(elem.getFromRole()) == false) {
				logger.error(MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle("org.scribble.protocol.Messages").getString("_UNKNOWN_ROLE"),
						elem.getFromRole().getName()), obj.getProperties());
			}
			
			// Check if 'from' role was the located role
			if (locatedRole != null && elem.getFromRole().equals(locatedRole)) {
				locatedRoleUsed = true;
			}
		}

		if (elem.getToRole() == null) {
			
			// Check if local model and 'from' is not the same as the
			// located role
			if (locatedRole == null || elem.getFromRole() == null ||
					locatedRole.equals(elem.getFromRole())) {
			
				logger.error(MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
							"org.scribble.protocol.Messages").getString(
							"_CHOICE_ROLE"), "to"), obj.getProperties());
			}
			
			if (locatedRole != null) {
				locatedRoleUsed = true;
			}
		} else {
			// Check that the role has been defined in scope
			java.util.Set<Role> roles=RoleUtil.getRolesInScope(elem);
			
			if (roles.contains(elem.getToRole()) == false) {
				logger.error(MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
							"org.scribble.protocol.Messages").getString(
							"_UNKNOWN_ROLE"), elem.getToRole().getName()), obj.getProperties());
			}
			
			// Check if 'to' role was the located role
			if (locatedRole != null && elem.getToRole().equals(locatedRole)) {
				locatedRoleUsed = true;
			}
		}
		
		if (locatedRole != null && !locatedRoleUsed) {
			logger.error(MessageFormat.format(
					java.util.PropertyResourceBundle.getBundle(
							"org.scribble.protocol.Messages").getString("_UNRELATED_TO_LOCATED_ROLE"),
							locatedRole.getName()), obj.getProperties());
		}
	}
}
