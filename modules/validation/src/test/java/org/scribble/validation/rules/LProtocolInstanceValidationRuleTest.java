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

import org.scribble.model.Argument;
import org.scribble.model.FullyQualifiedName;
import org.scribble.model.ModelObject;
import org.scribble.model.Module;
import org.scribble.model.ParameterDecl;
import org.scribble.model.PayloadTypeDecl;
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;
import org.scribble.model.RoleInstantiation;
import org.scribble.model.local.LProtocolDefinition;
import org.scribble.model.local.LProtocolInstance;
import org.scribble.validation.DefaultValidationContext;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.ValidationContext;
import org.scribble.validation.ValidationMessages;
import org.scribble.validation.rules.LProtocolInstanceValidationRule;

public class LProtocolInstanceValidationRuleTest {

	private static final String TEST_NAME1 = "TestName1";
	private static final String TEST_NAME2 = "TestName2";
	private static final String TEST_NAME3 = "TestName3";
	private static final String TEST_MEMBER_NAME = "TestMemberName";

	@org.junit.Test
    public void testValidProtocolInstance() {
    	LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

			public ModelObject getMember(String fqn) {
				return new LProtocolDefinition();
			}
    	};
    	
    	rule.validate(context, gpi, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testUnknownMemberName() {
    	LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

			public ModelObject getMember(String fqn) {
				return null;
			}
    	};
    	
    	rule.validate(context, gpi, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_MEMBER_NAME"), TEST_MEMBER_NAME))) {
    		fail("Error UNKNOWN_MEMBER_NAME not detected");
    	}
    }

	@org.junit.Test
    public void testMemberNotProtocolDefinition() {
		LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

			public ModelObject getMember(String fqn) {
				return new Role();
			}
    	};
    	
