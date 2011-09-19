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
package org.scribble.command.project;

import org.scribble.common.logging.Journal;
import org.scribble.common.resource.Content;
import org.scribble.common.resource.DefaultResourceLocator;
import org.scribble.common.resource.FileContent;
import org.scribble.protocol.DefaultProtocolTools;
import org.scribble.protocol.ProtocolTools;
import org.scribble.protocol.export.ProtocolExportManager;
import org.scribble.protocol.export.ProtocolExporter;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.parser.ProtocolParserManager;
import org.scribble.protocol.projection.ProtocolProjector;

/**
 * This class implements the projection command.
 *
 */
public class ProjectCommand implements org.scribble.command.Command {

    private Journal _journal=null;
    private ProtocolParserManager _protocolParserManager=null;
    private ProtocolProjector _protocolProjector=null;
    private ProtocolExportManager _protocolExportManager=null;

    /**
     * Default constructor.
     */
    public ProjectCommand() {    
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
     * This method sets the protocol projector.
     * 
     * @param projector The protocol projector
     */
    public void setProtocolProjector(ProtocolProjector projector) {
        _protocolProjector = projector;
    }
    
    /**
     * This method sets the export manager.
     * 
     * @param exportManager The export manager
     */
    public void setProtocolExportManager(ProtocolExportManager exportManager) {
        _protocolExportManager = exportManager;
    }
    
    /**
     * {@inheritDoc}
     */
    public String getName() {
        return ("project");
    }
    
    /**
     * {@inheritDoc}
     */
    public String getDescription() {
        return ("Project a global protocol description to a role's local model");
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean execute(String[] args) {
        boolean ret=false;
        
        if (args.length == 2) {
            _journal.info("PROJECT "+args[0]+" for role "+args[1], null);
            
            java.io.File f=new java.io.File(args[0]);
            
            if (!f.exists()) {
                _journal.error("File not found '"+args[0]+"'", null);
            } else {
                ProtocolModel model=null;
                
                try {
                    Content content=new FileContent(f);
                    
                    model = _protocolParserManager.parse(new DefaultProtocolTools(_protocolParserManager,
                            new DefaultResourceLocator(f.getParentFile())), content, _journal);
            
                } catch (Exception e) {
                    _journal.error("Failed to parse file '"+args[0]+"'", null);
                }
                
                if (model != null) {
                    // TODO: Need to check if model has been parsed
                    
                    // TODO: Need to validate that role is defined in global model
                    Role role=new Role(args[1]);
                    
                    try {
                        ProtocolTools context=new DefaultProtocolTools(_protocolParserManager,
                                new DefaultResourceLocator(f.getParentFile()));
                        
                        ProtocolModel projection=_protocolProjector.project(context,
                                        model, role, _journal);
                    
                        if (projection != null) {
                            // Get text exporter
                            ProtocolExporter exporter=_protocolExportManager.getExporter("txt");
                            
                            if (exporter != null) {
                                exporter.export(projection, _journal, System.out);
                                
                                ret = true;
                            } else {
                                _journal.error("Unable to find text exporter", null);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            _journal.error("Projection expects 2 parameters", null);
        }
        
        return (ret);
    }
}
