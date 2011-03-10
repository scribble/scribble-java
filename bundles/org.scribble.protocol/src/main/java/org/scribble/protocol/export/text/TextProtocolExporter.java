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
package org.scribble.protocol.export.text;

import java.text.MessageFormat;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.export.ProtocolExporter;
import org.scribble.protocol.model.*;

public class TextProtocolExporter implements ProtocolExporter {

	public static final String TEXT_ID = "txt";

	/**
	 * This method returns the id of the exporter.
	 * 
	 * @return The exporter id
	 */
	public String getId() {
		return(TEXT_ID);
	}
	
	/**
	 * This method returns the name of the exporter for use in
	 * user based selectors.
	 * 
	 * @return The name of the exporter
	 */
	public String getName() {
		return("Text");
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
		TextProtocolExporterVisitor visitor=createVisitor(journal, os);
		
		model.visit(visitor);
		
		if (visitor.getException() != null) {
			journal.error(MessageFormat.format(
					java.util.PropertyResourceBundle.getBundle(
							"org.scribble.protocol.Messages").getString("_EXPORT_FAILED"),
								visitor.getException().getLocalizedMessage()), null);
		}
	}
	
	/**
	 * This method creates the text protocol export visitor.
	 * 
	 * @param journal The journal
	 * @param os The output stream
	 * @return The visitor
	 */
	protected TextProtocolExporterVisitor createVisitor(Journal journal, java.io.OutputStream os) {
		return(new TextProtocolExporterVisitor(journal, os));
	}
}
