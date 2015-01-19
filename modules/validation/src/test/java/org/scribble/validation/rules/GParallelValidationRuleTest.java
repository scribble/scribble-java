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
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;
import org.scribble.model.global.GBlock;
import org.scribble.model.global.GMessageTransfer;
import org.scribble.model.global.GParallel;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.validation.TestValidationLogger;

public class GParallelValidationRuleTest {

    private static final String OP1 = "op1";
    private static final String OP2 = "op2";
	private static final String TEST_ROLE1 = "TestRole1";
    private static final String TEST_ROLE2 = "TestRole2";
    private static final String TEST_ROLE3 = "TestRole3";

	@org.junit.Test
    public void testParallelValid() {
		GParallelValidationRule rule=new GParallelValidationRule();
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
    	
    	GParallel par=new GParallel();
    	block.add(par);
    	
    	GBlock b1=new GBlock();
    	par.getPaths().add(b1);
    	
    	GMessageTransfer mt1=new GMessageTransfer();
    	
    	MessageSignature sig1=new MessageSignature();
    	sig1.setOperator(OP1);
    	
    	Message m1=new Message();
    	m1.setMessageSignature(sig1);
    	
    	mt1.setMessage(m1);
    	mt1.setFromRole(new Role(TEST_ROLE1));
    	mt1.getToRoles().add(new Role(TEST_ROLE2));
    	b1.add(mt1);
    	
    	GBlock b2=new GBlock();
    	par.getPaths().add(b2);
    	
    	GMessageTransfer mt2=new GMessageTransfer();
    	
    	MessageSignature sig2=new MessageSignature();
    	sig2.setOperator(OP2);
    	
    	Message m2=new Message();
    	m2.setMessageSignature(sig2);
    	
    	mt2.setMessage(m2);
    	mt2.setFromRole(new Role(TEST_ROLE1));
    	mt2.getToRoles().add(new Role(TEST_ROLE2));
    	b2.add(mt2);
    	
    	rule.validate(null, par, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testInteractionInConncurrentPath() {
		GParallelValidationRule rule=new GParallelValidationRule();
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
    	
    	GParallel par=new GParallel();
    	block.add(par);
    	
    	GBlock b1=new GBlock();
    	par.getPaths().add(b1);
    	
    	GMessageTransfer mt1=new GMessageTransfer();
    	
    	MessageSignature sig1=new MessageSignature();
    	sig1.setOperator(OP1);
    	
    	Message m1=new Message();
    	m1.setMessageSignature(sig1);
    	
    	mt1.setMessage(m1);
    	mt1.setFromRole(new Role(TEST_ROLE1));
    	mt1.getToRoles().add(new Role(TEST_ROLE2));
    	b1.add(mt1);
    	
    	GBlock b2=new GBlock();
    	par.getPaths().add(b2);
    	
    	GMessageTransfer mt2=new GMessageTransfer();
    	
    	MessageSignature sig2=new MessageSignature();
    	sig2.setOperator(OP2);
    	
    	Message m2=new Message();
    	m2.setMessageSignature(sig2);
    	
    	mt2.setMessage(m2);
    	mt2.setFromRole(new Role(TEST_ROLE1));
    	mt2.getToRoles().add(new Role(TEST_ROLE2));
    	b2.add(mt2);
    	
    	GMessageTransfer mt3=new GMessageTransfer();
    	
    	MessageSignature sig3=new MessageSignature();
    	sig3.setOperator(OP1);
    	
    	Message m3=new Message();
    	m3.setMessageSignature(sig3);
    	
    	mt3.setMessage(m3);
    	mt3.setFromRole(new Role(TEST_ROLE1));
    	mt3.getToRoles().add(new Role(TEST_ROLE2));
    	b2.add(mt3);
    	
    	rule.validate(null, par, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("INTERACTION_IN_CONCURRENT_PATHS"),
												mt1))) {
    		fail("Error INTERACTION_IN_CONCURRENT_PATHS not detected");
    	}
    }
}
