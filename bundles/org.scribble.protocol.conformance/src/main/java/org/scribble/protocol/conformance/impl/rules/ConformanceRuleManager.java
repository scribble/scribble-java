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
package org.scribble.protocol.conformance.impl.rules;

import org.scribble.protocol.conformance.ConformanceHandler;
import org.scribble.protocol.model.Interaction;
import org.scribble.protocol.model.ModelObject;

public class ConformanceRuleManager {
	
	private static java.util.Map<Class<? extends ModelObject>, ConformanceRule<? extends ModelObject>> m_rules=
					new java.util.HashMap<Class<? extends ModelObject>, ConformanceRule<? extends ModelObject>>();
	
	static {
		m_rules.put(Interaction.class, new InteractionConformanceRule());
	}

	public static boolean hasRule(ModelObject modelObject) {
		return(m_rules.containsKey(modelObject.getClass()));
	}
	
	public static <T extends ModelObject> boolean conforms(T model, T ref, ConformanceHandler handler) {
		boolean ret=false;
		
		@SuppressWarnings({ "unchecked" })
		ConformanceRule<T> rule=(ConformanceRule<T>)m_rules.get(model.getClass());
		
		if (rule != null) {
			ret = rule.conforms(model, ref, handler);
		}
		
		return(ret);
	}
}
