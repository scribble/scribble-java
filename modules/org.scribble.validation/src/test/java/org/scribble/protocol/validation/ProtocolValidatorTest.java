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
package org.scribble.protocol.validation;

import static org.junit.Assert.*;

import org.scribble.protocol.model.FullyQualifiedName;
import org.scribble.protocol.model.Module;
import org.scribble.protocol.model.global.GProtocolDefinition;

public class ProtocolValidatorTest {

    @org.junit.Test
    public void testModuleValid() {
    	ProtocolValidator pv=new ProtocolValidator();
    	TestValidationLogger logger=new TestValidationLogger();
    	DefaultValidationContext context=new DefaultValidationContext();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	module.getProtocols().add(new GProtocolDefinition());
    	
    	pv.validate(context, module, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
    
    @org.junit.Test
    public void testModuleInvalid() {
    	ProtocolValidator pv=new ProtocolValidator();
    	TestValidationLogger logger=new TestValidationLogger();
    	DefaultValidationContext context=new DefaultValidationContext();
    	
    	Module module=new Module();
    	
    	module.getProtocols().add(new GProtocolDefinition());
    	
    	pv.validate(context, module, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(ValidationMessages.getMessage("NO_FULLY_QUALIFIED_NAME"))) {
    		fail("Error NO_FULLY_QUALIFIED_NAME not detected");
    	}
    }
    
}
