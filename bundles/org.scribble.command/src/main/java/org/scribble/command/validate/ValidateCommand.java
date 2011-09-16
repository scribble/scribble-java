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
package org.scribble.command.validate;

import org.scribble.common.logging.Journal;
import org.scribble.common.resource.Content;
import org.scribble.common.resource.DefaultResourceLocator;
import org.scribble.common.resource.FileContent;
import org.scribble.protocol.DefaultProtocolContext;
import org.scribble.protocol.ProtocolContext;
import org.scribble.protocol.parser.ProtocolParserManager;
import org.scribble.protocol.validation.ProtocolValidationManager;

/**
 * This class implements the validation command.
 *
 */
public class ValidateCommand implements org.scribble.command.Command {

    private ProtocolValidationManager _validationManager=null;
    private Journal _journal=null;
    private ProtocolParserManager _protocolParserManager=null;

    /**
     * Default constructor.
     */
    public ValidateCommand() {
    }
    
    /**
     * This method sets the validation manager.
     * 
     * @param vm The validation manager
     */
    public void setValidationManager(ProtocolValidationManager vm) {
        _validationManager = vm;
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
        return ("validate");
    }
    
    /**
     * {@inheritDoc}
     */
    public String getDescription() {
        return ("Validate a Scribble description");
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

                    ProtocolContext context=new DefaultProtocolContext(_protocolParserManager,
                            new DefaultResourceLocator(f.getParentFile()));
                    
                    org.scribble.protocol.model.ProtocolModel model=
                        _protocolParserManager.parse(context,
                                content, _journal);
            
                    if (model != null) {
                        _journal.info("VALIDATE "+args[0], null);
                        
                        _validationManager.validate(context, model, _journal);
                        
                        ret = true;                        
                    } else {
                        _journal.error("Protocol model is null", null);
                    }
                    
                } catch (Exception e) {
                    _journal.error("Failed to parse file '"+args[0]+"'", null);
                }
            }
        } else {
            System.err.println("Validation expecting 1 parameter");
        }
        
        return (ret);
    }
}
