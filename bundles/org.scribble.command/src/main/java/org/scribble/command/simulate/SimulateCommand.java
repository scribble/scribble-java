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

import org.scribble.common.logging.*;
import org.scribble.common.resource.DefaultResourceLocator;
import org.scribble.protocol.DefaultProtocolContext;
import org.scribble.protocol.export.ProtocolExportManager;
import org.scribble.protocol.parser.ProtocolParserManager;
import org.scribble.protocol.monitor.DefaultSession;
import org.scribble.protocol.monitor.DefaultMonitorContext;
import org.scribble.protocol.monitor.ProtocolMonitor;

public class SimulateCommand implements org.scribble.command.Command {

	private ProtocolMonitor m_protocolMonitor=null;
	private ProtocolExportManager m_protocolExportManager=null;
	private Journal m_journal=null;
	private ProtocolParserManager m_protocolParserManager=null;
	
	private static Logger _log=Logger.getLogger(SimulateCommand.class.getName());

	public SimulateCommand() {
	}
	
	public void setProtocolMonitor(ProtocolMonitor pm) {
		m_protocolMonitor = pm;
	}
	
	public void setJournal(Journal journal) {
		m_journal = journal;
	}
	
	public void setProtocolParserManager(ProtocolParserManager parser) {
		m_protocolParserManager = parser;
	}
	
	public void setProtocolExportManager(ProtocolExportManager exportManager) {
		m_protocolExportManager = exportManager;
	}
	
	public String getName() {
		return("simulate");
	}
	
	public String getDescription() {
		return("Simulate a Scribble endpoint description against a list of events");
	}
	
	public boolean execute(String args[]) {
		boolean ret=false;
		
		if (args.length == 2) {
			m_journal.info("SIMULATE "+args[0]+" "+args[1], null);
			
			java.io.File f1=new java.io.File(args[0]);
			java.io.File f2=new java.io.File(args[1]);
			
			if (f1.exists() == false) {
				m_journal.error("File not found '"+args[0]+"'", null);
			} else if (f2.exists() == false) {
				m_journal.error("File not found '"+args[1]+"'", null);				
			} else {
				// TODO: Check if protocol
				try {
					java.io.InputStream is=new java.io.FileInputStream(f1);
			
					int index=f1.getName().lastIndexOf('.');
					String sourceType=null;
					
					if (index != -1) {
						sourceType = f1.getName().substring(index+1);
					}
			
					org.scribble.protocol.model.ProtocolModel model=
							m_protocolParserManager.parse(sourceType, is, m_journal,
									new DefaultProtocolContext(m_protocolParserManager,
									new DefaultResourceLocator(f1.getParentFile())));
			
					if (model != null) {
						
						if (model.getProtocol().getRole() != null) {
							
							simulate(model, f2);
						
							ret = true;		
						} else {
							m_journal.error("Protocol is not located at a role - " +
									"only located protocols can be simulated", null);
						}
					} else {
						m_journal.error("Protocol model not retrieved", null);
					}
					
					is.close();
					
				} catch(Exception e) {
					m_journal.error("Failed to parse file '"+args[0]+"'", null);
					e.printStackTrace();
				}
			}
		} else {
			System.err.println("SIMULATE EXPECTING 2 PARAMETERS");
		}
		
		return(ret);
	}
	
	public void simulate(org.scribble.protocol.model.ProtocolModel model, java.io.File eventFile) {
		// Generate internal representation for monitor
		org.scribble.protocol.export.ProtocolExporter exporter=
						m_protocolExportManager.getExporter("monitor");
		
		if (exporter != null) {
			ByteArrayOutputStream os=new ByteArrayOutputStream();
			
			try {
				exporter.export(model, m_journal, os);
				
				java.io.InputStream prtis=new java.io.ByteArrayInputStream(os.toByteArray());
				
				org.scribble.protocol.monitor.model.Description protocol=
						org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(prtis);
				
				prtis.close();
				
				if (_log.isLoggable(Level.FINE)) {
					_log.fine("Protocol = "+protocol);
				}
				
				DefaultMonitorContext context=new DefaultMonitorContext();
				
				DefaultSession conv=new DefaultSession();
				m_protocolMonitor.initialize(context, protocol, conv);
				
				java.io.InputStream is=new java.io.FileInputStream(eventFile);
				
				EventProcessor eventProcessor=new EventProcessor();
				
				eventProcessor.initialize(is);
				
				is.close();
				
				for (Event event : eventProcessor.getEvents()) {
					if (event.validate(m_protocolMonitor, context, protocol, conv).isValid()) {
						m_journal.info("Validated "+event, null);
					} else {
						m_journal.error(event.toString(), null);
					}
				}
				
			} catch(Exception e) {
				e.printStackTrace();
				m_journal.error("Failed to simulate process model: "+e, null);
			}
		} else {
			m_journal.error("Failed to export protocol model to monitoring representation", null);
		}
	}
}
