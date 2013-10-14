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
package org.scribble.protocol.validation.rules;

import org.scribble.protocol.model.ImportDecl;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Module;
import org.scribble.protocol.model.global.GMessageTransfer;
import org.scribble.protocol.model.global.GProtocolInstance;
import org.scribble.protocol.model.local.LProtocolInstance;

/**
 * This class provides the factory capability for validation rules.
 * 
 */
public class ValidationRuleFactory {

	private static java.util.Map<Class<?>, ValidationRule> _rules=
					new java.util.HashMap<Class<?>, ValidationRule>();
	
	static {
		_rules.put(GMessageTransfer.class, new GMessageTransferValidationRule());
		_rules.put(GProtocolInstance.class, new GProtocolInstanceValidationRule());
		_rules.put(ImportDecl.class, new ImportDeclValidationRule());
		_rules.put(LProtocolInstance.class, new LProtocolInstanceValidationRule());
		_rules.put(Module.class, new ModuleValidationRule());
	}
	
	/**
	 * This method returns the validation rule associated with the
	 * supplied model object.
	 * 
	 * @param mobj The model object
	 * @return The validation rule, or null if not relevant
	 */
	public static ValidationRule getValidationRule(ModelObject mobj) {
		return (_rules.get(mobj.getClass()));
	}
}
