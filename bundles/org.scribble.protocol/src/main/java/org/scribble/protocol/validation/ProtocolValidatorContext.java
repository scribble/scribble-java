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
package org.scribble.protocol.validation;

import org.scribble.protocol.ProtocolContext;
import org.scribble.protocol.projection.ProtocolProjector;

/**
 * This interface provides access to capabilities offered by the
 * protocol validator.
 *
 */
public interface ProtocolValidatorContext {

    /**
     * This method returns the context in which the protocol
     * is being processed.
     * 
     * @return The protocol context
     */
    public ProtocolContext getProtocolContext();
    
    /**
     * This method returns the protocol projector.
     * 
     * @return The protocol projector
     */
    public ProtocolProjector getProtocolProjector();
    
    /**
     * This method returns the named state from the current
     * scope.
     * 
     * @param name The state name
     * @return The state value, or null if not found
     */
    public Object getState(String name);
    
    /**
     * This method sets the value associated with the supplied
     * name in the current state.
     * 
     * @param name The state name
     * @param value The state value
     */
    public void setState(String name, Object value);
    
    /**
     * This method pushes the current state onto a stack.
     */
    public void pushState();
    
    /**
     * This method pops the current state from the stack.
     */
    public void popState();
    
    /**
     * This method pushes the current scope onto a stack.
     */
    public void pushScope();
    
    /**
     * This method pops the current scope from the stack.
     */
    public void popScope();
    
}
