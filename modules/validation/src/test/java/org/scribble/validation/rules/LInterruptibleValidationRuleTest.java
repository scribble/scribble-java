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

import org.scribble.model.FullyQualifiedName;
import org.scribble.model.Message;
import org.scribble.model.Module;
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;
import org.scribble.model.local.LBlock;
import org.scribble.model.local.LInterruptible;
import org.scribble.model.local.LProtocolDefinition;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.ValidationMessages;
import org.scribble.validation.rules.LInterruptibleValidationRule;

public class LInterruptibleValidationRuleTest {

    private static final String TEST_ROLE1 = "TestRole1";
    private static final String TEST_ROLE2 = "TestRole2";
	
	@org.junit.Test
    public void testThrowRoleFound() {
		LInterruptibleValidationRule rule=new LInterruptibleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolDefinition lpd=new LProtocolDefinition();
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_ROLE1);
    	lpd.getRoleDeclarations().add(rd);
    	
    	module.getProtocols().add(lpd);
    	
    	LBlock block=new LBlock();
    	lpd.setBlock(block);
    	
    	LInterruptible gm=new LInterruptible();
    	
    	LInterruptible.Throw t=new LInterruptible.Throw();
    	t.getToRoles().add(new Role(TEST_ROLE1));

    	Message message=new Message();    	
    	t.getMessages().add(message);
    	
    	gm.setThrows(t);
    	
    	block.add(gm);
    	
     	rule.validate(null, gm, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
	
	@org.junit.Test
    public void testCatchRoleFound() {
		LInterruptibleValidationRule rule=new LInterruptibleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolDefinition lpd=new LProtocolDefinition();
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_ROLE1);
    	lpd.getRoleDeclarations().add(rd);
    	
    	module.getProtocols().add(lpd);
    	
    	LBlock block=new LBlock();
    	lpd.setBlock(block);
    	
    	LInterruptible gm=new LInterruptible();
    	
    	LInterruptible.Catch c=new LInterruptible.Catch();
    	c.setRole(new Role(TEST_ROLE1));

    	Message message=new Message();    	
    	c.getMessages().add(message);
    	
    	gm.getCatches().add(c);
    	
    	block.add(gm);
    	
     	rule.validate(null, gm, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
	
	@org.junit.Test
    public void testThrowRoleNotFound() {
		LInterruptibleValidationRule rule=new LInterruptibleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolDefinition lpd=new LProtocolDefinition();
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_ROLE1);
    	lpd.getRoleDeclarations().add(rd);
    	
    	module.getProtocols().add(lpd);
    	
    	LBlock block=new LBlock();
    	lpd.setBlock(block);
    	
    	LInterruptible gm=new LInterruptible();
    	
    	LInterruptible.Throw t=new LInterruptible.Throw();
    	t.getToRoles().add(new Role(TEST_ROLE2));

    	Message message=new Message();    	
    	t.getMessages().add(message);
    	
    	gm.setThrows(t);
    	
    	block.add(gm);
    	
     	rule.validate(null, gm, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"), TEST_ROLE2))) {
    		fail("Error UNKNOWN_ROLE not detected");
    	}
    }
	
	@org.junit.Test
    public void testCatchRoleNotFound() {
		LInterruptibleValidationRule rule=new LInterruptibleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolDefinition lpd=new LProtocolDefinition();
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_ROLE1);
    	lpd.getRoleDeclarations().add(rd);
    	
    	module.getProtocols().add(lpd);
    	
    	LBlock block=new LBlock();
    	lpd.setBlock(block);
    	
    	LInterruptible gm=new LInterruptible();
    	
    	LInterruptible.Catch c=new LInterruptible.Catch();
    	c.setRole(new Role(TEST_ROLE2));

    	Message message=new Message();    	
    	c.getMessages().add(message);
    	
    	gm.getCatches().add(c);
    	
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
