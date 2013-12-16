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

import org.scribble.common.logging.ScribbleLogger;
import org.scribble.common.module.ModuleContext;
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
	public void validate(ModuleContext context, ModelObject mobj, ScribbleLogger logger) {
		GMessageTransfer elem=(GMessageTransfer)mobj;
		
		// Validate message
		if (elem.getMessage() != null) {
			MessageValidationRule mvr=new MessageValidationRule();
			mvr.validate(context, elem.getMessage(), logger);
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
			
			for (Role r : elem.getToRoles()) {
				
				if (pd.getRoleDeclaration(r.getName()) == null) {
					logger.error(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"),
							r.getName()), r);				
				}
			}
		}
	}

}
