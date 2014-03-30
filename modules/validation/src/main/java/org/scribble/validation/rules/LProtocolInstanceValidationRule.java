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
import org.scribble.model.Argument;
import org.scribble.model.ModelObject;
import org.scribble.model.Module;
import org.scribble.model.PayloadTypeDecl;
import org.scribble.model.RoleInstantiation;
import org.scribble.model.local.LProtocolDefinition;
import org.scribble.model.local.LProtocolInstance;

/**
 * This class implements the validation rule for the GProtocolInstance
 * component.
 *
 */
public class LProtocolInstanceValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ModuleContext context, ModelObject mobj, IssueLogger logger) {
		LProtocolInstance elem=(LProtocolInstance)mobj;
		
		if (elem.getLocalRole() != null) {
			// Check that local role is explicitly listed in the role declaration list
			
			if (elem.getRoleDeclaration(elem.getLocalRole().getName()) == null) {
				logger.error(MessageFormat.format(ValidationMessages.getMessage("LOCAL_ROLE_NOT_DECLARED"),
						elem.getLocalRole().getName()), elem);
			}
		}
		
		if (elem.getMemberName() != null) {
			ModelObject refd=context.getMember(elem.getMemberName());
			
			if (refd == null) {
				logger.error(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_MEMBER_NAME"),
						elem.getMemberName()), elem);
			} else if (!(refd instanceof LProtocolDefinition)) {
				logger.error(MessageFormat.format(ValidationMessages.getMessage("MEMBER_NOT_PROTOCOL_DEFINITION"),
						elem.getMemberName()), elem);
			} else {
				LProtocolDefinition pd=(LProtocolDefinition)refd;
				
				// Verify argument list
				if (pd.getParameterDeclarations().size() != elem.getArguments().size()) {
					logger.error(MessageFormat.format(ValidationMessages.getMessage("ARG_NUM_MISMATCH"),
							elem.getArguments().size(), pd.getParameterDeclarations().size()), elem);
				}
				
				for (Argument arg : elem.getArguments()) {
					if (arg.getName() != null) {
						// Check if argument name has been declared on the protocol instance as
						// a parameter, or it refers to a payload type name
						Module m=elem.getModule();
						PayloadTypeDecl ptd=null;
						
						if (m != null) {
							ptd = m.getTypeDeclaration(arg.getName());
						}
						
						if (ptd == null && elem.getParameterDeclaration(arg.getName()) == null) {
							logger.error(MessageFormat.format(ValidationMessages.getMessage("ARG_NOT_DECLARED"),
									arg.getName()), elem);
						}
					}
					if (arg.getAlias() != null) {
						// Check that target protocol declaration has a parameter name matching
						// the alias
						if (pd.getParameterDeclaration(arg.getAlias()) == null) {
							logger.error(MessageFormat.format(ValidationMessages.getMessage("ARG_ALIAS_NOT_DECLARED"),
									arg.getAlias()), elem);
						}
					}
				}

				// Verify role instantiations list
				
				// Check number of role instantiations matches the number of roles declared in the
				// target protocol declaration
				if (pd.getRoleDeclarations().size() != elem.getRoleInstantiations().size()) {
					logger.error(MessageFormat.format(ValidationMessages.getMessage("ROLE_NUM_MISMATCH"),
							elem.getRoleInstantiations().size(), pd.getRoleDeclarations().size()), elem);
				}
				
				// Check that the roles defined in the instantiation exist in the declaration
				for (RoleInstantiation ri : elem.getRoleInstantiations()) {
					if (ri.getName() != null) {
						// Check if instantiated role name has been declared on the protocol instance
						if (elem.getRoleDeclaration(ri.getName()) == null) {
							logger.error(MessageFormat.format(ValidationMessages.getMessage("ROLE_NOT_DECLARED"),
									ri.getName()), elem);
						}
					}
					if (ri.getAlias() != null) {
						if (pd.getRoleDeclaration(ri.getAlias()) == null) {
							logger.error(MessageFormat.format(ValidationMessages.getMessage("ROLE_ALIAS_NOT_DECLARED"),
									ri.getAlias()), elem);
						}
					}
				}
			}
		}
	}

}
