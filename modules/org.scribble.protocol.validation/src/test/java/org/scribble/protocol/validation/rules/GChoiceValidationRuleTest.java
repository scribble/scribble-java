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

import java.text.MessageFormat;

import org.scribble.protocol.model.FullyQualifiedName;
import org.scribble.protocol.model.Module;
import org.scribble.protocol.model.RoleDecl;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.model.global.GBlock;
import org.scribble.protocol.model.global.GChoice;
import org.scribble.protocol.model.global.GProtocolDefinition;
import org.scribble.protocol.validation.TestValidationLogger;
import org.scribble.protocol.validation.ValidationMessages;

public class GChoiceValidationRuleTest {

    private static final String TEST_ROLE = "TestRole";

	@org.junit.Test
    public void testValidChoice() {
		GChoiceValidationRule rule=new GChoiceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_ROLE);
    	gpd.getRoleDeclarations().add(rd);
    	
    	module.getProtocols().add(gpd);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GChoice choice=new GChoice();
    	Role role=new Role();
    	role.setName(TEST_ROLE);
    	choice.setRole(role);
    	block.add(choice);
    	
    	rule.validate(null, choice, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testUnknownRole() {
    	GChoiceValidationRule rule=new GChoiceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GChoice choice=new GChoice();
    	Role role=new Role();
    	role.setName(TEST_ROLE);
    	choice.setRole(role);
    	block.add(choice);
    	
    	rule.validate(null, choice, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"), TEST_ROLE))) {
    		fail("Error UNKNOWN_ROLE not detected");
    	}
    }

	@org.junit.Test
    public void testUndefinedRole() {
		GChoiceValidationRule rule=new GChoiceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GChoice choice=new GChoice();
    	block.add(choice);
    	
    	rule.validate(null, choice, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(ValidationMessages.getMessage("UNDEFINED_ROLE"))) {
    		fail("Error UNDEFINED_ROLE not detected");
    	}
    }
}
