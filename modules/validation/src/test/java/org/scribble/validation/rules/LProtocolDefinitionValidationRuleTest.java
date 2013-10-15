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

import org.scribble.context.DefaultModuleContext;
import org.scribble.context.ModuleContext;
import org.scribble.model.FullyQualifiedName;
import org.scribble.model.ModelObject;
import org.scribble.model.Module;
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;
import org.scribble.model.local.LProtocolDefinition;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.ValidationMessages;
import org.scribble.validation.rules.LProtocolDefinitionValidationRule;

public class LProtocolDefinitionValidationRuleTest {

	private static final String TEST_NAME1 = "TestName1";
	private static final String TEST_NAME2 = "TestName2";
	private static final String TEST_MEMBER_NAME = "TestMemberName";

	@org.junit.Test
    public void testLocalRoleDeclared() {
    	LProtocolDefinitionValidationRule rule=new LProtocolDefinitionValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolDefinition gpi=new LProtocolDefinition();
    	gpi.setLocalRole(new Role(TEST_NAME1));
    	gpi.setName(TEST_MEMBER_NAME);
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_NAME1);
    	gpi.getRoleDeclarations().add(rd);
    	
    	module.getProtocols().add(gpi);
    	
    	ModuleContext context=new DefaultModuleContext() {

			public ModelObject getMember(String fqn) {
				LProtocolDefinition ret=new LProtocolDefinition();
				ret.getRoleDeclarations().add(new RoleDecl());
				return ret;
			}
    	};
    	
    	rule.validate(context, gpi, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testLocalRoleNotDeclared() {
		LProtocolDefinitionValidationRule rule=new LProtocolDefinitionValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolDefinition gpi=new LProtocolDefinition();
    	gpi.setLocalRole(new Role(TEST_NAME2));
    	gpi.setName(TEST_MEMBER_NAME);
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_NAME1);
    	gpi.getRoleDeclarations().add(rd);
    	
    	module.getProtocols().add(gpi);
    	
    	ModuleContext context=new DefaultModuleContext() {

			public ModelObject getMember(String fqn) {
				LProtocolDefinition ret=new LProtocolDefinition();
				ret.getRoleDeclarations().add(new RoleDecl());
				return ret;
			}
    	};
    	
    	rule.validate(context, gpi, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("LOCAL_ROLE_NOT_DECLARED"), TEST_NAME2))) {
    		fail("Error LOCAL_ROLE_NOT_DECLARED not detected");
    	}
    }
}
