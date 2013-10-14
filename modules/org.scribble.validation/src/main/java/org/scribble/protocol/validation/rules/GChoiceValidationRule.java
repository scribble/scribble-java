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
import org.scribble.protocol.model.global.GBlock;
import org.scribble.protocol.model.global.GChoice;
import org.scribble.protocol.model.global.GProtocolDefinition;
import org.scribble.protocol.validation.ValidationContext;
import org.scribble.protocol.validation.ValidationLogger;
import org.scribble.protocol.validation.ValidationMessages;

/**
 * This class implements the validation rule for the GChoice
 * component.
 *
 */
public class GChoiceValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ValidationContext context, ModelObject mobj, ValidationLogger logger) {
		GChoice elem=(GChoice)mobj;
		
		// Check if decision role has been declared
		if (elem.getRole() != null) {
			GProtocolDefinition gpd=elem.getParent(GProtocolDefinition.class);
			
			if (gpd.getRoleDeclaration(elem.getRole().getName()) == null) {
				logger.error(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"),
						elem.getRole().getName()), elem.getRole());				
			}
		} else {
			logger.error(ValidationMessages.getMessage("UNDEFINED_ROLE"), elem);				
		}
		
		// TODO: Should the number of choice paths be validated?
		
		// TODO: Need to check each path to ensure that the 'decision is communicated
		// to each receiving role'
		
		for (GBlock subelem : elem.getPaths()) {
			ValidationRule rule=ValidationRuleFactory.getValidationRule(subelem);
			
			if (rule != null) {
				rule.validate(context, subelem, logger);
			}
		}
	}

}
