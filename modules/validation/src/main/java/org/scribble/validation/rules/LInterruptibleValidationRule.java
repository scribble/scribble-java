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
import org.scribble.model.Message;
import org.scribble.model.ModelObject;
import org.scribble.model.ProtocolDecl;
import org.scribble.model.Role;
import org.scribble.model.local.LInterruptible;
import org.scribble.model.local.LInterruptible.Catch;

/**
 * This class implements the validation rule for the LInterruptible
 * component.
 *
 */
public class LInterruptibleValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ModuleContext context, ModelObject mobj, IssueLogger logger) {
		LInterruptible elem=(LInterruptible)mobj;
		
		ProtocolDecl pd=elem.getParent(ProtocolDecl.class);
		
		MessageValidationRule mvr=new MessageValidationRule();

		if (elem.getThrows() != null) {
			
			// Validate messages
			for (Message m : elem.getThrows().getMessages()) {
				mvr.validate(context, m, logger);
			}
			
			// Validate roles
			if (pd != null) {
				for (Role r : elem.getThrows().getToRoles()) {
					if (pd.getRoleDeclaration(r.getName()) == null) {
						logger.error(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"),
								r.getName()), r);
					}
				}
			}
		}
		
		for (Catch cat : elem.getCatches()) {
			
			// Validate messages
			for (Message m : cat.getMessages()) {
				mvr.validate(context, m, logger);
			}
			
			// Validate role
			if (pd != null && cat.getRole() != null
					&& pd.getRoleDeclaration(cat.getRole().getName()) == null) {
				logger.error(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"),
						cat.getRole().getName()), cat.getRole());				
			}
		}
		
		if (elem.getBlock() != null) {
			ValidationRule rule=ValidationRuleFactory.getValidationRule(elem.getBlock());
			
			if (rule != null) {
				rule.validate(context, elem.getBlock(), logger);
			}
		}
	}

}
