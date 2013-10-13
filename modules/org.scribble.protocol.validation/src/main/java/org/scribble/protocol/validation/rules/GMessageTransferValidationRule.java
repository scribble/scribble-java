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
import org.scribble.protocol.model.ParameterDecl;
import org.scribble.protocol.model.PayloadType;
import org.scribble.protocol.model.PayloadTypeDecl;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.model.global.GMessageTransfer;
import org.scribble.protocol.model.global.GProtocolDefinition;
import org.scribble.protocol.validation.ValidationContext;
import org.scribble.protocol.validation.ValidationLogger;
import org.scribble.protocol.validation.ValidationMessages;

/**
 * This class implements the validation rule for the GMessage
 * component.
 *
 */
public class GMessageTransferValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ValidationContext context, ModelObject mobj, ValidationLogger logger) {
		GMessageTransfer elem=(GMessageTransfer)mobj;
		
		if (elem.getMessage() != null) {
			GProtocolDefinition gpd=elem.getParent(GProtocolDefinition.class);
			
			if (elem.getMessage().getParameter() != null) {
				
				if (gpd != null) {
					boolean f_found=false;
					
					for (ParameterDecl pd : gpd.getParameterDeclarations()) {
						if (pd.getName().equals(elem.getMessage().getParameter()) ||
								pd.getAlias() != null && pd.getAlias().equals(elem.getMessage().getParameter())) {
							f_found = true;
							
							// Check if parameter is a signature
							if (pd.getType() != ParameterDecl.ParameterType.Sig) {
								logger.error(MessageFormat.format(ValidationMessages.getMessage("PARAMETER_NOT_SIG"),
										elem.getMessage().getParameter()), elem);
							}
							break;
						}
					}
					
					if (!f_found) {
						logger.error(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_PARAMETER"),
								elem.getMessage().getParameter()), elem);
					}
				}
			}
			
			if (elem.getMessage().getMessageSignature() != null) {			
				for (PayloadType pt : elem.getMessage().getMessageSignature().getTypes()) {
					
					// Check if 'name' represents a payload type or parameter name
					if (pt.getName() != null) {
						boolean f_found=false;
						
						// QUESTION: what takes precedence, the payload type, or parameter name,
						// if both are defined?
						
						if (gpd != null) {
							for (ParameterDecl pd : gpd.getParameterDeclarations()) {
								if (pd.getName().equals(pt.getName()) ||
										pd.getAlias() != null && pd.getAlias().equals(pt.getName())) {
									f_found = true;
									break;
								}
							}
						}
						
						if (!f_found) {
							PayloadTypeDecl ptype=elem.getModule().getTypeDeclaration(pt.getName());
							
							f_found = (ptype != null);
						}
						
						if (!f_found) {
							logger.error(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_PAYLOAD_ELEMENT"),
									pt.getName()), elem);
						}
					}
				}
			}
			
			// Validate roles
			if (elem.getFromRole() != null) {
				
				if (gpd.getRoleDeclaration(elem.getFromRole().getName()) == null) {
					logger.error(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"),
							elem.getFromRole().getName()), elem.getFromRole());				
				}
			}
			
			for (Role r : elem.getToRoles()) {
				
				if (gpd.getRoleDeclaration(r.getName()) == null) {
					logger.error(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"),
							r.getName()), r);				
				}
			}
		}
	}

}
