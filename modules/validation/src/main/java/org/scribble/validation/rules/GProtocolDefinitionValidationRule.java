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
import org.scribble.model.ParameterDecl;
import org.scribble.model.PayloadElement;
import org.scribble.model.PayloadTypeDecl;
import org.scribble.model.RoleDecl;
import org.scribble.model.global.DefaultGVisitor;
import org.scribble.model.global.GDo;
import org.scribble.model.global.GInterruptible;
import org.scribble.model.global.GMessageTransfer;
import org.scribble.model.global.GProtocolDefinition;

/**
 * This class implements the validation rule for the GProtocolDefinition
 * component.
 *
 */
public class GProtocolDefinitionValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ModuleContext context, final ModelObject mobj, final IssueLogger logger) {
		GProtocolDefinition elem=(GProtocolDefinition)mobj;
		
		// Wellformedness - check role declaration names are distinct
		java.util.List<String> roleNames=new java.util.ArrayList<String>();
		
		for (RoleDecl rd : elem.getRoleDeclarations()) {
			String roleName=rd.getDeclarationName();
			
			if (roleNames.contains(roleName)) {
				logger.error(MessageFormat.format(ValidationMessages.getMessage("ROLE_NAME_NOT_DISTINCT"),
						roleName), mobj);
			} else {
				roleNames.add(roleName);
			}
		}
		
		// Wellformedness - check parameter declaration names are distinct
		java.util.List<String> paramNames=new java.util.ArrayList<String>();
		
		for (PayloadTypeDecl ptd : elem.getModule().getPayloadTypeDeclarations()) {
			paramNames.add(ptd.getAlias());
		}
		
		for (ImportDecl imp : elem.getModule().getImports()) {
			paramNames.add(imp.getDeclarationName());
		}
		
		for (ParameterDecl pd : elem.getParameterDeclarations()) {
			String paramName=pd.getDeclarationName();
			
			if (paramNames.contains(paramName)) {
				logger.error(MessageFormat.format(ValidationMessages.getMessage("PARAMETER_NAME_NOT_DISTINCT"),
						paramName), mobj);
			} else {
				paramNames.add(paramName);
			}
		}
		
		// Wellformedness - check scope names are distinct
		final java.util.List<String> scopeNames=new java.util.ArrayList<String>();
		
		elem.getBlock().visit(new DefaultGVisitor() {
			
		    public boolean start(GInterruptible elem) {
		    	verifyScope(elem.getScope());
		    	return (true);
		    }
		    
		    public void accept(GDo elem) {
		    	verifyScope(elem.getScope());
		    }
		    
		    protected void verifyScope(String scope) {
		    	if (scope != null) {
    				if (scopeNames.contains(scope)) {
    					logger.error(MessageFormat.format(ValidationMessages.getMessage("SCOPE_NOT_DISTINCT"),
    							scope), mobj);
    				} else {
    					scopeNames.add(scope);
    				}
		    	}
		    }
		});
				
		// Wellformedness - check annotation names are distinct
		final java.util.List<String> annotationNames=new java.util.ArrayList<String>();
		
		elem.getBlock().visit(new DefaultGVisitor() {
			
		    public void accept(GMessageTransfer elem) {
		    	if (elem.getMessage().getMessageSignature() != null) {
		    		for (PayloadElement pe : elem.getMessage().getMessageSignature().getPayloadElements()) {
		    			if (pe.getAnnotation() != null) {
		    				if (annotationNames.contains(pe.getAnnotation())) {
		    					logger.error(MessageFormat.format(ValidationMessages.getMessage("ANNOTATION_NOT_DISTINCT"),
		    							pe.getAnnotation()), mobj);
		    				} else {
		    					annotationNames.add(pe.getAnnotation());
		    				}
		    			}
		    		}
		    	}
		    }
		});
		
		// Validate the block
		if (elem.getBlock() != null) {
			ValidationRule rule=ValidationRuleFactory.getValidationRule(elem.getBlock());
			
			if (rule != null) {
				rule.validate(context, elem.getBlock(), logger);
			}
		}
	}

}
