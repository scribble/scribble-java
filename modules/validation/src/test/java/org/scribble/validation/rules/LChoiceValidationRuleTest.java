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
import org.scribble.model.Module;
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;
import org.scribble.model.local.LBlock;
import org.scribble.model.local.LChoice;
import org.scribble.model.local.LProtocolDefinition;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.ValidationMessages;
import org.scribble.validation.rules.LChoiceValidationRule;

public class LChoiceValidationRuleTest {

    private static final String TEST_ROLE = "TestRole";

	@org.junit.Test
    public void testValidChoice() {
		LChoiceValidationRule rule=new LChoiceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolDefinition pd=new LProtocolDefinition();
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_ROLE);
    	pd.getRoleDeclarations().add(rd);
    	
    	module.getProtocols().add(pd);
    	
    	LBlock block=new LBlock();
    	pd.setBlock(block);
    	
    	LChoice choice=new LChoice();
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
		LChoiceValidationRule rule=new LChoiceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolDefinition lpd=new LProtocolDefinition();
    	module.getProtocols().add(lpd);
    	
    	LBlock block=new LBlock();
    	lpd.setBlock(block);
    	
    	LChoice choice=new LChoice();
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
		LChoiceValidationRule rule=new LChoiceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolDefinition pd=new LProtocolDefinition();
    	module.getProtocols().add(pd);
    	
    	LBlock block=new LBlock();
    	pd.setBlock(block);
    	
    	LChoice choice=new LChoice();
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
