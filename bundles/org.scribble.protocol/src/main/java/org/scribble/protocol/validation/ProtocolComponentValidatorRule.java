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
import org.scribble.protocol.ProtocolContext;
import org.scribble.protocol.model.ModelObject;

/**
 * This interface represents the validation rule for the
 * model components.
 *
 */
public interface ProtocolComponentValidatorRule {

	/**
	 * This method determines whether the rule is applicable
	 * for the supplied model object.
	 * 
	 * @return Whether the rule can be used to validate the
	 * 				supplied model object
	 */
	public boolean isSupported(ModelObject obj);
	
	/**
	 * This method validates the supplied model object.
	 * 
	 * @param context The protocol context
	 * @param obj The model object being validated
	 * @param logger The logger
	 */
	public void validate(ProtocolContext context, ModelObject obj,
					Journal logger);

}
