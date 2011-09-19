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
package org.scribble.command.simulate;

import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.scribble.common.logging.Journal;
import org.scribble.common.resource.Content;
import org.scribble.common.resource.DefaultResourceLocator;
import org.scribble.common.resource.FileContent;
import org.scribble.protocol.DefaultProtocolTools;
import org.scribble.protocol.export.ProtocolExportManager;
import org.scribble.protocol.monitor.DefaultMonitorContext;
import org.scribble.protocol.monitor.DefaultSession;
import org.scribble.protocol.monitor.ProtocolMonitor;
import org.scribble.protocol.monitor.Session;
import org.scribble.protocol.parser.ProtocolParserManager;

/**
 * This class implements the simulation command.
 *
 */
public class SimulateCommand implements org.scribble.command.Command {

    private ProtocolMonitor _protocolMonitor=null;
    private ProtocolExportManager _protocolExportManager=null;
    private Journal _journal=null;
    private ProtocolParserManager _protocolParserManager=null;
    
    private final static Logger LOG=Logger.getLogger(SimulateCommand.class.getName());

    /**
     * Default constructor.
     */
    public SimulateCommand() {
    }
    
    /**
     * This method sets the protocol monitor.
     * 
     * @param pm The protocol monitor
     */
    public void setProtocolMonitor(ProtocolMonitor pm) {
        _protocolMonitor = pm;
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
        return ("simulate");
    }
    
    /**
     * {@inheritDoc}
     */
    public String getDescription() {
        return ("Simulate a Scribble endpoint description against a list of events");
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean execute(String[] args) {
        boolean ret=false;
        
        if (args.length == 2) {
            _journal.info("SIMULATE "+args[0]+" "+args[1], null);
            
            java.io.File f1=new java.io.File(args[0]);
            java.io.File f2=new java.io.File(args[1]);
            
            if (!f1.exists()) {
                _journal.error("File not found '"+args[0]+"'", null);
            } else if (!f2.exists()) {
                _journal.error("File not found '"+args[1]+"'", null);                
            } else {
                // TODO: Check if protocol
                try {
                    Content content=new FileContent(f1);
                    
                    org.scribble.protocol.model.ProtocolModel model=
                            _protocolParserManager.parse(new DefaultProtocolTools(_protocolParserManager,
                                    new DefaultResourceLocator(f1.getParentFile())),
                                    content, _journal);
            
                    if (model != null) {
                        
                        if (model.getProtocol().getLocatedRole() != null) {
                            
                            simulate(model, f2);
                        
                            ret = true;        
                        } else {
                            _journal.error("Protocol is not located at a role - "
                                    +"only located protocols can be simulated", null);
                        }
                    } else {
                        _journal.error("Protocol model not retrieved", null);
                    }
                    
                } catch (Exception e) {
                    _journal.error("Failed to parse file '"+args[0]+"'", null);
                    e.printStackTrace();
                }
            }
        } else {
            System.err.println("Simulation expects 2 parameters");
        }
        
        return (ret);
    }
    
    /**
     * This method simulates the protocol model against the specified event file.
     * 
     * @param model The protocol model
     * @param eventFile The event file
     */
    public void simulate(org.scribble.protocol.model.ProtocolModel model, java.io.File eventFile) {
        // Generate internal representation for monitor
        org.scribble.protocol.export.ProtocolExporter exporter=
                        _protocolExportManager.getExporter("monitor");
        
        if (exporter != null) {
            ByteArrayOutputStream os=new ByteArrayOutputStream();
            
            try {
                exporter.export(model, _journal, os);
                
                java.io.InputStream prtis=new java.io.ByteArrayInputStream(os.toByteArray());
                
                org.scribble.protocol.monitor.model.Description protocol=
                        org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(prtis);
                
                prtis.close();
                
                if (LOG.isLoggable(Level.FINE)) {
                    LOG.fine("Protocol = "+protocol);
                }
                
                DefaultMonitorContext context=new DefaultMonitorContext();
                
                Session conv=_protocolMonitor.createSession(context, protocol, DefaultSession.class);
                
                java.io.InputStream is=new java.io.FileInputStream(eventFile);
                
                EventProcessor eventProcessor=new EventProcessor();
                
                eventProcessor.initialize(is);
                
                is.close();
                
                for (Event event : eventProcessor.getEvents()) {
                    if (event.validate(_protocolMonitor, context, protocol, conv).isValid()) {
                        _journal.info("Validated "+event, null);
                    } else {
                        _journal.error(event.toString(), null);
                    }
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                _journal.error("Failed to simulate process model: "+e, null);
            }
        } else {
            _journal.error("Failed to export protocol model to monitoring representation", null);
        }
    }
}
