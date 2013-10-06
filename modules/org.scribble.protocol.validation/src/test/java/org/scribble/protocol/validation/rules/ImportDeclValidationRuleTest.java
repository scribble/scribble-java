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
import org.scribble.protocol.model.ImportDecl;
import org.scribble.protocol.model.Module;
import org.scribble.protocol.model.ProtocolDecl;
import org.scribble.protocol.model.global.GProtocolDefinition;
import org.scribble.protocol.validation.DefaultValidationContext;
import org.scribble.protocol.validation.TestValidationLogger;
import org.scribble.protocol.validation.ValidationMessages;

public class ImportDeclValidationRuleTest {

    @org.junit.Test
    public void testImportDeclValid() {
    	ImportDeclValidationRule rule=new ImportDeclValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	DefaultValidationContext context=new DefaultValidationContext();
    	context.registerModule("a.b.c", new Module());
    	
    	ImportDecl elem=new ImportDecl();
    	elem.setModuleName(new FullyQualifiedName("a.b.c"));
    	
    	rule.validate(context, elem, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
    
    @org.junit.Test
    public void testImportDeclNoModule() {
   	ImportDeclValidationRule rule=new ImportDeclValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	DefaultValidationContext context=new DefaultValidationContext();
    	
    	ImportDecl elem=new ImportDecl();
    	
    	rule.validate(context, elem, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(ValidationMessages.getMessage("NO_MODULE"))) {
    		fail("Error NO_MODULE not detected");
    	}
    }    
    
    @org.junit.Test
    public void testImportDeclNotFoundModule() {
   	ImportDeclValidationRule rule=new ImportDeclValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	DefaultValidationContext context=new DefaultValidationContext();
    	
    	ImportDecl elem=new ImportDecl();
    	elem.setModuleName(new FullyQualifiedName("a.b.c"));
    	
    	rule.validate(context, elem, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("NOT_FOUND_MODULE"), "a.b.c"))) {
    		fail("Error NOT_FOUND_MODULE not detected");
    	}
    }    
    
    @org.junit.Test
    public void testImportDeclNotFoundMember() {
   	ImportDeclValidationRule rule=new ImportDeclValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	DefaultValidationContext context=new DefaultValidationContext();
    	context.registerModule("a.b.c", new Module());
    	
    	ImportDecl elem=new ImportDecl();
    	elem.setModuleName(new FullyQualifiedName("a.b.c"));
    	elem.setMemberName("Test");
    	
    	rule.validate(context, elem, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("NOT_FOUND_MEMBER"), "Test", "a.b.c"))) {
    		fail("Error NOT_FOUND_MEMBER not detected");
    	}
    }    
    
    @org.junit.Test
    public void testImportDeclExistsAlias() {
   	ImportDeclValidationRule rule=new ImportDeclValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	DefaultValidationContext context=new DefaultValidationContext();
    	
    	Module m=new Module();
    	ProtocolDecl p=new GProtocolDefinition();
    	p.setName("Init");
    	m.getProtocols().add(p);
    	context.registerModule("a.b.c", m);
    	context.registerAlias("a.b.c", "Init", "MyAlias");
    	
    	ImportDecl elem=new ImportDecl();
    	elem.setModuleName(new FullyQualifiedName("a.b.c"));
    	elem.setMemberName("Init");
    	elem.setAlias("MyAlias");
    	
    	rule.validate(context, elem, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("EXISTS_ALIAS"), "MyAlias"))) {
    		fail("Error EXISTS_ALIAS not detected");
    	}
    }    
}
