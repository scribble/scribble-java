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

import java.util.logging.Logger;

import org.scribble.protocol.ProtocolContext;
import org.scribble.protocol.projection.ProtocolProjector;

/**
 * This class provides access to capabilities offered by the
 * protocol validator.
 *
 */
public class DefaultProtocolValidatorContext implements ProtocolValidatorContext {

    private static final Logger LOG=Logger.getLogger(DefaultProtocolValidatorContext.class.getName());
    
    private ProtocolContext _protocolContext=null;
    private ProtocolProjector _projector=null;
    private Scope _scope=new Scope();
    private java.util.List<Scope> _scopeStack=new java.util.Vector<Scope>();
    
    /**
     * This is the constructor.
     * 
     * @param pc The protocol context
     * @param pp The protocol projector
     */
    public DefaultProtocolValidatorContext(ProtocolContext pc, ProtocolProjector pp) {
        _protocolContext = pc;
        _projector = pp;
    }
    
    /**
     * {@inheritDoc}
     */
    public ProtocolContext getProtocolContext() {
        return (_protocolContext);
    }
    
    /**
     * {@inheritDoc}
     */
    public ProtocolProjector getProtocolProjector() {
        return (_projector);
    }
    
    /**
     * {@inheritDoc}
     */
    public Object getState(String name) {
        return (_scope.getState(name));
    }
    
    /**
     * {@inheritDoc}
     */
    public void setState(String name, Object value) {
        _scope.setState(name, value);
    }

    /**
     * {@inheritDoc}
     */
    public void pushState() {
        _scope.pushState();
    }
    
    /**
     * {@inheritDoc}
     */
    public void popState() {
        _scope.popState();
    }
        
    /**
     * {@inheritDoc}
     */
    public void pushScope() {
        _scopeStack.add(0, _scope);
        _scope = new Scope();
    }
    
    /**
     * {@inheritDoc}
     */
    public void popScope() {
        if (_scopeStack.size() > 0) {
            _scope = _scopeStack.remove(0);
        } else {
            LOG.severe("No state entry to pop from stack");
        }
    }
    
}
