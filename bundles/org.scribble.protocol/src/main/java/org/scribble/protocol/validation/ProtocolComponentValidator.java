/*
 * Copyright 2009 www.scribble.org
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

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.*;
import org.scribble.protocol.validation.rules.*;

//import java.util.logging.*;

/**
 * This class provides an implementation of the ProtocolValidator
 * interface. It enables individual validation rules, associated with
 * specific model components, to be registered and invoked when
 * validating a protocol model.
 */
public class ProtocolComponentValidator implements ProtocolValidator {
	
	private java.util.List<ProtocolComponentValidatorRule> m_rules=
					new java.util.Vector<ProtocolComponentValidatorRule>();

	//private static Logger _log=Logger.getLogger(ProtocolComponentValidator.class.getName());
	
	public ProtocolComponentValidator() {
	}
	
	protected void register(ProtocolComponentValidatorRule rule) {
		m_rules.add(rule);
	}

	protected void unregister(ProtocolComponentValidatorRule rule) {
		m_rules.remove(rule);
	}
	
	protected java.util.List<ProtocolComponentValidatorRule> getRules() {
		return(m_rules);
	}
	
	public void validate(org.scribble.protocol.model.ProtocolModel model,
						Journal logger) {
		
		model.visit(new ValidatingVisitor(logger));
	}

	public class ValidatingVisitor extends org.scribble.protocol.model.AbstractModelObjectVisitor {
		
		public ValidatingVisitor(Journal logger) {
			m_logger = logger;
		}
		
		/**
		 * This method can be implemented to process all of the model
		 * objects within a particular protocol model.
		 * 
		 * @param obj The model object
		 */
		public boolean process(ModelObject obj) {
			
			// Iterate through rules processing those that support the supplied
			// model object
			java.util.Iterator<ProtocolComponentValidatorRule> iter=getRules().iterator();
			
			while (iter.hasNext()) {
				ProtocolComponentValidatorRule rule=iter.next();
				
				if (rule.isSupported(obj)) {
					rule.validate(obj, m_logger);
				}
			}
			
			return(true);
		}
		
		private Journal m_logger=null;
	}
}
