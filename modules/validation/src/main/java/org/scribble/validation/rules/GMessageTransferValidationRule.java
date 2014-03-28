/*
 * Copyright 2009-11 www.scribble.org
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
package org.scribble.validation.rules;

import java.text.MessageFormat;

import org.scribble.context.ModuleContext;
import org.scribble.logging.IssueLogger;
import org.scribble.model.ModelObject;
import org.scribble.model.ProtocolDecl;
import org.scribble.model.Role;
import org.scribble.model.global.GMessageTransfer;
import org.scribble.validation.ValidationMessages;

/**
 * This class implements the validation rule for the GMessage
 * component.
 *
 */
public class GMessageTransferValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ModuleContext context, ModelObject mobj, IssueLogger logger) {
		GMessageTransfer elem=(GMessageTransfer)mobj;
		
		// Validate message
		if (elem.getMessage() != null) {
			ValidationRule rule=ValidationRuleFactory.getValidationRule(elem.getMessage());
			
			if (rule != null) {
				rule.validate(context, elem.getMessage(), logger);
			}
		}
		
		ProtocolDecl pd=elem.getParent(ProtocolDecl.class);
		
		if (pd != null) {
			// Validate roles
			if (elem.getFromRole() != null) {
				
				if (pd.getRoleDeclaration(elem.getFromRole().getName()) == null) {
					logger.error(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"),
							elem.getFromRole().getName()), elem.getFromRole());				
				}
			}
			
			java.util.List<String> toRoles=new java.util.ArrayList<String>();
			
			for (Role r : elem.getToRoles()) {
				if (toRoles.contains(r.getName())) {
					logger.error(MessageFormat.format(ValidationMessages.getMessage("ROLE_NOT_DISTINCT"),
							r.getName()), elem);
				} else {
					toRoles.add(r.getName());
				}
				
				if (pd.getRoleDeclaration(r.getName()) == null) {
					logger.error(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"),
							r.getName()), r);				
				}
			}
		}
	}

}
