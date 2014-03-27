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

import org.scribble.model.Message;
import org.scribble.model.Module;
import org.scribble.model.ParameterDecl;
import org.scribble.model.ParameterDecl.ParameterType;
import org.scribble.model.global.GBlock;
import org.scribble.model.global.GMessageTransfer;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.ValidationMessages;
import org.scribble.validation.rules.MessageValidationRule;

public class MessageValidationRuleTest {

    private static final String TEST_PARAMETER = "TestParameter";

	@org.junit.Test
    public void testParameterNotFound() {
    	MessageValidationRule rule=new MessageValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GMessageTransfer gm=new GMessageTransfer();
    	block.add(gm);
    	
    	Message message=new Message();    	
    	gm.setMessage(message);
    	
    	message.setParameter(TEST_PARAMETER);
    	
    	rule.validate(null, message, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_PARAMETER"), TEST_PARAMETER))) {
    		fail("Error UNKNOWN_PARAMETER not detected");
    	}
    }

	@org.junit.Test
    public void testParameterNotSig() {
    	MessageValidationRule rule=new MessageValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	ParameterDecl pd=new ParameterDecl();
    	pd.setName(TEST_PARAMETER);
    	pd.setType(ParameterType.Type);
    	gpd.getParameterDeclarations().add(pd);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GMessageTransfer gm=new GMessageTransfer();
    	block.add(gm);
    	
    	Message message=new Message();    	
    	gm.setMessage(message);
    	
    	message.setParameter(TEST_PARAMETER);
    	
    	rule.validate(null, message, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("PARAMETER_NOT_SIG"), TEST_PARAMETER))) {
    		fail("Error PARAMETER_NOT_SIG not detected");
    	}
    }

	@org.junit.Test
    public void testParameterFound() {
    	MessageValidationRule rule=new MessageValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	
    	ParameterDecl pd=new ParameterDecl();
    	pd.setName(TEST_PARAMETER);
    	pd.setType(ParameterType.Sig);
    	gpd.getParameterDeclarations().add(pd);
    	
    	module.getProtocols().add(gpd);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GMessageTransfer gm=new GMessageTransfer();
    	block.add(gm);
    	
    	Message message=new Message();    	
    	gm.setMessage(message);
    	
    	message.setParameter(TEST_PARAMETER);
    	
    	rule.validate(null, message, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
	
}
