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
package org.scribble.validation.rules;

import org.scribble.model.ImportDecl;
import org.scribble.model.Message;
import org.scribble.model.ModelObject;
import org.scribble.model.Module;
import org.scribble.model.global.GBlock;
import org.scribble.model.global.GChoice;
import org.scribble.model.global.GDo;
import org.scribble.model.global.GInterruptible;
import org.scribble.model.global.GMessageTransfer;
import org.scribble.model.global.GParallel;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.model.global.GProtocolInstance;
import org.scribble.model.global.GRecursion;
import org.scribble.model.local.LBlock;
import org.scribble.model.local.LChoice;
import org.scribble.model.local.LDo;
import org.scribble.model.local.LInterruptible;
import org.scribble.model.local.LParallel;
import org.scribble.model.local.LProtocolDefinition;
import org.scribble.model.local.LProtocolInstance;
import org.scribble.model.local.LReceive;
import org.scribble.model.local.LRecursion;
import org.scribble.model.local.LSend;

/**
 * This class provides the factory capability for validation rules.
 * 
 */
public class ValidationRuleFactory {

	private static java.util.Map<Class<?>, ValidationRule> _rules=
					new java.util.HashMap<Class<?>, ValidationRule>();
	
	static {
		_rules.put(GBlock.class, new GBlockValidationRule());
		_rules.put(GChoice.class, new GChoiceValidationRule());
		_rules.put(GDo.class, new GDoValidationRule());
		_rules.put(GInterruptible.class, new GInterruptibleValidationRule());
		_rules.put(GMessageTransfer.class, new GMessageTransferValidationRule());
		_rules.put(GParallel.class, new GParallelValidationRule());
		_rules.put(GProtocolDefinition.class, new GProtocolDefinitionValidationRule());
		_rules.put(GProtocolInstance.class, new GProtocolInstanceValidationRule());
		_rules.put(GRecursion.class, new GRecursionValidationRule());
		_rules.put(ImportDecl.class, new ImportDeclValidationRule());
		_rules.put(LBlock.class, new LBlockValidationRule());
		_rules.put(LChoice.class, new LChoiceValidationRule());
		_rules.put(LDo.class, new LDoValidationRule());
		_rules.put(LInterruptible.class, new LInterruptibleValidationRule());
		_rules.put(LParallel.class, new LParallelValidationRule());
		_rules.put(LProtocolDefinition.class, new LProtocolDefinitionValidationRule());
		_rules.put(LProtocolInstance.class, new LProtocolInstanceValidationRule());
		_rules.put(LReceive.class, new LReceiveValidationRule());
		_rules.put(LRecursion.class, new LRecursionValidationRule());
		_rules.put(LSend.class, new LSendValidationRule());
		_rules.put(Message.class, new MessageValidationRule());
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
