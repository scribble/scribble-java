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
package org.scribble.command.conforms;

import org.scribble.common.logging.Journal;
import org.scribble.common.resource.Content;
import org.scribble.common.resource.DefaultResourceLocator;
import org.scribble.common.resource.FileContent;
import org.scribble.protocol.DefaultProtocolContext;
import org.scribble.protocol.conformance.LoggingConformanceHandler;
import org.scribble.protocol.conformance.ProtocolConformer;
import org.scribble.protocol.parser.ProtocolParserManager;

/**
 * This class implements the conformance checking command.
 *
 */
public class ConformsCommand implements org.scribble.command.Command {

    private Journal _journal=null;
    private ProtocolParserManager _protocolParserManager=null;
    private ProtocolConformer _conformer=null;

    /**
     * Default constructor.
     */
    public ConformsCommand() {    
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
     * This method sets the protocol conformer.
     * 
     * @param conformer The protocol conformer
     */
    public void setConformer(ProtocolConformer conformer) {
        _conformer = conformer;
    }
    
    /**
     * {@inheritDoc}
     */
    public String getName() {
        return ("conforms");
    }
    
    /**
     * {@inheritDoc}
     */
    public String getDescription() {
        return ("Check two Scribble descriptions for conformance");
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean execute(String[] args) {
        boolean ret=false;
        
        if (args.length == 2) {
            java.io.File f1=new java.io.File(args[0]);
            java.io.File f2=new java.io.File(args[1]);
            
            if (!f1.exists()) {
                _journal.error("File not found '"+args[0]+"'", null);
            } else if (!f2.exists()) {
                _journal.error("File not found '"+args[1]+"'", null);
            } else {
                try {
                    Content content1=new FileContent(f1);
                    
                    org.scribble.protocol.model.ProtocolModel p1=
                        _protocolParserManager.parse(new DefaultProtocolContext(_protocolParserManager,
                                new DefaultResourceLocator(f1.getParentFile())),
                                content1, _journal);
            
                    Content content2=new FileContent(f2);
                                
                    org.scribble.protocol.model.ProtocolModel p2=
                        _protocolParserManager.parse(new DefaultProtocolContext(_protocolParserManager,
                                new DefaultResourceLocator(f2.getParentFile())),
                                content2, _journal);    
            
                    if (p1 != null && p2 != null) {
                        _conformer.conforms(p1, p2, new LoggingConformanceHandler(_journal));
                        
                        ret = true;
                    }
                } catch (Exception e) {
                    _journal.error("Failed to check conformance of '"
                           +args[0]+"' against '"+args[1]+"': "+e, null);
                    e.printStackTrace();
                }
            }
        } else {
            _journal.error("CONFORMS EXPECTING 2 PARAMETERS", null);
        }
        
        return (ret);
    }
}
