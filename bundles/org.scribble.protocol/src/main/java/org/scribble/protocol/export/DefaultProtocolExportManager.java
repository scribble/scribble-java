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
package org.scribble.protocol.export;

public class DefaultProtocolExportManager implements ProtocolExportManager {
	
	/**
	 * This method returns the protocol exporter associated with
	 * the supplied id.
	 * 
	 * @param id The id
	 * @return The exporter, or null if not found
	 */
	public ProtocolExporter getExporter(String id) {
		if (m_exporters != null) {
			for (ProtocolExporter pe : m_exporters) {
				if (pe.getId().equals(id)) {
					return(pe);
				}
			}
		}
		
		return(null);
	}
	
	/**
	 * This method returns the list of exporters registered
	 * with the manager.
	 * 
	 * @return The list of exporters
	 */
	public java.util.List<ProtocolExporter> getExporters() {
		if (m_exporters == null) {
			m_exporters = new java.util.ArrayList<ProtocolExporter>();
		}
		
		return(m_exporters);
	}
	
	/**
	 * This method sets the list of exporters registered
	 * with the manager.
	 * 
	 * @param exporters The list of exporters
	 */
	public void setExporters(java.util.List<ProtocolExporter> exporters) {
		m_exporters = exporters;
	}
	
	private java.util.List<ProtocolExporter> m_exporters=null;
}
