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

import org.scribble.model.ModelObject;
import org.scribble.model.local.LBlock;
import org.scribble.model.local.LParallel;
import org.scribble.validation.ValidationContext;
import org.scribble.validation.ValidationLogger;

/**
 * This class implements the validation rule for the LParallel
 * component.
 *
 */
public class LParallelValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ValidationContext context, ModelObject mobj, ValidationLogger logger) {
		LParallel elem=(LParallel)mobj;
		
		for (LBlock subelem : elem.getPaths()) {
			ValidationRule rule=ValidationRuleFactory.getValidationRule(subelem);
			
			if (rule != null) {
				rule.validate(context, subelem, logger);
			}
		}
	}

}
