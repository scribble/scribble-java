/*
 * Copyright 2009-11 www.scribble.org
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
package org.scribble.protocol;

import org.scribble.common.logging.Journal;
import org.scribble.common.resource.ResourceLocator;
import org.scribble.protocol.export.ProtocolExportManager;
import org.scribble.protocol.model.ProtocolImport;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.projection.ProtocolProjector;

/**
 * This interface represents the context in which a protocol related
 * tool will operate.
 *
 */
public interface ProtocolContext {

	/**
	 * This method returns the resource locator.
	 * 
	 * @return The resource locator
	 */
	public ResourceLocator getResourceLocator();
	
	/**
	 * This method retrieves a protocol model associated with a protocol
	 * import statement.
	 *  
	 * @param pi The protocol import
	 * @param journal The journal for reporting issues
	 * @return The protocol model, or null if not found
	 */
	public ProtocolModel getProtocolModel(ProtocolImport pi, Journal journal);
	
	/**
	 * This method returns the protocol projector.
	 * 
	 * @return The projector
	 */
	public ProtocolProjector getProtocolProjector();
	
	/**
	 * This method returns the protocol export manager.
	 * 
	 * @return The export manager
	 */
	public ProtocolExportManager getProtocolExportManager();
	
}
