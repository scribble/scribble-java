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
import org.scribble.context.DefaultModuleLoader;
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
    	DefaultModuleLoader loader=new DefaultModuleLoader();
    	DefaultModuleContext context=new DefaultModuleContext(null, null, loader);
    	
    	Module impmodule=new Module();
    	impmodule.setName("a.b.c");
    	loader.registerModule(impmodule);
    	
    	ImportDecl elem=new ImportDecl();
    	elem.setModuleName("a.b.c");
    	
    	rule.validate(context, elem, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
    
    @org.junit.Test
    public void testImportDeclNoModule() {
   	ImportDeclValidationRule rule=new ImportDeclValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null);
    	
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
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null);
    	
    	ImportDecl elem=new ImportDecl();
    	elem.setModuleName("a.b.c");
    	
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
    	DefaultModuleLoader loader=new DefaultModuleLoader();
    	DefaultModuleContext context=new DefaultModuleContext(null, null, loader);

    	Module impmodule=new Module();
    	impmodule.setName("a.b.c");
    	loader.registerModule(impmodule);
    	
    	ImportDecl elem=new ImportDecl();
    	elem.setModuleName("a.b.c");
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
    	DefaultModuleLoader loader=new DefaultModuleLoader();
    	DefaultModuleContext context=new DefaultModuleContext(null, null, loader);
    	
    	Module m=new Module();
    	m.setName("a.b.c");
    	ProtocolDecl p1=new GProtocolDefinition();
    	p1.setName("First");
    	m.getProtocols().add(p1);
    	ProtocolDecl p2=new GProtocolDefinition();
    	p2.setName("Second");
    	m.getProtocols().add(p2);
    	loader.registerModule(m);
    	
    	ImportDecl elem1=new ImportDecl();
    	elem1.setModuleName("a.b.c");
    	elem1.setMemberName("First");
    	elem1.setAlias("MyAlias");
    	
    	ImportDecl elem2=new ImportDecl();
    	elem2.setModuleName("a.b.c");
    	elem2.setMemberName("Second");
    	elem2.setAlias("MyAlias");
    	
    	rule.validate(context, elem2, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("EXISTS_ALIAS"), "MyAlias"))) {
    		fail("Error EXISTS_ALIAS not detected");
    	}
    }    
}
