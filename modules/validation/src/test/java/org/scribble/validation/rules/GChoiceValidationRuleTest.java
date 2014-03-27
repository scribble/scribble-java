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
import org.scribble.model.ParameterDecl.ParameterType;
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;
import org.scribble.model.global.GBlock;
import org.scribble.model.global.GChoice;
import org.scribble.model.global.GMessageTransfer;
import org.scribble.model.global.GParallel;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.ValidationMessages;
import org.scribble.validation.rules.GChoiceValidationRule;

public class GChoiceValidationRuleTest {

    private static final String OP1 = "op1";
    private static final String OP2 = "op2";
    private static final String OP3 = "op3";
    private static final String SIG1 = "sig1";
    private static final String SIG2 = "sig2";
    private static final String SIG3 = "sig3";
    private static final String SIG4 = "sig4";
	private static final String TEST_ROLE1 = "TestRole1";
    private static final String TEST_ROLE2 = "TestRole2";
    private static final String TEST_ROLE3 = "TestRole3";

	@org.junit.Test
    public void testValidChoice() {
		GChoiceValidationRule rule=new GChoiceValidationRule();
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
    	
    	GChoice choice=new GChoice();
    	Role role=new Role();
    	role.setName(TEST_ROLE1);
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
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GChoice choice=new GChoice();
    	Role role=new Role();
    	role.setName(TEST_ROLE1);
    	choice.setRole(role);
    	block.add(choice);
    	
    	rule.validate(null, choice, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_ROLE"), TEST_ROLE1))) {
    		fail("Error UNKNOWN_ROLE not detected");
    	}
    }

