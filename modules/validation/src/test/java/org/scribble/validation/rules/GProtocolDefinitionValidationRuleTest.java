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

import org.scribble.common.module.DefaultModuleContext;
import org.scribble.model.Message;
import org.scribble.model.MessageSignature;
import org.scribble.model.Module;
import org.scribble.model.ParameterDecl;
import org.scribble.model.PayloadElement;
import org.scribble.model.RoleDecl;
import org.scribble.model.global.GDo;
import org.scribble.model.global.GInterruptible;
import org.scribble.model.global.GMessageTransfer;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.ValidationMessages;

public class GProtocolDefinitionValidationRuleTest {

	private static final String NAME2 = "name2";
	private static final String NAME1 = "name1";

	@org.junit.Test
    public void testRoleNamesDistinct() {
    	GProtocolDefinitionValidationRule rule=new GProtocolDefinitionValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	RoleDecl rd1=new RoleDecl();
    	rd1.setName(NAME1);
    	
    	RoleDecl rd2=new RoleDecl();
    	rd2.setName(NAME2);
    	
    	gpd.getRoleDeclarations().add(rd1);
    	gpd.getRoleDeclarations().add(rd2);
    	
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	rule.validate(context, gpd, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testRoleNamesNotDistinct() {
		GProtocolDefinitionValidationRule rule=new GProtocolDefinitionValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	RoleDecl rd1=new RoleDecl();
    	rd1.setName(NAME1);
    	
    	RoleDecl rd2=new RoleDecl();
    	rd2.setName(NAME1);
    	
    	gpd.getRoleDeclarations().add(rd1);
    	gpd.getRoleDeclarations().add(rd2);
    	
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	rule.validate(context, gpd, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("ROLE_NAME_NOT_DISTINCT"), NAME1))) {
    		fail("Error ROLE_NAME_NOT_DISTINCT not detected");
    	}
    }

	@org.junit.Test
    public void testParameterNamesDistinct() {
    	GProtocolDefinitionValidationRule rule=new GProtocolDefinitionValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);

    	ParameterDecl pd1=new ParameterDecl();
    	pd1.setName(NAME1);
    	
    	ParameterDecl pd2=new ParameterDecl();
    	pd2.setName(NAME2);
    	
    	gpd.getParameterDeclarations().add(pd1);
    	gpd.getParameterDeclarations().add(pd2);
    	
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	rule.validate(context, gpd, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testParameterNamesNotDistinct() {
		GProtocolDefinitionValidationRule rule=new GProtocolDefinitionValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	ParameterDecl pd1=new ParameterDecl();
    	pd1.setName(NAME1);
    	
    	ParameterDecl pd2=new ParameterDecl();
    	pd2.setName(NAME1);
    	
    	gpd.getParameterDeclarations().add(pd1);
    	gpd.getParameterDeclarations().add(pd2);
    	
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	rule.validate(context, gpd, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("PARAMETER_NAME_NOT_DISTINCT"), NAME1))) {
    		fail("Error PARAMETER_NAME_NOT_DISTINCT not detected");
    	}
    }

	@org.junit.Test
    public void testScopesDistinct() {
    	GProtocolDefinitionValidationRule rule=new GProtocolDefinitionValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);

    	GInterruptible i1=new GInterruptible();
    	gpd.getBlock().add(i1);
    	
    	i1.setScope(NAME1);
    	
    	GDo d1=new GDo();
    	gpd.getBlock().add(d1);
    	
    	d1.setScope(NAME2);

    	
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	rule.validate(context, gpd, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testScopesNotDistinct() {
		GProtocolDefinitionValidationRule rule=new GProtocolDefinitionValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	GInterruptible i1=new GInterruptible();
    	gpd.getBlock().add(i1);
    	
    	i1.setScope(NAME1);
    	
    	GDo d1=new GDo();
    	gpd.getBlock().add(d1);
    	
    	d1.setScope(NAME1);
    	
    	
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	rule.validate(context, gpd, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("SCOPE_NOT_DISTINCT"), NAME1))) {
    		fail("Error SCOPE_NOT_DISTINCT not detected");
    	}
    }

	@org.junit.Test
    public void testAnnotationsDistinct() {
    	GProtocolDefinitionValidationRule rule=new GProtocolDefinitionValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);

    	GMessageTransfer mt1=new GMessageTransfer();
    	gpd.getBlock().add(mt1);
    	
    	Message m1=new Message();
    	mt1.setMessage(m1);
    	
    	MessageSignature ms1=new MessageSignature();
    	m1.setMessageSignature(ms1);
    	
    	PayloadElement pe1=new PayloadElement();
    	ms1.getPayloadElements().add(pe1);
    	
    	pe1.setAnnotation(NAME1);
    	
    	GMessageTransfer mt2=new GMessageTransfer();
    	gpd.getBlock().add(mt2);

    	Message m2=new Message();
    	mt2.setMessage(m2);
    	
    	MessageSignature ms2=new MessageSignature();
    	m2.setMessageSignature(ms2);
    	
    	PayloadElement pe2=new PayloadElement();
    	ms2.getPayloadElements().add(pe2);
    	
    	pe2.setAnnotation(NAME2);
    	
    	
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	rule.validate(context, gpd, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testAnnotationsNotDistinct() {
		GProtocolDefinitionValidationRule rule=new GProtocolDefinitionValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	module.getProtocols().add(gpd);
    	
    	GMessageTransfer mt1=new GMessageTransfer();
    	gpd.getBlock().add(mt1);
    	
    	Message m1=new Message();
    	mt1.setMessage(m1);
    	
    	MessageSignature ms1=new MessageSignature();
    	m1.setMessageSignature(ms1);
    	
    	PayloadElement pe1=new PayloadElement();
    	ms1.getPayloadElements().add(pe1);
    	
    	pe1.setAnnotation(NAME1);
    	
    	GMessageTransfer mt2=new GMessageTransfer();
    	gpd.getBlock().add(mt2);

    	Message m2=new Message();
    	mt2.setMessage(m2);
    	
    	MessageSignature ms2=new MessageSignature();
    	m2.setMessageSignature(ms2);
    	
    	PayloadElement pe2=new PayloadElement();
    	ms2.getPayloadElements().add(pe2);
    	
    	pe2.setAnnotation(NAME1);
    	
    	
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	rule.validate(context, gpd, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("ANNOTATION_NOT_DISTINCT"), NAME1))) {
    		fail("Error ANNOTATION_NOT_DISTINCT not detected");
    	}
    }
}
