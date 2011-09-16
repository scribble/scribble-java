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
package org.scribble.protocol.conformance.impl.rules;

import static org.junit.Assert.*;

import org.junit.Test;
import org.scribble.protocol.model.*;

public class InteractionConformanceRuleTest {

    @Test
    public void testValidInteraction() {
        InteractionConformanceRule rule=new InteractionConformanceRule();
        
        Interaction model=new Interaction();
        model.setMessageSignature(new MessageSignature(new TypeReference("A")));
        model.setFromRole(new Role("A"));
        
        Interaction ref=new Interaction();
        ref.setMessageSignature(new MessageSignature(new TypeReference("A")));
        ref.setFromRole(new Role("A"));
        
        if (rule.conforms(model, ref, null) == false) {
            fail("Should not fail");
        }
    }


    @Test
    public void testInvalidInteraction() {
        InteractionConformanceRule rule=new InteractionConformanceRule();
        
        Interaction model=new Interaction();
        model.setMessageSignature(new MessageSignature(new TypeReference("A")));
        model.setFromRole(new Role("A"));
        
        Interaction ref=new Interaction();
        ref.setMessageSignature(new MessageSignature(new TypeReference("A")));
        ref.setFromRole(new Role("B"));
        
        if (rule.conforms(model, ref, null) == true) {
            fail("Should fail");
        }
    }
}
