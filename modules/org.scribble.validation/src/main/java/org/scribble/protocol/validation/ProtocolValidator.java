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
package org.scribble.protocol.validation;

import org.scribble.protocol.model.Module;
import org.scribble.protocol.validation.rules.ValidationRule;
import org.scribble.protocol.validation.rules.ValidationRuleFactory;

/**
 * This class is responsible for validating a protocol module.
 *
 */
public class ProtocolValidator {

	/**
	 * This method validates the supplied module, reporting
	 * any issues to the logger.
	 * 
	 * @param context The validation context
	 * @param module The module
	 * @param logger The logger
	 */
	public void validate(ValidationContext context, Module module, ValidationLogger logger) {
		
		ValidationRule rule=ValidationRuleFactory.getValidationRule(module);
		
		if (rule != null) {
			rule.validate(context, module, logger);
		}
	}
}
