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
package org.scribble.protocol.parser;

import org.scribble.common.logging.Journal;
import org.scribble.common.resource.Content;
import org.scribble.protocol.ProtocolContext;
import org.scribble.protocol.model.ProtocolModel;

/**
 * This interface represents the protocol parser.
 *
 */
public interface ProtocolParser {

    /**
     * This method sets an annotation processor.
     * 
     * @param ap The annotation processor
     */
    public void setAnnotationProcessor(AnnotationProcessor ap);
    
    /**
     * This method determines whether the parser supports the specified
     * content.
     * 
     * @param content The content
     * @return Whether the parser supports the content
     */
    public boolean isSupported(Content content);
    
    /**
     * This method parses the supplied input stream to create a protocol
     * model. Any issues are reported to the supplied journal. The protocol
     * context is optionally used by the parser to locate additional artifacts
     * required to construct the protocol model.
     * 
     * @param context The protocol context
     * @param content The content containing the information to be parsed
     * @param journal The journal for reporting issues
     * @return The protocol model
     * @throws java.io.IOException Failed to retrieve content to be parsed
     */
    public ProtocolModel parse(ProtocolContext context, Content content, Journal journal)
                                throws java.io.IOException;

}
