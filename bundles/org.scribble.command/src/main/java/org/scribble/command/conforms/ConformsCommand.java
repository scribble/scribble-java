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

public class ConformsCommand implements org.scribble.command.Command {

	public ConformsCommand() {	
	}
	
	public void setJournal(Journal journal) {
		m_journal = journal;
	}
	
	public void setProtocolParserManager(ProtocolParserManager parser) {
		m_protocolParserManager = parser;
	}
	
	public void setConformer(ProtocolConformer conformer) {
		m_conformer = conformer;
	}
	
	public String getName() {
		return("conforms");
	}
	
	public String getDescription() {
		return("Check two Scribble descriptions for conformance");
	}
	
	public boolean execute(String args[]) {
		boolean ret=false;
		
		if (args.length == 2) {
			java.io.File f1=new java.io.File(args[0]);
			java.io.File f2=new java.io.File(args[1]);
			
			if (f1.exists() == false) {
				m_journal.error("File not found '"+args[0]+"'", null);
			} else if (f2.exists() == false) {
				m_journal.error("File not found '"+args[1]+"'", null);
			} else {
				try {
					Content content1=new FileContent(f1);
					
					org.scribble.protocol.model.ProtocolModel p1=
						m_protocolParserManager.parse(new DefaultProtocolContext(m_protocolParserManager,
								new DefaultResourceLocator(f1.getParentFile())),
								content1, m_journal);
			
					Content content2=new FileContent(f2);
								
					org.scribble.protocol.model.ProtocolModel p2=
						m_protocolParserManager.parse(new DefaultProtocolContext(m_protocolParserManager,
								new DefaultResourceLocator(f2.getParentFile())),
								content2, m_journal);	
			
					if (p1 != null && p2 != null) {
						m_conformer.conforms(p1, p2, new LoggingConformanceHandler(m_journal));
						
						ret = true;
					}
				} catch(Exception e) {
					m_journal.error("Failed to check conformance of '"+
							args[0]+"' against '"+args[1]+"': "+e, null);
					e.printStackTrace();
				}
			}
		} else {
			m_journal.error("CONFORMS EXPECTING 2 PARAMETERS", null);
		}
		
		return(ret);
	}

	private Journal m_journal=null;
	private ProtocolParserManager m_protocolParserManager=null;
	private ProtocolConformer m_conformer=null;
}
