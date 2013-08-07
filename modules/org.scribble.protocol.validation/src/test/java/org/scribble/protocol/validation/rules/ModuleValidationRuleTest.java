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

import static org.junit.Assert.*;

import org.scribble.protocol.model.FullyQualifiedName;
import org.scribble.protocol.model.Module;
import org.scribble.protocol.model.global.GProtocol;
import org.scribble.protocol.validation.TestValidationLogger;
import org.scribble.protocol.validation.ValidationMessages;

public class ModuleValidationRuleTest {

    @org.junit.Test
    public void testModuleValid() {
    	ModuleValidationRule rule=new ModuleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	module.getProtocols().add(new GProtocol());
    	
    	rule.validate(null, module, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
    
    @org.junit.Test
    public void testModuleNoPackage() {
    	ModuleValidationRule rule=new ModuleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	module.getProtocols().add(new GProtocol());
    	
    	rule.validate(null, module, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(ValidationMessages.getMessage("NO_PACKAGE"))) {
    		fail("Error NO_PACKAGE not detected");
    	}
    }
    
    @org.junit.Test
    public void testModuleNoProtocol() {
    	ModuleValidationRule rule=new ModuleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	rule.validate(null, module, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(ValidationMessages.getMessage("NO_PROTOCOLS"))) {
    		fail("Error NO_PROTOCOLS not detected");
    	}
    }
    
}
