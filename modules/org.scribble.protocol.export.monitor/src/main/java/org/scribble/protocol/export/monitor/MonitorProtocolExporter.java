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
package org.scribble.protocol.export.monitor;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.export.ProtocolExporter;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.monitor.model.Description;
import org.scribble.protocol.monitor.util.MonitorModelUtil;

/**
 * This implementation of the protocol exporter interface generates
 * the internal monitoring description for a protocol.
 *
 */
public class MonitorProtocolExporter implements ProtocolExporter {

    /**
     * Monitor export id.
     */
    public static final String MONITOR_ID = "monitor";
    
    private final static Logger LOG=Logger.getLogger(MonitorProtocolExporter.class.getName());
    
    private MonitorExportVisitor _visitor=new MonitorExportVisitor();

    /**
     * This method returns the id of the exporter.
     * 
     * @return The exporter id
     */
    public String getId() {
        return (MONITOR_ID);
    }
    
    /**
     * This method returns the name of the exporter for use in
     * user based selectors.
     * 
     * @return The name of the exporter
     */
    public String getName() {
        return ("Monitor");
    }
    
    /**
     * This method sets the monitor export visitor.
     * 
     * @param mev The monitor export visitor
     */
    public void setMonitorExportVisitor(MonitorExportVisitor mev) {
    	_visitor = mev;
    }
    
    /**
     * This method exports the supplied protocol model, in the implementation
     * specific format, to the specified output stream. If any issues occur
     * during the export process, they will be reported to the journal.
     * 
     * @param model The protocol model to be exported
     * @param journal The journal
     * @param os The output stream
     */
    public void export(ProtocolModel model, Journal journal, java.io.OutputStream os) {
        Description desc=generateDescription(model, journal);
        
        try {
            MonitorModelUtil.serialize(desc, os);
        } catch (Exception e) {
        	LOG.log(Level.SEVERE, "Failed to export protocol to monitor description", e);
            journal.error("Export failed due to exception: "+e, null);
        }
    }
    
    /**
     * This method generates the protocol model's monitoring description.
     * 
     * @param model The protocol model
     * @param journal The journal for reporting errors
     * @return The monitoring description
     */
    public Description generateDescription(ProtocolModel model, Journal journal) {
        _visitor.setJournal(journal);
        
        model.visit(_visitor);
        
        Description desc=_visitor.getDescription();
        
        return (desc);
    }
    
}
