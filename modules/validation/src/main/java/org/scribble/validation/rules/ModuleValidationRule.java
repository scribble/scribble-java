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
import org.scribble.model.ImportDecl;
import org.scribble.model.ModelObject;
import org.scribble.model.Module;
import org.scribble.model.PayloadTypeDecl;
import org.scribble.model.ProtocolDecl;
import org.scribble.model.Role;
import org.scribble.model.local.LProtocolDecl;
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
	public void validate(ModuleContext context, ModelObject mobj, IssueLogger logger) {
		Module elem=(Module)mobj;
		
		if (elem.getName() == null) {
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

		boolean f_global=true;
		String localRole=null;
		
		if (context.getResource() != null) {
			int pos=context.getResource().getPath().indexOf('@');
			if (pos != -1) {
				f_global = false;
				localRole = context.getResource().getPath().substring(pos+1,
								context.getResource().getPath().length()-4);
			}
		}
		
		for (int i=0; i < elem.getProtocols().size(); i++) {
			ProtocolDecl protocol=elem.getProtocols().get(i);
			
			if (context.getResource() != null) {
				if (protocol instanceof LProtocolDecl) {
					Role role=((LProtocolDecl)protocol).getLocalRole();
					
					if (f_global) {
						logger.error(MessageFormat.format(ValidationMessages.getMessage("LOCAL_DEFINED_IN_GLOBAL_MODULE"),
													role.getName()), protocol);
					} else if (!role.getName().equals(localRole)) {
						logger.error(MessageFormat.format(ValidationMessages.getMessage("LOCAL_MODULE_ROLE_MISMATCH"),
								role.getName(), localRole), protocol);
					}
				} else if (!f_global) {
					logger.error(MessageFormat.format(ValidationMessages.getMessage("GLOBAL_DEFINED_IN_LOCAL_MODULE"),
													localRole), protocol);
				}
			}
			
			// Validate the protocol
			ValidationRule rule=ValidationRuleFactory.getValidationRule(protocol);
			
			if (rule != null) {
				rule.validate(context, protocol, logger);
			}
		}
		
		// Well formed ness checks
		if (context.getResource() != null && context.getResource().getPath() != null) {
			String filepath=elem.getName().replace('.',  java.io.File.separatorChar);
			
			if (localRole != null) {
				filepath += "@"+localRole;
			}
			
			filepath += ".scr";			
			
			if (!context.getResource().getPath().equals(filepath)) {
				logger.error(MessageFormat.format(ValidationMessages.getMessage("INCORRECT_FILEPATH"),
						elem.getName(), filepath), mobj);
			}
		}
		
		java.util.List<String> moduleNames=new java.util.ArrayList<String>();
		if (elem.getName() != null) {
			moduleNames.add(elem.getLocalName());
		}
		
		for (ImportDecl imp : elem.getImports()) {
			if (imp.getMemberName() == null) {
				String declName=imp.getDeclarationName();
				
				if (moduleNames.contains(declName)) {
					logger.error(MessageFormat.format(ValidationMessages.getMessage("MODULE_NAME_NOT_DISTINCT"),
							declName), mobj);
				} else {
					moduleNames.add(declName);
				}
			}
		}
		
		java.util.List<String> memberNames=new java.util.ArrayList<String>();
		
		for (ImportDecl imp : elem.getImports()) {
			if (imp.getMemberName() != null) {
				String declName=imp.getDeclarationName();
				
				if (memberNames.contains(declName)) {
					logger.error(MessageFormat.format(ValidationMessages.getMessage("MEMBER_NAME_NOT_DISTINCT"),
							declName), imp);
				} else {
					memberNames.add(declName);
				}
			}
		}
		
		for (PayloadTypeDecl plt : elem.getPayloadTypeDeclarations()) {
			if (memberNames.contains(plt.getAlias())) {
				logger.error(MessageFormat.format(ValidationMessages.getMessage("MEMBER_NAME_NOT_DISTINCT"),
						plt.getAlias()), plt);
			} else {
				memberNames.add(plt.getAlias());
			}
		}
		
		for (ProtocolDecl pd : elem.getProtocols()) {
			if (memberNames.contains(pd.getName())) {
				logger.error(MessageFormat.format(ValidationMessages.getMessage("MEMBER_NAME_NOT_DISTINCT"),
						pd.getName()), pd);
			} else {
				memberNames.add(pd.getName());
			}
		}
	}

}
