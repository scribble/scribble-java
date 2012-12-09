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
package org.scribble.protocol.projection.impl;

import java.util.logging.Logger;

/**
 * This class represents information associated with a scope.
 */
public class Scope {
    
    private final static Logger LOG = Logger.getLogger(Scope.class.getName());
    
    private String _locatedRole=null;
    private java.util.List<java.util.Map<String,Object>> _stateStack=
        new java.util.Vector<java.util.Map<String,Object>>();
    private java.util.Map<String,Object> _properties=new java.util.HashMap<String, Object>();

    /**
     * The default constructor for the scope.
     */
    public Scope() {
        // Initialize the state
        pushState();
    }
    
    /**
     * This is the copy constructor for the scope.
     * 
     * @param copy The copy
     */
    public Scope(Scope copy) {
        _locatedRole = copy._locatedRole;
        
        for (int i=0; i < copy._stateStack.size(); i++) {
            java.util.Map<String,Object> current=copy._stateStack.get(i);
            
            java.util.Map<String,Object> copyStackEntry=
                        new java.util.Hashtable<String,Object>();
        
            java.util.Iterator<String> iter=current.keySet().iterator();
            
            while (iter.hasNext()) {
                String key=iter.next();
                Object value=current.get(key);
                
                copyStackEntry.put(key, value);
            }
            
            _stateStack.add(copyStackEntry);
        }
    }
    
    /**
     * This method returns the named state from the current
     * scope.
     * 
     * @param name The state name
     * @return The state value, or null if not found
     */
    public Object getState(String name) {
        Object ret=null;
        
        if (_stateStack.size() > 0) {
            for (int i=0; ret == null && i < _stateStack.size(); i++) {
                java.util.Map<String,Object> current=_stateStack.get(i);
                ret = current.get(name);
            }
        }
        
        return (ret);
    }
    
    /**
     * This method sets the value associated with the supplied
     * name in the current state scope.
     * 
     * @param name The state name
     * @param value The state value
     */
    public void setState(String name, Object value) {
        if (_stateStack.size() > 0) {
            java.util.Map<String,Object> current=_stateStack.get(0);
            current.put(name, value);
        }
    }

    /**
     * This method returns the located role associated with the
     * current scope.
     * 
     * @return The located role
     */
    public String getLocatedParticipant() {
        return (_locatedRole);
    }
    
    /**
     * This method sets the located role associated with the
     * current scope.
     * 
     * @param located The located role
     */
    public void setLocatedParticipant(String located) {
        _locatedRole = located;
    }

    /**
     * This method pushes the current state onto a stack.
     */
    public void pushState() {
        _stateStack.add(0, new java.util.Hashtable<String,Object>());
    }
    
    /**
     * This method pops the current state from the stack.
     */
    public void popState() {
        if (_stateStack.size() > 0) {
            _stateStack.remove(0);
        } else {
            LOG.severe("No state entry to pop from stack");
        }
    }
    
    /**
     * This method returns the properties associated with
     * the scope.
     * 
     * @return The properties
     */
    public java.util.Map<String,Object> getProperties() {
        return (_properties);
    }
}
