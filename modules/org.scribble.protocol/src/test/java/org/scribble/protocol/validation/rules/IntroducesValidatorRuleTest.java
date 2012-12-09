/*
 * Copyright 2009-11 www.scribble.org
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

import org.scribble.protocol.model.*;
import org.scribble.protocol.validation.DefaultProtocolValidatorContext;

public class IntroducesValidatorRuleTest {

    @org.junit.Test
    public void testValidateNameAlreadyDefined() {
        
        Protocol prot1=new Protocol();
        
        Introduces in1=new Introduces();
        prot1.getBlock().add(in1);
        
        Role part1=new Role();
        part1.setName("part1");
        in1.getIntroducedRoles().add(part1);
        
        Role part2=new Role();
        part2.setName("part2");
        in1.getIntroducedRoles().add(part2);
        
        ParameterDefinition pd=new ParameterDefinition();
        pd.setName("part1");

        prot1.getParameterDefinitions().add(pd);
        
        TestScribbleLogger logger=new TestScribbleLogger();
        
        DefaultProtocolValidatorContext pvc=new DefaultProtocolValidatorContext(null, null);
        
        // Initialize state associated with protocol validator context
        ProtocolValidatorRule prule=new ProtocolValidatorRule();
        prule.validate(pvc, prot1, logger);

        // Validate the rule to be tested
        IntroducesValidatorRule rule=new IntroducesValidatorRule();
        rule.validate(pvc, in1, logger);
        
        logger.verifyErrors(new String[]{
                MessageFormat.format(
                        java.util.PropertyResourceBundle.getBundle(
                        "org.scribble.protocol.Messages").getString("_NAME_ALREADY_DEFINED"),
                            part1.getName())
        });
    }
}
