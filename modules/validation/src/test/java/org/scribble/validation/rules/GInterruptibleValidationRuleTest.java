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
import org.scribble.model.global.GBlock;
import org.scribble.model.global.GInterruptible;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.rules.GInterruptibleValidationRule;

public class GInterruptibleValidationRuleTest {

    private static final String TEST_ROLE1 = "TestRole1";
    private static final String TEST_ROLE2 = "TestRole2";
	
	@org.junit.Test
    public void testRoleFound() {
		GInterruptibleValidationRule rule=new GInterruptibleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_ROLE1);
    	gpd.getRoleDeclarations().add(rd);
    	
    	module.getProtocols().add(gpd);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GInterruptible gm=new GInterruptible();
    	
    	GInterruptible.Interrupt in=new GInterruptible.Interrupt();
    	in.setRole(new Role(TEST_ROLE1));

    	Message message=new Message();    	
    	in.getMessages().add(message);
    	
    	gm.getInterrupts().add(in);
    	
    	block.add(gm);
    	
     	rule.validate(null, gm, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
	
	@org.junit.Test
    public void testRoleNotFound() {
    	GInterruptibleValidationRule rule=new GInterruptibleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_ROLE1);
    	gpd.getRoleDeclarations().add(rd);
    	
    	module.getProtocols().add(gpd);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GInterruptible gm=new GInterruptible();
    	
    	GInterruptible.Interrupt in=new GInterruptible.Interrupt();
    	in.setRole(new Role(TEST_ROLE2));

    	Message message=new Message();    	
    	in.getMessages().add(message);
    	
    	gm.getInterrupts().add(in);
    	
    	block.add(gm);
    	
     	rule.validate(null, gm, logger);
   	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"), TEST_ROLE2))) {
    		fail("Error UNKNOWN_ROLE not detected");
    	}
    }

}
