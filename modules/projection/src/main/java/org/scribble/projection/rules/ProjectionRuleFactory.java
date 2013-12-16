/*
 * Copyright 2009-14 www.scribble.org
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
package org.scribble.projection.rules;

import org.scribble.model.ModelObject;
import org.scribble.model.Module;
import org.scribble.model.global.GBlock;
import org.scribble.model.global.GChoice;
import org.scribble.model.global.GContinue;
import org.scribble.model.global.GDo;
import org.scribble.model.global.GInterruptible;
import org.scribble.model.global.GMessageTransfer;
import org.scribble.model.global.GParallel;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.model.global.GProtocolInstance;
import org.scribble.model.global.GRecursion;

/**
 * This class provides the factory capability for projection rules.
 * 
 */
public class ProjectionRuleFactory {

	private static java.util.Map<Class<?>, ProjectionRule> _rules=
					new java.util.HashMap<Class<?>, ProjectionRule>();
	
	static {
		_rules.put(GBlock.class, new GBlockProjectionRule());
		_rules.put(GDo.class, new GDoProjectionRule());
		_rules.put(GChoice.class, new GChoiceProjectionRule());
		_rules.put(GContinue.class, new GContinueProjectionRule());
		_rules.put(GInterruptible.class, new GInterruptibleProjectionRule());
		_rules.put(GMessageTransfer.class, new GMessageTransferProjectionRule());
		_rules.put(GParallel.class, new GParallelProjectionRule());
		_rules.put(GProtocolDefinition.class, new GProtocolDefinitionProjectionRule());
		_rules.put(GProtocolInstance.class, new GProtocolInstanceProjectionRule());
		_rules.put(GRecursion.class, new GRecursionProjectionRule());
		_rules.put(Module.class, new ModuleProjectionRule());
	}
	
	/**
	 * This method returns the projection rule associated with the
	 * supplied model object.
	 * 
	 * @param mobj The model object
	 * @return The projection rule, or null if not relevant
	 */
	public static ProjectionRule getProjectionRule(ModelObject mobj) {
		return (_rules.get(mobj.getClass()));
	}
}
