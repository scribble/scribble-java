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
package org.scribble.protocol.model;

import static org.junit.Assert.*;

public class ProtocolTest {

    @org.junit.Test
    public void testGetTopLevelProtocol() {
        ProtocolModel model=new ProtocolModel();
        
        Protocol top=new Protocol();
        model.setProtocol(top);
        
        Protocol inner=new Protocol();
        top.getNestedProtocols().add(inner);
        
        if (inner.getTopLevelProtocol() != top) {
            fail("Top level protocol is incorrect");
        }
    }
}
