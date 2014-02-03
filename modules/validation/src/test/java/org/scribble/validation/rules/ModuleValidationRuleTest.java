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
import org.scribble.common.resources.InputStreamResource;
import org.scribble.model.FullyQualifiedName;
import org.scribble.model.ImportDecl;
import org.scribble.model.Module;
import org.scribble.model.PayloadTypeDecl;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.ValidationMessages;
import org.scribble.validation.rules.ModuleValidationRule;

public class ModuleValidationRuleTest {

    @org.junit.Test
    public void testModuleValid() {
    	ModuleValidationRule rule=new ModuleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	module.getProtocols().add(new GProtocolDefinition());
    	
    	rule.validate(new DefaultModuleContext(null, null, null, null), module, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
    
    @org.junit.Test
    public void testModuleNoFullyQualifiedName() {
    	ModuleValidationRule rule=new ModuleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	module.getProtocols().add(new GProtocolDefinition());
    	
    	rule.validate(new DefaultModuleContext(null, null, null, null), module, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(ValidationMessages.getMessage("NO_FULLY_QUALIFIED_NAME"))) {
    		fail("Error NO_FULLY_QUALIFIED_NAME not detected");
    	}
    }
    
    @org.junit.Test
    public void testFilenameValid() {
    	ModuleValidationRule rule=new ModuleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	module.setFullyQualifiedName(new FullyQualifiedName("a.b.C"));
    	
    	String path="a"+java.io.File.separatorChar+"b"+java.io.File.separatorChar+"C.scr";
    	
    	InputStreamResource resource=new InputStreamResource(path, null);
    	
    	DefaultModuleContext context=new DefaultModuleContext(resource, null, null, null);
    	
    	rule.validate(context, module, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
    
    @org.junit.Test
    public void testFilenameInvalid() {
    	ModuleValidationRule rule=new ModuleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	module.setFullyQualifiedName(new FullyQualifiedName("a.b.D"));
    	
    	String path="a"+java.io.File.separatorChar+"b"+java.io.File.separatorChar+"C.scr";
    	
    	InputStreamResource resource=new InputStreamResource(path, null);
    	
    	DefaultModuleContext context=new DefaultModuleContext(resource, null, null, null);
    	
    	rule.validate(context, module, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	String moduleName="a.b.D";
    	String filepath=moduleName.replace('.', java.io.File.separatorChar)+".scr";
    	
    	if (!logger.getErrors().contains(MessageFormat.format(
    				ValidationMessages.getMessage("INCORRECT_FILEPATH"), moduleName, filepath))) {
    		fail("Error INCORRECT_FILEPATH not detected");
    	}
    }
    
    @org.junit.Test
    public void testModuleNamesDistinct() {
    	ModuleValidationRule rule=new ModuleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	module.setFullyQualifiedName(new FullyQualifiedName("a.b.C"));
    	
    	ImportDecl imp=new ImportDecl();
    	imp.setModuleName(new FullyQualifiedName("a.b.D"));
    	module.getImports().add(imp);
    	
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	Module impmodule=new Module();
    	impmodule.setFullyQualifiedName(new FullyQualifiedName("a.b.D"));
    	context.registerModule(impmodule);
    	
    	rule.validate(context, module, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
    
    @org.junit.Test
    public void testModuleNamesNotDistinct() {
    	ModuleValidationRule rule=new ModuleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	module.setFullyQualifiedName(new FullyQualifiedName("a.b.C"));
    	
    	ImportDecl imp=new ImportDecl();
    	imp.setModuleName(new FullyQualifiedName("a.b.D"));
    	imp.setAlias("C");
    	module.getImports().add(imp);
    	
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	Module impmodule=new Module();
    	impmodule.setFullyQualifiedName(new FullyQualifiedName("a.b.D"));
    	context.registerModule(impmodule);
    	
    	rule.validate(context, module, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(
    				ValidationMessages.getMessage("MODULE_NAME_NOT_DISTINCT"), "C"))) {
    		fail("Error MODULE_NAME_NOT_DISTINCT not detected");
    	}
    }
    
    @org.junit.Test
    public void testMemberNamesDistinct() {
    	ModuleValidationRule rule=new ModuleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	module.setFullyQualifiedName(new FullyQualifiedName("a.b.C"));
    	
    	ImportDecl imp=new ImportDecl();
    	imp.setModuleName(new FullyQualifiedName("a.b.D"));
    	imp.setMemberName("E");
    	module.getImports().add(imp);
    	
    	PayloadTypeDecl ptd=new PayloadTypeDecl();
    	ptd.setAlias("F");
    	module.getPayloadTypeDeclarations().add(ptd);
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	gpd.setName("G");
    	module.getProtocols().add(gpd);
    	
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	Module impmodule=new Module();
    	impmodule.setFullyQualifiedName(new FullyQualifiedName("a.b.D"));

    	PayloadTypeDecl impptd=new PayloadTypeDecl();
    	impptd.setAlias("E");
    	impmodule.getPayloadTypeDeclarations().add(impptd);

    	context.registerModule(impmodule);
    	
    	rule.validate(context, module, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }
    
    @org.junit.Test
    public void testMemberNamesNotDistinct1() {
    	ModuleValidationRule rule=new ModuleValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	
    	module.setFullyQualifiedName(new FullyQualifiedName("a.b.C"));
    	
    	ImportDecl imp=new ImportDecl();
    	imp.setModuleName(new FullyQualifiedName("a.b.D"));
    	imp.setMemberName("E");
    	module.getImports().add(imp);
    	
    	PayloadTypeDecl ptd=new PayloadTypeDecl();
    	ptd.setAlias("F");
    	module.getPayloadTypeDeclarations().add(ptd);
    	
    	GProtocolDefinition gpd=new GProtocolDefinition();
    	gpd.setName("F");
    	module.getProtocols().add(gpd);
    	
    	DefaultModuleContext context=new DefaultModuleContext(null, null, null, null);
    	
    	Module impmodule=new Module();
    	impmodule.setFullyQualifiedName(new FullyQualifiedName("a.b.D"));
    	
    	PayloadTypeDecl impptd=new PayloadTypeDecl();
    	impptd.setAlias("E");
    	impmodule.getPayloadTypeDeclarations().add(impptd);

    	context.registerModule(impmodule);
    	
    	rule.validate(context, module, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(
    				ValidationMessages.getMessage("MEMBER_NAME_NOT_DISTINCT"), "F"))) {
    		fail("Error MEMBER_NAME_NOT_DISTINCT not detected");
    	}
    }
}
