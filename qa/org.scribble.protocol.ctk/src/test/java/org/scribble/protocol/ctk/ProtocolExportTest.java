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
package org.scribble.protocol.ctk;

import static org.junit.Assert.*;

import org.scribble.protocol.model.*;

public class ProtocolExportTest {
    
    @org.junit.Test
    public void testRunSubProtocol() {
        TestJournal logger=new TestJournal();
        
        String filename="tests/protocol/global/RunSubProtocol.spr";
        
        ProtocolModel model=CTKUtil.getModel(filename, logger);
        
        assertNotNull(model);
        
        assertTrue(logger.getErrorCount() == 0);
        
        String protocol=CTKUtil.getModelText(filename);
        
        org.scribble.protocol.export.text.TextProtocolExporter exporter=
                new org.scribble.protocol.export.text.TextProtocolExporter();
            
        java.io.ByteArrayOutputStream os=new java.io.ByteArrayOutputStream();
            
        exporter.export(model, logger, os);
        
        try {
            os.close();
        } catch(Exception e) {
            fail("Failed to close stream");
        }
        
        String str=os.toString();
        
        str = str.replaceAll("\r\n", "\n");
        
        if (!str.equals(protocol)) {
            System.out.println("ORIGINAL="+protocol);
            System.out.println("GENERATED="+str);
            fail("Exported text is not same as original protocol");
        }
    }
    
}
