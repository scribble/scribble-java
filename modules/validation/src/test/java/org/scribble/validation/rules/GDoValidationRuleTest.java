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
import org.scribble.model.global.GBlock;
import org.scribble.model.global.GDo;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.validation.TestValidationLogger;
import org.scribble.validation.ValidationMessages;
import org.scribble.validation.rules.GDoValidationRule;

public class GDoValidationRuleTest {

    private static final String PROTOCOL2 = "Protocol2";
	private static final String PROTOCOL1 = "Protocol1";

	@org.junit.Test
    public void testValidDo() {
		GDoValidationRule rule=new GDoValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	GProtocolDefinition gpd1=new GProtocolDefinition();
    	gpd1.setName(PROTOCOL1);
    	
    	module.getProtocols().add(gpd1);
    	
    	GBlock block=new GBlock();
    	gpd1.setBlock(block);
    	
    	GDo gdo=new GDo();
    	gdo.setProtocol(new FullyQualifiedName(PROTOCOL2));
    	block.add(gdo);
    	
    	ModuleContext context=new DefaultModuleContext() {

			public ModelObject getMember(String fqn) {
				return new GProtocolDefinition();
			}
    	};
    	
    	rule.validate(context, gdo, logger);
    	
    	if (logger.isErrorsOrWarnings()) {
    		fail("Errors detected");
    	}
    }

	@org.junit.Test
    public void testUnknownProtocol() {
    	GDoValidationRule rule=new GDoValidationRule();
    	TestValidationLogger logger=new TestValidationLogger();
    	
    	Module module=new Module();
    	module.setFullyQualifiedName(new FullyQualifiedName("test"));
    	
    	GProtocolDefinition gpd1=new GProtocolDefinition();
    	gpd1.setName(PROTOCOL1);
    	
    	module.getProtocols().add(gpd1);
    	
    	GBlock block=new GBlock();
    	gpd1.setBlock(block);
    	
    	GDo gdo=new GDo();
    	gdo.setProtocol(new FullyQualifiedName(PROTOCOL2));
    	block.add(gdo);
    	
    	ModuleContext context=new DefaultModuleContext() {

			public ModelObject getMember(String fqn) {
				return null;
			}
    	};
    	
    	rule.validate(context, gdo, logger);
    	
    	if (!logger.isErrorsOrWarnings()) {
    		fail("Errors not detected");
    	}
    	
    	if (!logger.getErrors().contains(MessageFormat.format(ValidationMessages.getMessage("UNKNOWN_PROTOCOL"), PROTOCOL2))) {
    		fail("Error UNKNOWN_PROTOCOL not detected");
    	}
    }
}
