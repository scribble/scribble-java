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

import org.scribble.context.ModuleContext;
import org.scribble.logging.IssueLogger;
import org.scribble.model.ModelObject;
import org.scribble.model.local.LRecursion;

/**
 * This class implements the validation rule for the LRecursion
 * component.
 *
 */
public class LRecursionValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ModuleContext context, ModelObject mobj, IssueLogger logger) {
		LRecursion elem=(LRecursion)mobj;
		
		if (elem.getBlock() != null) {
			ValidationRule rule=ValidationRuleFactory.getValidationRule(elem.getBlock());
			
			if (rule != null) {
				rule.validate(context, elem.getBlock(), logger);
			}
		}
	}

}
