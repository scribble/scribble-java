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

/**
 * This interface represents a conformance rule.
 *
 * @param <T> The model type being conformance checked
 */
public interface ConformanceRule<T> {

    /**
     * This method checks the conformance of two model objects
     * of the same type.
     * 
     * @param model The model object
     * @param ref The reference object
     * @param handler Report conformance issues
     * @return Whether the model and reference objects conform
     */
    public boolean conforms(T model, T ref, ConformanceHandler handler);
    
}
