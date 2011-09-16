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
package org.scribble.protocol.util;

import org.scribble.protocol.model.ImportList;
import org.scribble.protocol.model.ProtocolImport;
import org.scribble.protocol.model.ProtocolImportList;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.model.ProtocolReference;

/**
 * This class provides utility functions related to the protocol model.
 *
 */
public final class ProtocolModelUtil {

    /**
     * Private constructor.
     */
    private ProtocolModelUtil() {
    }
    
    /**
     * This method attempts to locate a protocol import statement associated
     * with the supplied protocol reference.
     * 
     * @param model The protocol model
     * @param ref The protocol reference
     * @return The protocol import statement, or null if not found
     */
    public static ProtocolImport getProtocolImport(ProtocolModel model, ProtocolReference ref) {
        ProtocolImport ret=null;
        
        for (ImportList il : model.getImports()) {
            if (il instanceof ProtocolImportList) {
                
                for (ProtocolImport pi : ((ProtocolImportList)il).getProtocolImports()) {
                    if (pi.getName().equals(ref.getName())) {
                        ret = pi;
                        break;
                    }
                }
            }
        }
        
        return (ret);
    }
    
}
