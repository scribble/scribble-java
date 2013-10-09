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
import org.scribble.protocol.model.global.GProtocolDefinition;
import org.scribble.protocol.model.global.GProtocolInstance;
import org.scribble.protocol.validation.ValidationContext;
import org.scribble.protocol.validation.ValidationLogger;
import org.scribble.protocol.validation.ValidationMessages;

/**
 * This class implements the validation rule for the GProtocolInstance
 * component.
 *
 */
public class GProtocolInstanceValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ValidationContext context, ModelObject mobj, ValidationLogger logger) {
		GProtocolInstance elem=(GProtocolInstance)mobj;
		
		if (elem.getMemberName() != null) {
			ModelObject refd=context.getFullyQualifiedMember(elem.getMemberName());
			
			if (refd == null) {
				logger.error(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_MEMBER_NAME"),
						elem.getMemberName()), elem);
			} else if (!(refd instanceof GProtocolDefinition)) {
				logger.error(MessageFormat.format(ValidationMessages.getMessage("MEMBER_NOT_PROTOCOL_DEFINITION"),
						elem.getMemberName()), elem);
			}
		}
	}

}
