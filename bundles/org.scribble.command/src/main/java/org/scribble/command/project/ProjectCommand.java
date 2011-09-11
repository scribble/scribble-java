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
import org.scribble.protocol.DefaultProtocolContext;
import org.scribble.protocol.export.ProtocolExportManager;
import org.scribble.protocol.export.ProtocolExporter;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.parser.ProtocolParserManager;
import org.scribble.protocol.projection.ProtocolProjector;

public class ProjectCommand implements org.scribble.command.Command {

	public ProjectCommand() {	
	}
	
	public void setJournal(Journal journal) {
		m_journal = journal;
	}
	
	public void setProtocolParserManager(ProtocolParserManager parser) {
		m_protocolParserManager = parser;
	}
	
	public void setProtocolProjector(ProtocolProjector projector) {
		m_protocolProjector = projector;
	}
	
	public void setProtocolExportManager(ProtocolExportManager exportManager) {
		m_protocolExportManager = exportManager;
	}
	
	public String getName() {
		return("project");
	}
	
	public String getDescription() {
		return("Project a global protocol description to a role's local model");
	}
	
	public boolean execute(String args[]) {
		boolean ret=false;
		
		if (args.length == 2) {
			m_journal.info("PROJECT "+args[0]+" for role "+args[1], null);
			
			java.io.File f=new java.io.File(args[0]);
			
			if (f.exists() == false) {
				m_journal.error("File not found '"+args[0]+"'", null);
			} else {
				ProtocolModel model=null;
				
				try {
					Content content=new FileContent(f);
					
					model = m_protocolParserManager.parse(new DefaultProtocolContext(m_protocolParserManager,
							new DefaultResourceLocator(f.getParentFile())), content, m_journal);
			
				} catch(Exception e) {
					m_journal.error("Failed to parse file '"+args[0]+"'", null);
				}
				
				if (model != null) {
					// TODO: Need to check if model has been parsed
					
					// TODO: Need to validate that role is defined in global model
					Role role=new Role(args[1]);
					
					try {
					ProtocolModel projection=m_protocolProjector.project(model, role, m_journal,
							new DefaultProtocolContext(m_protocolParserManager,
									new DefaultResourceLocator(f.getParentFile())));
					
					if (projection != null) {
						// Get text exporter
						ProtocolExporter exporter=m_protocolExportManager.getExporter("txt");
						
						if (exporter != null) {
							exporter.export(projection, m_journal, System.out);
							
							ret = true;
						} else {
							// TODO: Report not able to find exporter
						}
					}
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			m_journal.error("Projection expects 2 parameters", null);
		}
		
		return(ret);
	}

	private Journal m_journal=null;
	private ProtocolParserManager m_protocolParserManager=null;
	private ProtocolProjector m_protocolProjector=null;
	private ProtocolExportManager m_protocolExportManager=null;
}
