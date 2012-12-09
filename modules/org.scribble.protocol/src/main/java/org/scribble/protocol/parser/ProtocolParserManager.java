/*
 * Copyright 2009-10 www.scribble.org
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
 * This interface defines the protocol parser manager, responsible
 * for parsing a specified input stream against a parser appropriate
 * for the information being parsed.
 *
 */
public interface ProtocolParserManager {
    
	/**
	 * This method determines whether there is a parser available for the supplied
	 * content.
	 * 
	 * @param content The content
	 * @return Whether a parser is available for the supplied content
	 */
	public boolean isParserAvailable(Content content);
	
    /**
     * This method parses the supplied content to create a protocol
     * model. Any issues are reported to the supplied journal. The protocol
     * context is optionally used by the parser to locate additional artifacts
     * required to construct the protocol model.
     * 
     * @param context The protocol context
     * @param content The content to be parsed
     * @param journal The journal for reporting issues
     * @return The protocol model
     * @throws java.io.IOException Failed to retrieve content to be parsed
     */
    public ProtocolModel parse(ProtocolContext context, Content content, Journal journal)
                        throws java.io.IOException;
    
    /**
     * This method returns the list of protocol parsers.
     * 
     * @return The list of protocol parsers
     */
    public java.util.List<ProtocolParser> getParsers();
    
    /**
     * This method sets the list of protocol parsers.
     * 
     * @param parsers The list of protocol parsers
     */
    public void setParsers(java.util.List<ProtocolParser> parsers);
    
}