	@org.junit.Test
    public void testUndefinedRole() {
		GChoiceValidationRule rule=new GChoiceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
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

	@org.junit.Test
    public void testRolesReceiverBeforeOther() {
		GChoiceValidationRule rule=new GChoiceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	RoleDecl rd1=new RoleDecl();
    	rd1.setName(TEST_ROLE1);
    	gpd.getRoleDeclarations().add(rd1);
    	
    	RoleDecl rd2=new RoleDecl();
    	rd2.setName(TEST_ROLE2);
    	gpd.getRoleDeclarations().add(rd2);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GChoice choice=new GChoice();
    	block.add(choice);
    	
    	choice.setRole(new Role(TEST_ROLE1));
    	
    	GBlock b1=new GBlock();
    	choice.getPaths().add(b1);
    	
    	GMessageTransfer mt1=new GMessageTransfer();
    	mt1.setFromRole(new Role(TEST_ROLE1));
    	mt1.getToRoles().add(new Role(TEST_ROLE2));
    	b1.add(mt1);
    	
    	GBlock b2=new GBlock();
    	choice.getPaths().add(b2);
    	
    	GParallel par2=new GParallel();
    	b2.add(par2);
    	
    	GBlock b3=new GBlock();
    	par2.getPaths().add(b3);
    	
    	GMessageTransfer mt2=new GMessageTransfer();
    	mt2.setFromRole(new Role(TEST_ROLE1));
    	mt2.getToRoles().add(new Role(TEST_ROLE2));
    	b3.add(mt2);
    	
    	GMessageTransfer mt3=new GMessageTransfer();
    	mt3.setFromRole(new Role(TEST_ROLE2));
    	mt3.getToRoles().add(new Role(TEST_ROLE1));
    	b3.add(mt3);
    	
    	GBlock b4=new GBlock();
    	par2.getPaths().add(b4);
    	
    	GMessageTransfer mt4=new GMessageTransfer();
    	mt4.setFromRole(new Role(TEST_ROLE1));
    	mt4.getToRoles().add(new Role(TEST_ROLE2));
    	b4.add(mt4);
    	
    	rule.validate(null, choice, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testRolesNotReceiverBeforeOther() {
		GChoiceValidationRule rule=new GChoiceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	RoleDecl rd1=new RoleDecl();
    	rd1.setName(TEST_ROLE1);
    	gpd.getRoleDeclarations().add(rd1);
    	
    	RoleDecl rd2=new RoleDecl();
    	rd2.setName(TEST_ROLE2);
    	gpd.getRoleDeclarations().add(rd2);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GChoice choice=new GChoice();
    	block.add(choice);
    	
    	choice.setRole(new Role(TEST_ROLE1));
    	
    	GBlock b1=new GBlock();
    	choice.getPaths().add(b1);
    	
    	GMessageTransfer mt1=new GMessageTransfer();
    	mt1.setFromRole(new Role(TEST_ROLE1));
    	mt1.getToRoles().add(new Role(TEST_ROLE2));
    	b1.add(mt1);
    	
    	GBlock b2=new GBlock();
    	choice.getPaths().add(b2);
    	
    	GParallel par2=new GParallel();
    	b2.add(par2);
    	
    	GBlock b3=new GBlock();
    	par2.getPaths().add(b3);
    	
    	GMessageTransfer mt3=new GMessageTransfer();
    	mt3.setFromRole(new Role(TEST_ROLE2));
    	mt3.getToRoles().add(new Role(TEST_ROLE1));
    	b3.add(mt3);
    	
    	GMessageTransfer mt2=new GMessageTransfer();
    	mt2.setFromRole(new Role(TEST_ROLE1));
    	mt2.getToRoles().add(new Role(TEST_ROLE2));
    	b3.add(mt2);
    	
    	GBlock b4=new GBlock();
    	par2.getPaths().add(b4);
    	
    	GMessageTransfer mt4=new GMessageTransfer();
    	mt4.setFromRole(new Role(TEST_ROLE1));
    	mt4.getToRoles().add(new Role(TEST_ROLE2));
    	b4.add(mt4);
    	
    	rule.validate(null, choice, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("ROLE_NOT_RECEIVER"), TEST_ROLE2))) {
    		fail("Error UNKNOWN_ROLE not detected");
    	}
    }

	@org.junit.Test
    public void testRolesReceiverOperatorsDistinct() {
		GChoiceValidationRule rule=new GChoiceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	RoleDecl rd1=new RoleDecl();
    	rd1.setName(TEST_ROLE1);
    	gpd.getRoleDeclarations().add(rd1);
    	
    	RoleDecl rd2=new RoleDecl();
    	rd2.setName(TEST_ROLE2);
    	gpd.getRoleDeclarations().add(rd2);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GChoice choice=new GChoice();
    	block.add(choice);
    	
    	choice.setRole(new Role(TEST_ROLE1));
    	
    	GBlock b1=new GBlock();
    	choice.getPaths().add(b1);
    	
    	GMessageTransfer mt1=new GMessageTransfer();
    	mt1.setFromRole(new Role(TEST_ROLE1));
    	mt1.getToRoles().add(new Role(TEST_ROLE2));
    	
    	Message m1=new Message();
    	mt1.setMessage(m1);

    	MessageSignature ms1=new MessageSignature();
    	m1.setMessageSignature(ms1);
    	ms1.setOperator(OP1);
    	
    	b1.add(mt1);
    	
    	GBlock b2=new GBlock();
    	choice.getPaths().add(b2);
    	
    	GParallel par2=new GParallel();
    	b2.add(par2);
    	
    	GBlock b3=new GBlock();
    	par2.getPaths().add(b3);
    	
    	GMessageTransfer mt2=new GMessageTransfer();
    	mt2.setFromRole(new Role(TEST_ROLE1));
    	mt2.getToRoles().add(new Role(TEST_ROLE2));
    	b3.add(mt2);
    	
    	Message m2=new Message();
    	mt2.setMessage(m2);

    	MessageSignature ms2=new MessageSignature();
    	m2.setMessageSignature(ms2);
    	ms2.setOperator(OP2);
    	
    	GMessageTransfer mt3=new GMessageTransfer();
    	mt3.setFromRole(new Role(TEST_ROLE2));
    	mt3.getToRoles().add(new Role(TEST_ROLE1));
    	b3.add(mt3);
    	
    	Message m3=new Message();
    	mt3.setMessage(m3);

    	MessageSignature ms3=new MessageSignature();
    	m3.setMessageSignature(ms3);
    	ms3.setOperator(OP2);
    	
    	GBlock b4=new GBlock();
    	par2.getPaths().add(b4);
    	
    	GMessageTransfer mt4=new GMessageTransfer();
    	mt4.setFromRole(new Role(TEST_ROLE1));
    	mt4.getToRoles().add(new Role(TEST_ROLE2));
    	b4.add(mt4);
    	
    	Message m4=new Message();
    	mt4.setMessage(m4);

    	MessageSignature ms4=new MessageSignature();
    	m4.setMessageSignature(ms4);
    	ms4.setOperator(OP3);
    	
    	rule.validate(null, choice, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testRolesNotReceiverOperatorsNotDistinct() {
		GChoiceValidationRule rule=new GChoiceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	RoleDecl rd1=new RoleDecl();
    	rd1.setName(TEST_ROLE1);
    	gpd.getRoleDeclarations().add(rd1);
    	
    	RoleDecl rd2=new RoleDecl();
    	rd2.setName(TEST_ROLE2);
    	gpd.getRoleDeclarations().add(rd2);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GChoice choice=new GChoice();
    	block.add(choice);
    	
    	choice.setRole(new Role(TEST_ROLE1));
    	
    	GBlock b1=new GBlock();
    	choice.getPaths().add(b1);
    	
    	GMessageTransfer mt1=new GMessageTransfer();
    	mt1.setFromRole(new Role(TEST_ROLE1));
    	mt1.getToRoles().add(new Role(TEST_ROLE2));
    	
    	Message m1=new Message();
    	mt1.setMessage(m1);

    	MessageSignature ms1=new MessageSignature();
    	m1.setMessageSignature(ms1);
    	ms1.setOperator(OP1);
    	
    	b1.add(mt1);
    	
    	GBlock b2=new GBlock();
    	choice.getPaths().add(b2);
    	
    	GParallel par2=new GParallel();
    	b2.add(par2);
    	
    	GBlock b3=new GBlock();
    	par2.getPaths().add(b3);
    	
    	GMessageTransfer mt2=new GMessageTransfer();
    	mt2.setFromRole(new Role(TEST_ROLE1));
    	mt2.getToRoles().add(new Role(TEST_ROLE2));
    	b3.add(mt2);
    	
    	Message m2=new Message();
    	mt2.setMessage(m2);

    	MessageSignature ms2=new MessageSignature();
    	m2.setMessageSignature(ms2);
    	ms2.setOperator(OP2);
    	
    	GMessageTransfer mt3=new GMessageTransfer();
    	mt3.setFromRole(new Role(TEST_ROLE2));
    	mt3.getToRoles().add(new Role(TEST_ROLE1));
    	b3.add(mt3);
    	
    	Message m3=new Message();
    	mt3.setMessage(m3);

    	MessageSignature ms3=new MessageSignature();
    	m3.setMessageSignature(ms3);
    	ms3.setOperator(OP2);
    	
    	GBlock b4=new GBlock();
    	par2.getPaths().add(b4);
    	
    	GMessageTransfer mt4=new GMessageTransfer();
    	mt4.setFromRole(new Role(TEST_ROLE1));
    	mt4.getToRoles().add(new Role(TEST_ROLE2));
    	b4.add(mt4);
    	
    	Message m4=new Message();
    	mt4.setMessage(m4);

    	MessageSignature ms4=new MessageSignature();
    	m4.setMessageSignature(ms4);
    	ms4.setOperator(OP1);
    	
    	rule.validate(null, choice, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("ROLE_OPERATOR_NOT_DISTINCT"),
    									OP1, TEST_ROLE2))) {
    		fail("Error ROLE_OPERATOR_NOT_DISTINCT not detected");
    	}
    }

	@org.junit.Test
    public void testRolesReceiverSignaturesDistinct() {
		GChoiceValidationRule rule=new GChoiceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	RoleDecl rd1=new RoleDecl();
    	rd1.setName(TEST_ROLE1);
    	gpd.getRoleDeclarations().add(rd1);
    	
    	RoleDecl rd2=new RoleDecl();
    	rd2.setName(TEST_ROLE2);
    	gpd.getRoleDeclarations().add(rd2);
    	
    	ParameterDecl pd1=new ParameterDecl();
    	pd1.setName(SIG1);
    	pd1.setType(ParameterType.Sig);
    	gpd.getParameterDeclarations().add(pd1);
    	
    	ParameterDecl pd2=new ParameterDecl();
    	pd2.setName(SIG2);
    	pd2.setType(ParameterType.Sig);
    	gpd.getParameterDeclarations().add(pd2);
    	
    	ParameterDecl pd3=new ParameterDecl();
    	pd3.setName(SIG3);
    	pd3.setType(ParameterType.Sig);
    	gpd.getParameterDeclarations().add(pd3);
    	
    	ParameterDecl pd4=new ParameterDecl();
    	pd4.setName(SIG4);
    	pd4.setType(ParameterType.Sig);
    	gpd.getParameterDeclarations().add(pd4);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GChoice choice=new GChoice();
    	block.add(choice);
    	
    	choice.setRole(new Role(TEST_ROLE1));
    	
    	GBlock b1=new GBlock();
    	choice.getPaths().add(b1);
    	
    	GMessageTransfer mt1=new GMessageTransfer();
    	mt1.setFromRole(new Role(TEST_ROLE1));
    	mt1.getToRoles().add(new Role(TEST_ROLE2));
    	
    	Message m1=new Message();
    	m1.setParameter(SIG1);
    	mt1.setMessage(m1);

    	b1.add(mt1);
    	
    	GBlock b2=new GBlock();
    	choice.getPaths().add(b2);
    	
    	GParallel par2=new GParallel();
    	b2.add(par2);
    	
    	GBlock b3=new GBlock();
    	par2.getPaths().add(b3);
    	
    	GMessageTransfer mt2=new GMessageTransfer();
    	mt2.setFromRole(new Role(TEST_ROLE1));
    	mt2.getToRoles().add(new Role(TEST_ROLE2));
    	b3.add(mt2);
    	
    	Message m2=new Message();
    	m2.setParameter(SIG2);
    	mt2.setMessage(m2);

    	GMessageTransfer mt3=new GMessageTransfer();
    	mt3.setFromRole(new Role(TEST_ROLE2));
    	mt3.getToRoles().add(new Role(TEST_ROLE1));
    	b3.add(mt3);
    	
    	Message m3=new Message();
    	m3.setParameter(SIG3);
    	mt3.setMessage(m3);

    	GBlock b4=new GBlock();
    	par2.getPaths().add(b4);
    	
    	GMessageTransfer mt4=new GMessageTransfer();
    	mt4.setFromRole(new Role(TEST_ROLE1));
    	mt4.getToRoles().add(new Role(TEST_ROLE2));
    	b4.add(mt4);
    	
    	Message m4=new Message();
    	m4.setParameter(SIG4);
    	mt4.setMessage(m4);

    	rule.validate(null, choice, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testRolesNotReceiverSignaturesNotDistinct() {
		GChoiceValidationRule rule=new GChoiceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	RoleDecl rd1=new RoleDecl();
    	rd1.setName(TEST_ROLE1);
    	gpd.getRoleDeclarations().add(rd1);
    	
    	RoleDecl rd2=new RoleDecl();
    	rd2.setName(TEST_ROLE2);
    	gpd.getRoleDeclarations().add(rd2);
    	
    	ParameterDecl pd1=new ParameterDecl();
    	pd1.setName(SIG1);
    	pd1.setType(ParameterType.Sig);
    	gpd.getParameterDeclarations().add(pd1);
    	
    	ParameterDecl pd2=new ParameterDecl();
    	pd2.setName(SIG2);
    	pd2.setType(ParameterType.Sig);
    	gpd.getParameterDeclarations().add(pd2);
    	
    	ParameterDecl pd3=new ParameterDecl();
    	pd3.setName(SIG3);
    	pd3.setType(ParameterType.Sig);
    	gpd.getParameterDeclarations().add(pd3);
    	
    	ParameterDecl pd4=new ParameterDecl();
    	pd4.setName(SIG4);
    	pd4.setType(ParameterType.Sig);
    	gpd.getParameterDeclarations().add(pd4);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GChoice choice=new GChoice();
    	block.add(choice);
    	
    	choice.setRole(new Role(TEST_ROLE1));
    	
    	GBlock b1=new GBlock();
    	choice.getPaths().add(b1);
    	
    	GMessageTransfer mt1=new GMessageTransfer();
    	mt1.setFromRole(new Role(TEST_ROLE1));
    	mt1.getToRoles().add(new Role(TEST_ROLE2));
    	
    	Message m1=new Message();
    	m1.setParameter(SIG1);
    	mt1.setMessage(m1);

    	b1.add(mt1);
    	
    	GBlock b2=new GBlock();
    	choice.getPaths().add(b2);
    	
    	GParallel par2=new GParallel();
    	b2.add(par2);
    	
    	GBlock b3=new GBlock();
    	par2.getPaths().add(b3);
    	
    	GMessageTransfer mt2=new GMessageTransfer();
    	mt2.setFromRole(new Role(TEST_ROLE1));
    	mt2.getToRoles().add(new Role(TEST_ROLE2));
    	b3.add(mt2);
    	
    	Message m2=new Message();
    	m2.setParameter(SIG2);
    	mt2.setMessage(m2);

    	GMessageTransfer mt3=new GMessageTransfer();
    	mt3.setFromRole(new Role(TEST_ROLE2));
    	mt3.getToRoles().add(new Role(TEST_ROLE1));
    	b3.add(mt3);
    	
    	Message m3=new Message();
    	m3.setParameter(SIG3);
    	mt3.setMessage(m3);

    	GBlock b4=new GBlock();
    	par2.getPaths().add(b4);
    	
    	GMessageTransfer mt4=new GMessageTransfer();
    	mt4.setFromRole(new Role(TEST_ROLE1));
    	mt4.getToRoles().add(new Role(TEST_ROLE2));
    	b4.add(mt4);
    	
    	Message m4=new Message();
    	m4.setParameter(SIG1);
    	mt4.setMessage(m4);

    	rule.validate(null, choice, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("ROLE_SIGNATURE_NOT_DISTINCT"),
    									SIG1, TEST_ROLE2))) {
    		fail("Error ROLE_SIGNATURE_NOT_DISTINCT not detected");
    	}
    }

	@org.junit.Test
    public void testRolesMatch() {
		GChoiceValidationRule rule=new GChoiceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	RoleDecl rd1=new RoleDecl();
    	rd1.setName(TEST_ROLE1);
    	gpd.getRoleDeclarations().add(rd1);
    	
    	RoleDecl rd2=new RoleDecl();
    	rd2.setName(TEST_ROLE2);
    	gpd.getRoleDeclarations().add(rd2);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GChoice choice=new GChoice();
    	block.add(choice);
    	
    	choice.setRole(new Role(TEST_ROLE1));
    	
    	GBlock b1=new GBlock();
    	choice.getPaths().add(b1);
    	
    	GMessageTransfer mt1=new GMessageTransfer();
    	mt1.setFromRole(new Role(TEST_ROLE1));
    	mt1.getToRoles().add(new Role(TEST_ROLE2));
    	b1.add(mt1);
    	
    	GBlock b2=new GBlock();
    	choice.getPaths().add(b2);
    	
    	GMessageTransfer mt2=new GMessageTransfer();
    	mt2.setFromRole(new Role(TEST_ROLE1));
    	mt2.getToRoles().add(new Role(TEST_ROLE2));
    	b2.add(mt2);
    	
    	rule.validate(null, choice, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testRolesMisMatch() {
		GChoiceValidationRule rule=new GChoiceValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setName("test");
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	RoleDecl rd1=new RoleDecl();
    	rd1.setName(TEST_ROLE1);
    	gpd.getRoleDeclarations().add(rd1);
    	
    	RoleDecl rd2=new RoleDecl();
    	rd2.setName(TEST_ROLE2);
    	gpd.getRoleDeclarations().add(rd2);
    	
    	RoleDecl rd3=new RoleDecl();
    	rd3.setName(TEST_ROLE3);
    	gpd.getRoleDeclarations().add(rd3);
    	
    	GBlock block=new GBlock();
    	gpd.setBlock(block);
    	
    	GChoice choice=new GChoice();
    	block.add(choice);
    	
    	choice.setRole(new Role(TEST_ROLE1));
    	
    	GBlock b1=new GBlock();
    	choice.getPaths().add(b1);
    	
    	GMessageTransfer mt1=new GMessageTransfer();
    	mt1.setFromRole(new Role(TEST_ROLE1));
    	mt1.getToRoles().add(new Role(TEST_ROLE2));
    	b1.add(mt1);
    	
    	GBlock b2=new GBlock();
    	choice.getPaths().add(b2);
    	
    	GMessageTransfer mt2=new GMessageTransfer();
    	mt2.setFromRole(new Role(TEST_ROLE1));
    	mt2.getToRoles().add(new Role(TEST_ROLE3));
    	b2.add(mt2);
    	
    	rule.validate(null, choice, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(ValidationMessages.getMessage("ROLES_MISMATCH"))) {
    		fail("Error ROLES_MISMATCH not detected");
    	}
    }
}
