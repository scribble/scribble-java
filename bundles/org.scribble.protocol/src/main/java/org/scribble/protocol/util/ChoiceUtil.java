/*
 * Copyright 2009-10 www.scribble.org
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

import org.scribble.protocol.model.MessageSignature;
import org.scribble.protocol.model.TypeReference;

/**
 * Utility functions for the choice construct.
 *
 */
public final class ChoiceUtil {
    
    private static final String LABEL_SEPARATOR = "_";

    /**
     * Private constructor.
     */
    private ChoiceUtil() {
    }

    /**
     * This method returns the label associated with the message signature.
     * 
     * @param sig The message signature
     * @return The label
     */
    public static String getLabel(MessageSignature sig) {
        StringBuffer ret=new StringBuffer();
        
        if (sig.getOperation() != null) {
            ret.append(sig.getOperation());
        }
        
        for (TypeReference tref : sig.getTypeReferences()) {            
            ret.append(LABEL_SEPARATOR);
            ret.append(tref.getName());
        }
        
        return (ret.toString());
    }
}