    	rule.validate(context, gpi, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("MEMBER_NOT_PROTOCOL_DEFINITION"), TEST_MEMBER_NAME))) {
    		fail("Error MEMBER_NOT_PROTOCOL_DEFINITION not detected");
    	}
    }

	@org.junit.Test
    public void testMismatchArguParamNum() {
    	LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	gpi.getArguments().add(new Argument());
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

			public ModelObject getMember(String fqn) {
				return new LProtocolDefinition();
			}
    	};
    	
    	rule.validate(context, gpi, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("ARG_NUM_MISMATCH"), 1, 0))) {
    		fail("Error ARG_NUM_MISMATCH not detected");
    	}
    }

	@org.junit.Test
    public void testMismatchRoleNum() {
    	LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	gpi.getRoleInstantiations().add(new RoleInstantiation());
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

			public ModelObject getMember(String fqn) {
				return new LProtocolDefinition();
			}
    	};
    	
    	rule.validate(context, gpi, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("ROLE_NUM_MISMATCH"), 1, 0))) {
    		fail("Error ROLE_NUM_MISMATCH not detected");
    	}
    }

	@org.junit.Test
    public void testRoleDeclared() {
    	LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_NAME1);
    	gpi.getRoleDeclarations().add(rd);
    	
    	RoleInstantiation ri=new RoleInstantiation();
    	ri.setName(TEST_NAME1);
    	gpi.getRoleInstantiations().add(ri);
    	
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

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
    public void testRoleNotDeclared() {
    	LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	
    	RoleInstantiation ri=new RoleInstantiation();
    	ri.setName(TEST_NAME1);
    	
    	gpi.getRoleInstantiations().add(ri);
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

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
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("ROLE_NOT_DECLARED"), TEST_NAME1))) {
    		fail("Error ROLE_NOT_DECLARED not detected");
    	}
    }

	@org.junit.Test
    public void testRoleAliasDeclared() {
    	LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_NAME1);
    	gpi.getRoleDeclarations().add(rd);
    	
    	RoleInstantiation ri=new RoleInstantiation();
    	ri.setName(TEST_NAME1);
    	ri.setAlias(TEST_NAME2);
    	gpi.getRoleInstantiations().add(ri);
    	
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

			public ModelObject getMember(String fqn) {
				LProtocolDefinition ret=new LProtocolDefinition();
				RoleDecl rd=new RoleDecl();
				rd.setName(TEST_NAME2);
				ret.getRoleDeclarations().add(rd);
				return ret;
			}
    	};
    	
    	rule.validate(context, gpi, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testRoleAliasNotDeclared() {
		LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_NAME1);
    	gpi.getRoleDeclarations().add(rd);
    	
    	RoleInstantiation ri=new RoleInstantiation();
    	ri.setName(TEST_NAME1);
    	ri.setAlias(TEST_NAME2);
    	
    	gpi.getRoleInstantiations().add(ri);
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

			public ModelObject getMember(String fqn) {
				LProtocolDefinition ret=new LProtocolDefinition();
				RoleDecl rd=new RoleDecl();
				rd.setName(TEST_NAME3);
				ret.getRoleDeclarations().add(rd);
				return ret;
			}
    	};
    	
    	rule.validate(context, gpi, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("ROLE_ALIAS_NOT_DECLARED"), TEST_NAME2))) {
    		fail("Error ROLE_ALIAS_NOT_DECLARED not detected");
    	}
    }

	@org.junit.Test
    public void testArgDeclaredAsParameter() {
    	LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	
    	ParameterDecl pd=new ParameterDecl();
    	pd.setName(TEST_NAME1);
    	gpi.getParameterDeclarations().add(pd);
    	
    	Argument arg=new Argument();
    	arg.setName(TEST_NAME1);
    	gpi.getArguments().add(arg);
    	
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

			public ModelObject getMember(String fqn) {
				LProtocolDefinition ret=new LProtocolDefinition();
				ret.getParameterDeclarations().add(new ParameterDecl());
				return ret;
			}
    	};
    	
    	rule.validate(context, gpi, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testArgDeclaredAsPayloadType() {
    	LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	PayloadTypeDecl ptd=new PayloadTypeDecl();
    	ptd.setAlias(TEST_NAME1);
    	module.getPayloadTypeDeclarations().add(ptd);
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	
    	Argument arg=new Argument();
    	arg.setName(TEST_NAME1);
    	gpi.getArguments().add(arg);
    	
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

			public ModelObject getMember(String fqn) {
				LProtocolDefinition ret=new LProtocolDefinition();
				ret.getParameterDeclarations().add(new ParameterDecl());
				return ret;
			}
    	};
    	
    	rule.validate(context, gpi, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testParameterAndPayloadTypeNotDeclared() {
    	LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	
    	Argument arg=new Argument();
    	arg.setName(TEST_NAME1);
    	
    	gpi.getArguments().add(arg);
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

			public ModelObject getMember(String fqn) {
				LProtocolDefinition ret=new LProtocolDefinition();
				ret.getParameterDeclarations().add(new ParameterDecl());
				return ret;
			}
    	};
    	
    	rule.validate(context, gpi, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("ARG_NOT_DECLARED"), TEST_NAME1))) {
    		fail("Error ARG_NOT_DECLARED not detected");
    	}
    }

	@org.junit.Test
    public void testArgAliasDeclared() {
    	LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	
    	ParameterDecl pd=new ParameterDecl();
    	pd.setName(TEST_NAME1);
    	gpi.getParameterDeclarations().add(pd);
    	
    	Argument arg=new Argument();
    	arg.setName(TEST_NAME1);
    	arg.setAlias(TEST_NAME2);
    	gpi.getArguments().add(arg);
    	
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

			public ModelObject getMember(String fqn) {
				LProtocolDefinition ret=new LProtocolDefinition();
				ParameterDecl pd=new ParameterDecl();
				pd.setName(TEST_NAME2);
				ret.getParameterDeclarations().add(pd);
				return ret;
			}
    	};
    	
    	rule.validate(context, gpi, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testArgAliasNotDeclared() {
    	LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	
    	ParameterDecl pd=new ParameterDecl();
    	pd.setName(TEST_NAME1);
    	gpi.getParameterDeclarations().add(pd);
    	
    	Argument arg=new Argument();
    	arg.setName(TEST_NAME1);
    	arg.setAlias(TEST_NAME2);
    	
    	gpi.getArguments().add(arg);
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

			public ModelObject getMember(String fqn) {
				LProtocolDefinition ret=new LProtocolDefinition();
				ParameterDecl pd=new ParameterDecl();
				pd.setName(TEST_NAME3);
				ret.getParameterDeclarations().add(pd);
				return ret;
			}
    	};
    	
    	rule.validate(context, gpi, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("ARG_ALIAS_NOT_DECLARED"), TEST_NAME2))) {
    		fail("Error ARG_ALIAS_NOT_DECLARED not detected");
    	}
    }
	
	@org.junit.Test
    public void testLocalRoleDeclared() {
    	LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setLocalRole(new Role(TEST_NAME1));
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_NAME1);
    	gpi.getRoleDeclarations().add(rd);
    	
    	RoleInstantiation ri=new RoleInstantiation();
    	ri.setName(TEST_NAME1);
    	gpi.getRoleInstantiations().add(ri);
    	
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

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
    	LProtocolInstanceValidationRule rule=new LProtocolInstanceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	LProtocolInstance gpi=new LProtocolInstance();
    	gpi.setLocalRole(new Role(TEST_NAME2));
    	gpi.setMemberName(TEST_MEMBER_NAME);
    	
    	RoleDecl rd=new RoleDecl();
    	rd.setName(TEST_NAME1);
    	gpi.getRoleDeclarations().add(rd);
    	
    	RoleInstantiation ri=new RoleInstantiation();
    	ri.setName(TEST_NAME1);
    	gpi.getRoleInstantiations().add(ri);
    	
    	module.getProtocols().add(gpi);
    	
    	ValidationContext context=new DefaultValidationContext() {

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
