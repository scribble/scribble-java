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

import org.scribble.protocol.model.ImportDecl;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Module;
import org.scribble.protocol.validation.ValidationContext;
import org.scribble.protocol.validation.ValidationLogger;
import org.scribble.protocol.validation.ValidationMessages;

/**
 * This class implements the validation rule for the ImportDecl
 * component.
 *
 */
public class ImportDeclValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ValidationContext context, ModelObject mobj, ValidationLogger logger) {
		ImportDecl elem=(ImportDecl)mobj;
		
		if (elem.getModuleName() != null) {
			
			// Load imported module
			Module importedModule=context.importModule(elem.getModuleName().getName());
			
			if (importedModule == null) {
				logger.error(MessageFormat.format(ValidationMessages.getMessage("NOT_FOUND_MODULE"),
						elem.getModuleName().getName()), elem);
			} else if (elem.getMemberName() != null) {
				
				// Check if member within module has been specified
				if (elem.getAlias() == null
						&& context.getMember(elem.getModuleName().getName(),
								elem.getMemberName()) == null) {					
					logger.error(MessageFormat.format(ValidationMessages.getMessage("NOT_FOUND_MEMBER"),
							elem.getMemberName(), elem.getModuleName().getName()), elem);
					
				} else if (context.getAlias(elem.getAlias()) != null) {
					logger.error(MessageFormat.format(ValidationMessages.getMessage("EXISTS_ALIAS"),
							elem.getAlias()), elem);
					
				} else if (context.registerAlias(elem.getModuleName().getName(),
						elem.getMemberName(), elem.getAlias()) == null) {					
					logger.error(MessageFormat.format(ValidationMessages.getMessage("NOT_FOUND_MEMBER"),
							elem.getMemberName(), elem.getModuleName().getName()), elem);
				}
				
			}
		} else {
			logger.error(ValidationMessages.getMessage("NO_MODULE"), elem);
		}
	}

}
