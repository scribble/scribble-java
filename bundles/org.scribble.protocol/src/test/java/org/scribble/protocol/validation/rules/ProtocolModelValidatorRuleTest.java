/*
 * Copyright 2009-10 www.scribble.org
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

import java.text.MessageFormat;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.DefaultProtocolContext;
import org.scribble.protocol.ProtocolContext;
import org.scribble.protocol.model.*;
import org.scribble.protocol.projection.ProtocolProjector;
import org.scribble.protocol.validation.DefaultProtocolValidatorContext;
import org.scribble.protocol.validation.ProtocolValidationManager;
import org.scribble.protocol.validation.ProtocolValidatorContext;

public class ProtocolModelValidatorRuleTest {

    private static final String TEST_ROLE = "A";

    @org.junit.Test
    public void testUnprojectableRole() {
        ProtocolModel pm=new ProtocolModel();
        
        Protocol p=new Protocol();
        
        ParameterDefinition pd=new ParameterDefinition();
        pd.setName(TEST_ROLE);
        
        p.getParameterDefinitions().add(pd);
        
        pm.setProtocol(p);
                
        TestScribbleLogger logger=new TestScribbleLogger();
        
        DefaultProtocolContext pc=new DefaultProtocolContext();
        
        ProtocolValidatorContext pvc=new DefaultProtocolValidatorContext(pc, new TestErrorProjector());

        ProtocolModelValidatorRule rule=new ProtocolModelValidatorRule();
        rule.validate(pvc, pm, logger);
        
        logger.verifyErrors(new String[]{
                MessageFormat.format(
                        java.util.PropertyResourceBundle.getBundle(
                        "org.scribble.protocol.Messages").getString("_UNPROJECTABLE_ROLES"),
                        TEST_ROLE)
        }, false);
    }
    
    public class TestErrorProjector implements ProtocolProjector {

        public ProtocolModel project(ProtocolContext context,
                ProtocolModel model, Role role, Journal journal) {
            // Log error
            journal.error("An error", null);
            return null;
        }

        public void setProtocolValidationManager(ProtocolValidationManager pvm) {
        }

        public ProtocolValidationManager getProtocolValidationManager() {
             return null;
        }
        
    }
}
