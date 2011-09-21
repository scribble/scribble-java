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
import org.scribble.protocol.validation.ProtocolValidatorContext;

/**
 * This class provides access to capabilities offered by the
 * protocol validator.
 *
 */
public class DefaultProtocolValidatorContext implements ProtocolValidatorContext {

    private ProtocolContext _protocolContext=null;
    private ProtocolProjector _projector=null;
    
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
        return(_protocolContext);
    }
    
    /**
     * {@inheritDoc}
     */
    public ProtocolProjector getProtocolProjector() {
        return(_projector);
    }
    
}
