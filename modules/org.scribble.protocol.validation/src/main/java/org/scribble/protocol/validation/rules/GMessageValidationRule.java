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

import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.PayloadType;
import org.scribble.protocol.model.global.GMessage;
import org.scribble.protocol.validation.ValidationContext;
import org.scribble.protocol.validation.ValidationLogger;

/**
 * This class implements the validation rule for the GMessage
 * component.
 *
 */
public class GMessageValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ValidationContext context, ModelObject mobj, ValidationLogger logger) {
		GMessage elem=(GMessage)mobj;
		
		if (elem.getMessage() != null && elem.getMessage().getMessageSignature() != null) {
			
			for (PayloadType pt : elem.getMessage().getMessageSignature().getTypes()) {
				
				// Check if 'name' represents a payload type or parameter name
				if (pt.getName() != null) {
				}
			}
		}
	}

}
