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
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;
import org.scribble.model.local.LBlock;
import org.scribble.model.local.LProtocolDefinition;
import org.scribble.model.local.LSend;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.ValidationMessages;
import org.scribble.validation.rules.LSendValidationRule;

public class LSendValidationRuleTest {

    private static final String TEST_ROLE1 = "TestRole1";
    private static final String TEST_ROLE2 = "TestRole2";

	@org.junit.Test
    public void testToRoleFound() {
    	LSendValidationRule rule=new LSendValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	LProtocolDefinition lpd=new LProtocolDefinition();
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_ROLE1);
    	lpd.getRoleDeclarations().add(rd);
    	
    	module.getProtocols().add(lpd);
    	
    	LBlock block=new LBlock();
    	lpd.setBlock(block);
    	
    	LSend send=new LSend();
    	send.getToRoles().add(new Role(TEST_ROLE1));
    	block.add(send);
    	
    	Message message=new Message();    	
    	send.setMessage(message);
    	
     	rule.validate(null, send, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
	
	@org.junit.Test
    public void testToRoleNotFound() {
		LSendValidationRule rule=new LSendValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	LProtocolDefinition lpd=new LProtocolDefinition();
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_ROLE1);
    	lpd.getRoleDeclarations().add(rd);
    	
    	module.getProtocols().add(lpd);
    	
    	LBlock block=new LBlock();
    	lpd.setBlock(block);
    	
    	LSend send=new LSend();
    	send.getToRoles().add(new Role(TEST_ROLE2));
    	block.add(send);
    	
    	Message message=new Message();    	
    	send.setMessage(message);
    	
     	rule.validate(null, send, logger);
   	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"), TEST_ROLE2))) {
    		fail("Error UNKNOWN_ROLE not detected");
    	}
    }
}
