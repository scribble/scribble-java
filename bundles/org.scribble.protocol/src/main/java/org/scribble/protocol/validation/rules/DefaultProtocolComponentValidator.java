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
package org.scribble.protocol.validation.rules;

import org.scribble.protocol.validation.ProtocolComponentValidator;

/**
 * This class provides the default implementation of the protocol component
 * validator, initialized with the set of rules to validate the basic syntax
 * and semantics for the protocol model.
 *
 */
public class DefaultProtocolComponentValidator extends ProtocolComponentValidator {

	/**
	 * This is the default constructor.
	 */
	public DefaultProtocolComponentValidator() {
		getRules().add(new ChoiceValidatorRule());
		getRules().add(new InteractionValidatorRule());
		getRules().add(new RecursionValidatorRule());
		getRules().add(new RepeatValidatorRule());
	}
}
