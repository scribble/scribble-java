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
package org.scribble.protocol.validation.rules;

import java.text.MessageFormat;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.Catch;
import org.scribble.protocol.model.Interaction;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Try;
import org.scribble.protocol.validation.ProtocolComponentValidatorRule;

/**
 * This class provides the validation rule for the Try
 * model component.
 *
 */
public class TryValidatorRule implements ProtocolComponentValidatorRule {

	/**
	 * This method determines whether the rule is applicable
	 * for the supplied model object.
	 * 
	 * @return Whether the rule can be used to validate the
	 * 				supplied model object
	 */
	public boolean isSupported(ModelObject obj) {
		return(obj.getClass() == org.scribble.protocol.model.Try.class);
	}
	
	/**
	 * This method validates the supplied model object.
	 * 
	 * @param obj The model object being validated
	 * @param logger The logger
	 */
	public void validate(ModelObject obj,
					Journal logger) {
		Try elem=(Try)obj;
		Interaction first=null;
		
		// Check that all catch blocks have atleast one interaction
		for (Catch c : elem.getCatches()) {
			if (c.getInteractions().size() == 0) {
				logger.error(MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle("org.scribble.protocol.Messages").
										getString("_CATCH_NO_INTERACTIONS"), (Object[])null),
										obj.getProperties());
			} else if (first == null) {
				first = c.getInteractions().get(0);
				
				if (c.getInteractions().size() > 1) {
					for (int i=1; i < c.getInteractions().size(); i++) {
						validateInteraction(first, c.getInteractions().get(i), logger);
					}
				}
			} else {
			
				// Check that the catch interactions are between the same parties
				// and in the same direction
				for (int i=0; i < c.getInteractions().size(); i++) {
					validateInteraction(first, c.getInteractions().get(i), logger);
				}
			}
		}
	}
	
	protected void validateInteraction(Interaction ref, Interaction interaction, Journal logger) {
		boolean inconsistent=false;
		
		if ((ref.getFromRole() == null && interaction.getFromRole() != null) ||
				(ref.getFromRole() != null && interaction.getFromRole() == null) ||
				(ref.getFromRole() != null && ref.getFromRole().equals(interaction.getFromRole()) == false)) {
			inconsistent = true;
		}
		
		if (ref.getToRoles().size() != interaction.getToRoles().size()) {
			inconsistent = true;
			
		} else {
			for (int i=0; inconsistent == false && i < ref.getToRoles().size(); i++) {
				if (interaction.getToRoles().contains(ref.getToRoles().get(i)) == false) {
					inconsistent = true;
				}
			}
		}
		
		if (inconsistent) {
			logger.error(MessageFormat.format(
					java.util.PropertyResourceBundle.getBundle("org.scribble.protocol.Messages").
									getString("_CATCH_INCONSISTENT_ROLES"), (Object[])null),
									interaction.getProperties());
		}
	}
}
