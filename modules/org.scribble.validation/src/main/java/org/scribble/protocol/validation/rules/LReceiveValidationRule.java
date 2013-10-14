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
package org.scribble.protocol.validation.rules;

import java.text.MessageFormat;

import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.ProtocolDecl;
import org.scribble.protocol.model.local.LReceive;
import org.scribble.protocol.validation.ValidationContext;
import org.scribble.protocol.validation.ValidationLogger;
import org.scribble.protocol.validation.ValidationMessages;

/**
 * This class implements the validation rule for the LSend
 * component.
 *
 */
public class LReceiveValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ValidationContext context, ModelObject mobj, ValidationLogger logger) {
		LReceive elem=(LReceive)mobj;
		
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
		}
	}

}
