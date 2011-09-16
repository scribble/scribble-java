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

/**
 * Manager for conformance rules.
 *
 */
public final class ConformanceRuleManager {
    
    private final static java.util.Map<Class<? extends ModelObject>, ConformanceRule<? extends ModelObject>> RULES=
                    new java.util.HashMap<Class<? extends ModelObject>, ConformanceRule<? extends ModelObject>>();
    
    static {
        RULES.put(Interaction.class, new InteractionConformanceRule());
    }

    /**
     * Private constructor.
     */
    private ConformanceRuleManager() {
    }
    
    /**
     * This method determines whether a rule exists for the supplied
     * model object.
     * 
     * @param modelObject The model object
     * @return Whether a rule exists
     */
    public static boolean hasRule(ModelObject modelObject) {
        return (RULES.containsKey(modelObject.getClass()));
    }
    
    /**
     * This method determines whether the supplied model and
     * reference components conform.
     * 
     * @param model The model object
     * @param ref The reference object
     * @param handler Handler for reporting conformance issues
     * @return Whether they conform
     * 
     * @param <T> Model component type
     */
    public static <T extends ModelObject> boolean conforms(T model, T ref, ConformanceHandler handler) {
        boolean ret=false;
        
        @SuppressWarnings({ "unchecked" })
        ConformanceRule<T> rule=(ConformanceRule<T>)RULES.get(model.getClass());
        
        if (rule != null) {
            ret = rule.conforms(model, ref, handler);
        }
        
        return (ret);
    }
}
