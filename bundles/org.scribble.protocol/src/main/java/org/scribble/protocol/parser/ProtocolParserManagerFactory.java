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
package org.scribble.protocol.parser;

/**
 * This is the factory class for obtaining the parser
 * manager.
 *
 */
public final class ProtocolParserManagerFactory {
    
    private final static ProtocolParserManager INSTANCE=
            new org.scribble.protocol.parser.DefaultProtocolParserManager();

    /**
     * Private constructor.
     */
    private ProtocolParserManagerFactory() {
    }
    
    /**
     * This method returns the parser manager.
     * 
     * @return The parser manager
     */
    public static ProtocolParserManager getParserManager() {
        return (INSTANCE);
    }
}
