/*
 * Copyright 2009-11 www.scribble.org
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
package org.scribble.protocol.conformance;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.ModelObject;

/**
 * This implementation records conformance related information to the logging journal.
 * 
 * WARNING: This component is still in the EXPERIMENTAL stage, so its API could change.
 */
public class LoggingConformanceHandler implements ConformanceHandler {

	private Journal m_logger=null;
	
	public static final String CONFORMANCE_MODEL_OBJECT="Conformance.Model.Object";
	public static final String CONFORMANCE_REFERENCE_PARENT="Conformance.Reference.Parent";
	public static final String CONFORMANCE_REFERENCE_INDEX="Conformance.Reference.Index";
	public static final String CONFORMANCE_REFERENCE_OBJECT="Conformance.Reference.Object";
	
	public LoggingConformanceHandler(Journal logger) {
		m_logger = logger;
	}

	public void added(ModelObject modelObject, ModelObject refParent, int index) {	
	}

	public void updated(ModelObject modelObject, ModelObject refObject) {
	}

	public void removed(ModelObject refObject) {
	}
	
}
