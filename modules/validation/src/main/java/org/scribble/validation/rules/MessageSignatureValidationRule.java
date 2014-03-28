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
import org.scribble.model.MessageSignature;
import org.scribble.model.ModelObject;
import org.scribble.model.ParameterDecl;
import org.scribble.model.PayloadElement;
import org.scribble.model.PayloadTypeDecl;
import org.scribble.model.ProtocolDecl;
import org.scribble.validation.ValidationMessages;

/**
 * This class implements the validation rule for the MessageSignature
 * component.
 *
 */
public class MessageSignatureValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ModuleContext context, ModelObject mobj, IssueLogger logger) {
		MessageSignature elem=(MessageSignature)mobj;
		
		ProtocolDecl gpd=elem.getParent(ProtocolDecl.class);
		
		for (PayloadElement pt : elem.getPayloadElements()) {
			
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
				
				if (!f_found && context != null) {
					ModelObject alias=context.getMember(pt.getName());
					
					if (alias instanceof PayloadTypeDecl) {
						f_found = true;
					}
				}
				
				if (!f_found) {
					logger.error(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_PAYLOAD_ELEMENT"),
							pt.getName()), elem);
				}
			}
		}
	}

}
