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

import org.scribble.context.DefaultModuleContext;
import org.scribble.context.ModuleContext;
import org.scribble.model.Module;
import org.scribble.model.global.GBlock;
import org.scribble.model.global.GContinue;
import org.scribble.model.global.GInterruptible;
import org.scribble.model.global.GParallel;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.model.global.GRecursion;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.ValidationMessages;

public class GContinueValidationRuleTest {

	private static final String LABEL1 = "label1";
	private static final String LABEL2 = "label2";

	@org.junit.Test
    public void testContinueValid() {
		GContinueValidationRule rule=new GContinueValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd1=new GProtocolDefinition();    	
    	module.getProtocols().add(gpd1);
    	
    	GBlock block=new GBlock();
    	gpd1.setBlock(block);
    	
    	GRecursion recur=new GRecursion();
    	recur.setLabel(LABEL1);
    	block.add(recur);
    	
    	GContinue cont=new GContinue();
    	cont.setLabel(LABEL1);
    	recur.getBlock().add(cont);
    	
    	ModuleContext context=new DefaultModuleContext(null, null, null);
    	
    	rule.validate(context, cont, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testContinueNotBound() {
		GContinueValidationRule rule=new GContinueValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
       	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd1=new GProtocolDefinition();    	
    	module.getProtocols().add(gpd1);
    	
    	GBlock block=new GBlock();
    	gpd1.setBlock(block);
    	
    	GRecursion recur=new GRecursion();
    	recur.setLabel(LABEL1);
    	block.add(recur);
    	
    	GContinue cont=new GContinue();
    	cont.setLabel(LABEL2);
    	recur.getBlock().add(cont);
    	
    	ModuleContext context=new DefaultModuleContext(null, null, null);
    	
    	rule.validate(context, cont, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("LABEL_NOT_BOUND"), LABEL2))) {
    		fail("Error LABEL_NOT_BOUND not detected");
    	}
    }
	
	@org.junit.Test
    public void testContinueContainedInParallel() {
		GContinueValidationRule rule=new GContinueValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
       	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd1=new GProtocolDefinition();    	
    	module.getProtocols().add(gpd1);
    	
    	GBlock block=new GBlock();
    	gpd1.setBlock(block);
    	
    	GRecursion recur=new GRecursion();
    	recur.setLabel(LABEL1);
    	block.add(recur);
    	
    	GParallel par=new GParallel();
    	recur.getBlock().add(par);
    	
    	GBlock p1=new GBlock();
    	par.getPaths().add(p1);
    	
    	GContinue cont=new GContinue();
    	cont.setLabel(LABEL1);
    	p1.add(cont);
    	
    	ModuleContext context=new DefaultModuleContext(null, null, null);
    	
    	rule.validate(context, cont, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(ValidationMessages.getMessage("LABEL_CONTAINED_IN_PARALLEL"))) {
    		fail("Error LABEL_CONTAINED_IN_PARALLEL not detected");
    	}
    }
	
	@org.junit.Test
    public void testContinueContainedInInterruptible() {
		GContinueValidationRule rule=new GContinueValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
       	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd1=new GProtocolDefinition();    	
    	module.getProtocols().add(gpd1);
    	
    	GBlock block=new GBlock();
    	gpd1.setBlock(block);
    	
    	GRecursion recur=new GRecursion();
    	recur.setLabel(LABEL1);
    	block.add(recur);
    	
    	GInterruptible interrupt=new GInterruptible();
    	recur.getBlock().add(interrupt);
    	
    	GBlock p1=new GBlock();
    	interrupt.getBlock().add(p1);
    	
    	GContinue cont=new GContinue();
    	cont.setLabel(LABEL1);
    	p1.add(cont);
    	
    	ModuleContext context=new DefaultModuleContext(null, null, null);
    	
    	rule.validate(context, cont, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(ValidationMessages.getMessage("LABEL_CONTAINED_IN_INTERRUPTIBLE"))) {
    		fail("Error LABEL_CONTAINED_IN_INTERRUPTIBLE not detected");
    	}
    }
}
