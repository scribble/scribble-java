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

import org.scribble.common.logging.ScribbleLogger;
import org.scribble.common.module.ModuleContext;
import org.scribble.model.ModelObject;
import org.scribble.model.global.DefaultGVisitor;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.model.global.GRecursion;
import org.scribble.validation.ValidationMessages;

/**
 * This class implements the validation rule for the GRecursion
 * component.
 *
 */
public class GRecursionValidationRule implements ValidationRule {

	/**
	 * {@inheritDoc}
	 */
	public void validate(ModuleContext context, ModelObject mobj, final ScribbleLogger logger) {
		final GRecursion elem=(GRecursion)mobj;
		
		// Check that bound label is unique
		GProtocolDefinition gpd=elem.getParent(GProtocolDefinition.class);
		
		if (gpd != null && elem.getLabel() != null) {
			final java.util.Set<String> labels=new java.util.HashSet<String>();
			labels.add(elem.getLabel());
			
			gpd.visit(new DefaultGVisitor() {
			    public boolean start(GRecursion rec) {
			    	if (elem != rec) {
			    		if (rec.getLabel() != null) {
			    			if (labels.contains(rec.getLabel())) {
			    				logger.error(MessageFormat.format(ValidationMessages.getMessage("LABEL_NOT_UNIQUE"),
			    						rec.getLabel()), elem);				
			    			}
			    			labels.add(rec.getLabel());
			    		}
			    	}
			    	return (true);
			    }
			});
		}
		
		if (elem.getBlock() != null) {
			ValidationRule rule=ValidationRuleFactory.getValidationRule(elem.getBlock());
			
			if (rule != null) {
				rule.validate(context, elem.getBlock(), logger);
			}
		}
	}

}
