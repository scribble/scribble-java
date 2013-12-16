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
import org.scribble.model.FullyQualifiedName;
import org.scribble.model.ImportDecl;
import org.scribble.model.Module;
import org.scribble.model.ProtocolDecl;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.ValidationMessages;
import org.scribble.validation.rules.ImportDeclValidationRule;

public class ImportDeclValidationRuleTest {

    @org.junit.Test
    public void testImportDeclValid() {
    	ImportDeclValidationRule rule=new ImportDeclValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	Module impmodule=new Module();
    	impmodule.setFullyQualifiedName(new FullyQualifiedName("a.b.c"));
    	context.registerModule(impmodule);
    	
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
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
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
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
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
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);

    	Module impmodule=new Module();
    	impmodule.setFullyQualifiedName(new FullyQualifiedName("a.b.c"));
    	context.registerModule(impmodule);
    	
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
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	Module m=new Module();
    	m.setFullyQualifiedName(new FullyQualifiedName("a.b.c"));
    	ProtocolDecl p=new GProtocolDefinition();
    	p.setName("Init");
    	m.getProtocols().add(p);
    	context.registerModule(m);
    	context.registerImportedMember("a.b.c", "Init", "MyAlias");
    	
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
