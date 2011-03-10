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
package org.scribble.protocol.validation;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the default implementation of the ProtocolValidationManager
 * interface.
 *
 */
public class DefaultProtocolValidationManager implements ProtocolValidationManager {
	
	private java.util.List<ProtocolValidator> m_validators=
							new java.util.Vector<ProtocolValidator>();
	
	private static final Logger logger=Logger.getLogger(DefaultProtocolValidationManager.class.getName());
	
	/**
	 * This is the default constructor.
	 */
	public DefaultProtocolValidationManager() {
	}
	
	/**
	 * This method adds a protocol validator to the manager.
	 * 
	 * @param validator The protocol validator
	 */
	public void addValidator(ProtocolValidator validator) {
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Register validator: "+validator);
		}
		m_validators.add(validator);
	}

	/**
	 * This method removes a protocol validator to the manager.
	 * 
	 * @param validator The protocol validator
	 */
	public void removeValidator(ProtocolValidator validator) {
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Unregister validator: "+validator);
		}
		m_validators.remove(validator);
	}

	/**
	 * This method invokes the validation of the supplied
	 * model against the registered validators. Any issues
	 * found during validation will be reported to the
	 * supplied journal.
	 * 
	 * @param model The protocol model
	 * @param journal The journal
	 */
	public void validate(org.scribble.protocol.model.ProtocolModel model,
				org.scribble.common.logging.Journal logger) {
	
		for (ProtocolValidator v : m_validators) {
			v.validate(model, logger);
		}
	}
}
