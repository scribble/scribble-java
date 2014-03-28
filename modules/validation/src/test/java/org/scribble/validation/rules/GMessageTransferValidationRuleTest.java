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
import org.scribble.model.global.GMessageTransfer;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.ValidationMessages;
import org.scribble.validation.rules.GMessageTransferValidationRule;

public class GMessageTransferValidationRuleTest {

    private static final String TEST_ROLE1 = "TestRole1";
    private static final String TEST_ROLE2 = "TestRole2";
	
	@org.junit.Test
    public void testFromRoleFound() {
    	GMessageTransferValidationRule rule=new GMessageTransferValidationRule();
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
    	
    	GMessageTransfer gm=new GMessageTransfer();
    	gm.setFromRole(new Role(TEST_ROLE1));
    	block.add(gm);
    	
    	Message message=new Message();    	
    	gm.setMessage(message);
    	
     	rule.validate(null, gm, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
	
	@org.junit.Test
    public void testFromRoleNotFound() {
    	GMessageTransferValidationRule rule=new GMessageTransferValidationRule();
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
    	
    	GMessageTransfer gm=new GMessageTransfer();
    	gm.setFromRole(new Role(TEST_ROLE2));
    	block.add(gm);
    	
    	Message message=new Message();    	
    	gm.setMessage(message);
    	
     	rule.validate(null, gm, logger);
   	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"), TEST_ROLE2))) {
    		fail("Error UNKNOWN_ROLE not detected");
    	}
    }

	@org.junit.Test
    public void testToRoleFound() {
    	GMessageTransferValidationRule rule=new GMessageTransferValidationRule();
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
    	
    	GMessageTransfer gm=new GMessageTransfer();
    	gm.getToRoles().add(new Role(TEST_ROLE1));
    	block.add(gm);
    	
    	Message message=new Message();    	
    	gm.setMessage(message);
    	
     	rule.validate(null, gm, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
	
	@org.junit.Test
    public void testToRoleNotFound() {
    	GMessageTransferValidationRule rule=new GMessageTransferValidationRule();
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
    	
    	GMessageTransfer gm=new GMessageTransfer();
    	gm.getToRoles().add(new Role(TEST_ROLE2));
    	block.add(gm);
    	
    	Message message=new Message();    	
    	gm.setMessage(message);
    	
     	rule.validate(null, gm, logger);
   	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"), TEST_ROLE2))) {
    		fail("Error UNKNOWN_ROLE not detected");
    	}
    }
	
	@org.junit.Test
    public void testToRolesDistinct() {
    	GMessageTransferValidationRule rule=new GMessageTransferValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	
    	RoleDecl rd1=new RoleDecl();
    	rd1.setName(TEST_ROLE1);
    	gpd.getRoleDeclarations().add(rd1);
    	
    	RoleDecl rd2=new RoleDecl();
    	rd2.setName(TEST_ROLE2);
    	gpd.getRoleDeclarations().add(rd2);
    	
    	module.getProtocols().add(gpd);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GMessageTransfer gm=new GMessageTransfer();
    	gm.getToRoles().add(new Role(TEST_ROLE1));
    	gm.getToRoles().add(new Role(TEST_ROLE2));
    	block.add(gm);
    	
    	Message message=new Message();    	
    	gm.setMessage(message);
    	
     	rule.validate(null, gm, logger);
   	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
	
	@org.junit.Test
    public void testToRolesNotDistinct() {
    	GMessageTransferValidationRule rule=new GMessageTransferValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	
    	RoleDecl rd1=new RoleDecl();
    	rd1.setName(TEST_ROLE1);
    	gpd.getRoleDeclarations().add(rd1);
    	
    	RoleDecl rd2=new RoleDecl();
    	rd2.setName(TEST_ROLE2);
    	gpd.getRoleDeclarations().add(rd2);
    	
    	module.getProtocols().add(gpd);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GMessageTransfer gm=new GMessageTransfer();
    	gm.getToRoles().add(new Role(TEST_ROLE1));
    	gm.getToRoles().add(new Role(TEST_ROLE1));
    	block.add(gm);
    	
    	Message message=new Message();    	
    	gm.setMessage(message);
    	
     	rule.validate(null, gm, logger);
   	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("ROLE_NOT_DISTINCT"), TEST_ROLE1))) {
    		fail("Error ROLE_NOT_DISTINCT not detected");
    	}
    }
}
