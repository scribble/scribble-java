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
import org.scribble.model.global.GContinue;
import org.scribble.model.global.GInterruptible;
import org.scribble.model.global.GParallel;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.model.global.GRecursion;

/**
 * This class implements the validation rule for the GContinue
 * component.
 *
 */
public class GContinueValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ModuleContext context, ModelObject mobj, IssueLogger logger) {
		GContinue elem=(GContinue)mobj;
		
		if (elem.getLabel() == null) {
			logger.error(ValidationMessages.getMessage("LABEL_NOT_DEFINED"), elem);
			return;
		}
		
		ModelObject act=elem;
		
		do {
			act = act.getParent();
			
			if (act instanceof GProtocolDefinition || act == null) {
				// Continue label not bound
				logger.error(MessageFormat.format(ValidationMessages.getMessage("LABEL_NOT_BOUND"),
						elem.getLabel()), elem);				
				
				break;
				
			} else if (act instanceof GParallel) {
				// Continue cannot be contained within a parallel
				logger.error(ValidationMessages.getMessage("LABEL_CONTAINED_IN_PARALLEL"), elem);
				
				break;
				
			} else if (act instanceof GInterruptible) {
				// Continue cannot be contained within an interruptible
				logger.error(ValidationMessages.getMessage("LABEL_CONTAINED_IN_INTERRUPTIBLE"), elem);
				
				break;
			} else if (act instanceof GRecursion &&
					elem.getLabel().equals(((GRecursion)act).getLabel())) {
				break;
			}
		} while (true);		
	}

}
