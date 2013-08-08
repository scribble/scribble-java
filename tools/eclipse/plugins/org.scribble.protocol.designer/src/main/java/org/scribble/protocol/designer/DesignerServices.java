/*
 * Copyright 2009 www.scribble.org
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
package org.scribble.protocol.designer;

import org.scribble.protocol.parser.ProtocolParser;

/**
 * This class provides a manager for accessing services used
 * by the designer.
 *
 */
public final class DesignerServices {

    /**
     * Protocol file extension.
     */
    public static final String PROTOCOL_FILE_EXTENSION = "scr";

    private static ProtocolParser _parser=null;

    /**
     * Private constructor.
     */
    private DesignerServices() {
    }
    
     /**
     * This method returns the parser.
     * 
     * @return The parser
     */
    public static ProtocolParser getProtocolParser() {
        return (_parser);
    }
    
    /**
     * This method sets the parser.
     * 
     * @param pp The parser
     */
    public static void setProtocolParser(ProtocolParser pp) {
        _parser = pp;
    }
    
}
