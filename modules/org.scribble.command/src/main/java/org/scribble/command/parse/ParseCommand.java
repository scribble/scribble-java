/*
 * Copyright 2009 www.scribble.org
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
package org.scribble.command.parse;

import org.scribble.common.logging.Journal;
import org.scribble.common.resource.Content;
import org.scribble.common.resource.FileContent;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.parser.ProtocolParserManager;

/**
 * This class implements the parse command.
 *
 */
public class ParseCommand implements org.scribble.command.Command {

    private Journal _journal=null;
    private ProtocolParserManager _protocolParserManager=null;

    /**
     * Default constructor.
     */
    public ParseCommand() {    
    }
    
    /**
     * This method sets the journal.
     * 
     * @param journal The journal
     */
    public void setJournal(Journal journal) {
        _journal = journal;
    }
    
    /**
     * This method sets the protocol parser manager.
     * 
     * @param parser The parser manager
     */
    public void setProtocolParserManager(ProtocolParserManager parser) {
        _protocolParserManager = parser;
    }
    
    /**
     * {@inheritDoc}
     */
    public String getName() {
        return ("parse");
    }
    
    /**
     * {@inheritDoc}
     */
    public String getDescription() {
        return ("Parse a Scribble description");
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean execute(String[] args) {
        boolean ret=false;
        
        if (args.length == 1) {
            _journal.info("PARSE "+args[0], null);
            
            java.io.File f=new java.io.File(args[0]);
            
            if (!f.exists()) {
                _journal.error("File not found '"+args[0]+"'", null);
            } else {
                // TODO: Check if protocol
                try {
                    Content content=new FileContent(f);

                    ProtocolModel pm=_protocolParserManager.parse(null,
                                content, _journal);
                    
                    if (pm == null) {
                        _journal.error("Protocol Model is null", null);    
                    }
            
                    ret = true;
                } catch (Exception e) {
                    _journal.error("Failed to parse file '"+args[0]+"'", null);
                }
            }
        } else {
            _journal.error("Parser expects 1 parameter", null);
        }
        
        return (ret);
    }
}
