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
import org.scribble.model.MessageSignature;
import org.scribble.model.Module;
import org.scribble.model.ParameterDecl;
import org.scribble.model.PayloadElement;
import org.scribble.model.PayloadTypeDecl;
import org.scribble.model.global.GBlock;
import org.scribble.model.global.GMessageTransfer;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.ValidationMessages;

public class MessageSignatureValidationRuleTest {

	private static final String KNOWN_PAYLOAD_TYPE = "KnownPayloadType";

	@org.junit.Test
    public void testPayloadTypeFound() {
    	MessageSignatureValidationRule rule=new MessageSignatureValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	PayloadTypeDecl pltd=new PayloadTypeDecl();
    	module.getPayloadTypeDeclarations().add(pltd);
    	
    	pltd.setAlias(KNOWN_PAYLOAD_TYPE);
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GMessageTransfer gm=new GMessageTransfer();
    	block.add(gm);
    	
    	Message message=new Message();    	
    	gm.setMessage(message);
    	
    	MessageSignature ms=new MessageSignature();
    	message.setMessageSignature(ms);
    	
    	PayloadElement plt=new PayloadElement();
    	ms.getPayloadElements().add(plt);
    	
    	plt.setName(KNOWN_PAYLOAD_TYPE);
    	
    	rule.validate(null, ms, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testProtocolParameterNameFound() {
    	MessageSignatureValidationRule rule=new MessageSignatureValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	ParameterDecl pd=new ParameterDecl();
    	gpd.getParameterDeclarations().add(pd);
    	
    	pd.setName(KNOWN_PAYLOAD_TYPE);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GMessageTransfer gm=new GMessageTransfer();
    	block.add(gm);
    	
    	Message message=new Message();    	
    	gm.setMessage(message);
    	
    	MessageSignature ms=new MessageSignature();
    	message.setMessageSignature(ms);
    	
    	PayloadElement plt=new PayloadElement();
    	ms.getPayloadElements().add(plt);
    	
    	plt.setName(KNOWN_PAYLOAD_TYPE);
    	
    	rule.validate(null, ms, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testProtocolParameterAliasFound() {
		MessageSignatureValidationRule rule=new MessageSignatureValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	ParameterDecl pd=new ParameterDecl();
    	gpd.getParameterDeclarations().add(pd);
    	
    	pd.setName("A_Name");
    	pd.setAlias(KNOWN_PAYLOAD_TYPE);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GMessageTransfer gm=new GMessageTransfer();
    	block.add(gm);
    	
    	Message message=new Message();    	
    	gm.setMessage(message);
    	
    	MessageSignature ms=new MessageSignature();
    	message.setMessageSignature(ms);
    	
    	PayloadElement plt=new PayloadElement();
    	ms.getPayloadElements().add(plt);
    	
    	plt.setName(KNOWN_PAYLOAD_TYPE);
    	
    	rule.validate(null, ms, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testPayloadNameUnknown() {
		MessageSignatureValidationRule rule=new MessageSignatureValidationRule();
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
    	
    	MessageSignature ms=new MessageSignature();
    	message.setMessageSignature(ms);
    	
    	PayloadElement plt=new PayloadElement();
    	ms.getPayloadElements().add(plt);
    	
    	plt.setName(KNOWN_PAYLOAD_TYPE);
    	
    	rule.validate(null, ms, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_PAYLOAD_ELEMENT"), KNOWN_PAYLOAD_TYPE))) {
    		fail("Error UNKNOWN_PAYLOAD_ELEMENT not detected");
    	}
    }

}
