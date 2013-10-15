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
import org.scribble.model.Message;
import org.scribble.model.ModelObject;
import org.scribble.model.ProtocolDecl;
import org.scribble.model.global.GInterruptible;
import org.scribble.model.global.GInterruptible.Interrupt;
import org.scribble.validation.ValidationLogger;
import org.scribble.validation.ValidationMessages;

/**
 * This class implements the validation rule for the GInterruptible
 * component.
 *
 */
public class GInterruptibleValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ModuleContext context, ModelObject mobj, ValidationLogger logger) {
		GInterruptible elem=(GInterruptible)mobj;
		
		ProtocolDecl pd=elem.getParent(ProtocolDecl.class);
		
		for (Interrupt in : elem.getInterrupts()) {
			
			// Validate messages
			MessageValidationRule mvr=new MessageValidationRule();

			for (Message m : in.getMessages()) {
				mvr.validate(context, m, logger);
			}
			
			// Validate role
			if (pd != null && in.getRole() != null
					&& pd.getRoleDeclaration(in.getRole().getName()) == null) {
				logger.error(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"),
						in.getRole().getName()), in.getRole());				
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
