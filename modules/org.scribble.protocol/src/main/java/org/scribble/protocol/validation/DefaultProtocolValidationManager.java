/*
 * Copyright 2009 www.scribble.org
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
package org.scribble.protocol.validation;

import org.scribble.protocol.ProtocolContext;
import org.scribble.protocol.projection.ProtocolProjector;

/**
 * This class represents the default implementation of the ProtocolValidationManager
 * interface.
 *
 */
public class DefaultProtocolValidationManager implements ProtocolValidationManager {
    
    private java.util.List<ProtocolValidator> _validators=
                            new java.util.Vector<ProtocolValidator>();
    private ProtocolProjector _projector=null;
    
    /**
     * This is the default constructor.
     */
    public DefaultProtocolValidationManager() {
    }
    
    /**
     * This method sets the protocol projector.
     * 
     * @param pp The protocol projector
     */
    public void setProtocolProjector(ProtocolProjector pp) {
        _projector = pp;
    }
    
    /**
     * This method gets the protocol projector.
     * 
     * @return The protocol projector
     */
    public ProtocolProjector getProtocolProjector() {
        return (_projector);
    }
    
    /**
    /**
     * This method invokes the validation of the supplied
     * model against the registered validators. Any issues
     * found during validation will be reported to the
     * supplied journal.
     * 
     * @param pc The protocol context
     * @param model The protocol model
     * @param journal The journal
     */
    public void validate(ProtocolContext pc, org.scribble.protocol.model.ProtocolModel model,
                org.scribble.common.logging.Journal journal) {
    
        if (_validators != null) {
            ProtocolValidatorContext pvc=new DefaultProtocolValidatorContext(pc, _projector);
            
            for (ProtocolValidator v : _validators) {
                v.validate(pvc, model, journal);
            }
        }
    }
    
    /**
     * This method returns the list of protocol validators.
     * 
     * @return The list of validators
     */
    public java.util.List<ProtocolValidator> getValidators() {
        if (_validators == null) {
            _validators = new java.util.ArrayList<ProtocolValidator>();
        }
        
        return (_validators);
    }
    
    /**
     * This method sets the list of protocol validators.
     * 
     * @param validators The list of validators
     */
    public void setValidators(java.util.List<ProtocolValidator> validators) {
        _validators = validators;
    }
}
