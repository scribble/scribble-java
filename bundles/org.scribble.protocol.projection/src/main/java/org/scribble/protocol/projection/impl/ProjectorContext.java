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
package org.scribble.protocol.projection.impl;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.ProtocolTools;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Protocol;
import org.scribble.protocol.model.Role;

/**
 * This interface represents the comparator context.
 */
public interface ProjectorContext {
    
    /**
     * This method returns the context in which the protocol
     * is being processed.
     * 
     * @return The protocol context
     */
    public ProtocolTools getProtocolTools();
    
    /**
     * This method projects the supplied model object based on the
     * specified role.
     * 
     * @param model The model object
     * @param role The role
     * @param l The model listener
     * @return The projected model object
     */
    public Object project(ModelObject model, Role role,
                        Journal    l);
    
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
    
    /**
     * This method determines whether the context is associated
     * with the outer scope.
     * 
     * @return Whether the context is for the outer scope
     */
    public boolean isOuterScope();
    
    /**
     * This method registers an interest in the projected role
     * associated with the supplied definition.
     * 
     * @param defn The definition
     * @param role The projected role of interest
     */
    public void registerInterest(Protocol defn, Role role);
    
    /**
     * This method returns the list of roles of interest
     * for the supplied definition.
     * 
     * @param defn The definition
     * @return The list of roles, or null if no roles have
     *                 registered interest in the definition
     */
    public java.util.List<Role> getRolesOfInterestForDefinition(Protocol defn);
    
}
