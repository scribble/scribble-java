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

import static org.junit.Assert.*;

import java.text.MessageFormat;

import org.scribble.common.module.DefaultModuleContext;
import org.scribble.common.module.ModuleContext;
import org.scribble.model.FullyQualifiedName;
import org.scribble.model.Module;
import org.scribble.model.global.GBlock;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.model.global.GRecursion;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.ValidationMessages;

public class GRecursionValidationRuleTest {

	private static final String LABEL1 = "label1";
	private static final String LABEL2 = "label2";

	@org.junit.Test
    public void testLabelsUnique() {
		GRecursionValidationRule rule=new GRecursionValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	GProtocolDefinition gpd1=new GProtocolDefinition();    	
    	module.getProtocols().add(gpd1);
    	
    	GBlock block=new GBlock();
    	gpd1.setBlock(block);
    	
    	GRecursion recur1=new GRecursion();
    	recur1.setLabel(LABEL1);
    	block.add(recur1);
    	
    	GRecursion recur2=new GRecursion();
    	recur2.setLabel(LABEL2);
    	block.add(recur2);

    	ModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	rule.validate(context, recur1, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testLabelNotUnique() {
		GRecursionValidationRule rule=new GRecursionValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
       	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	GProtocolDefinition gpd1=new GProtocolDefinition();    	
    	module.getProtocols().add(gpd1);
    	
    	GBlock block=new GBlock();
    	gpd1.setBlock(block);
    	
    	GRecursion recur1=new GRecursion();
    	recur1.setLabel(LABEL1);
    	block.add(recur1);
    	
    	GRecursion recur2=new GRecursion();
    	recur2.setLabel(LABEL1);
    	block.add(recur2);
    	
    	ModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	rule.validate(context, recur1, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("LABEL_NOT_UNIQUE"), LABEL1))) {
    		fail("Error LABEL_NOT_UNIQUE not detected");
    	}
    }
	
}
