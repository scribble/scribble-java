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

import org.scribble.common.logging.ScribbleLogger;
import org.scribble.common.module.ModuleContext;
import org.scribble.model.ImportDecl;
import org.scribble.model.ModelObject;
import org.scribble.model.Module;
import org.scribble.model.PayloadTypeDecl;
import org.scribble.model.ProtocolDecl;
import org.scribble.validation.ValidationMessages;

/**
 * This class implements the validation rule for the GMessage
 * component.
 *
 */
public class ModuleValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ModuleContext context, ModelObject mobj, ScribbleLogger logger) {
		Module elem=(Module)mobj;
		
		if (elem.getFullyQualifiedName() == null) {
			logger.error(ValidationMessages.getMessage("NO_FULLY_QUALIFIED_NAME"), mobj);
		}
		
		for (ImportDecl imp : elem.getImports()) {
			ValidationRule rule=ValidationRuleFactory.getValidationRule(imp);
			
			if (rule != null) {
				rule.validate(context, imp, logger);
			}
		}
		
		for (PayloadTypeDecl ptd : elem.getPayloadTypeDeclarations()) {
			ValidationRule rule=ValidationRuleFactory.getValidationRule(ptd);
			
			if (rule != null) {
				rule.validate(context, ptd, logger);
			}
		}

		for (ProtocolDecl protocol : elem.getProtocols()) {
			ValidationRule rule=ValidationRuleFactory.getValidationRule(protocol);
			
			if (rule != null) {
				rule.validate(context, protocol, logger);
			}
		}
	}

}
