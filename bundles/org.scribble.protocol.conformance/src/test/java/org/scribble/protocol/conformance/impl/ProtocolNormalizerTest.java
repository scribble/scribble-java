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
package org.scribble.protocol.conformance.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.scribble.protocol.model.*;

public class ProtocolNormalizerTest {

    @Test
    public void testReorderChoicePaths() {
        
        ProtocolModel pm=new ProtocolModel();
        
        Protocol p=new Protocol();
        pm.setProtocol(p);
        
        Role roleA=new Role("A");
        Role roleB=new Role("B");
        
        Choice c=new Choice();
        p.getBlock().add(c);
        
        Block b1=new Block();
        
        Interaction i1=new Interaction();
        i1.setMessageSignature(new MessageSignature(new TypeReference("M1")));
        i1.setFromRole(roleA);
        b1.add(i1);
        
        Block b2=new Block();
        
        Interaction i2=new Interaction();
        i2.setMessageSignature(new MessageSignature(new TypeReference("M2")));
        i2.setFromRole(roleB);
        b2.add(i2);
        
        c.getPaths().add(b2);
        c.getPaths().add(b1);
        
        ProtocolModel result=ProtocolNormalizer.normalize(pm);
        
        Choice c2=(Choice)result.getProtocol().getBlock().get(0);
        
        if (c2.getPaths().get(0).equals(b1) == false) {
            fail("First block not expected");
        }
        
        if (c2.getPaths().get(1).equals(b2) == false) {
            fail("Second block not expected");
        }
    }

}
