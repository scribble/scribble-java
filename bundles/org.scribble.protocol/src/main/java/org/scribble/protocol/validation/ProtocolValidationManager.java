/*
 * Copyright 2009 www.scribble.org
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

/**
 * This interface defines the protocol validation manager, responsible
 * for validating a protocol model against a set of registered
 * protocol validators.
 *
 */
public interface ProtocolValidationManager {
    
    /**
     * This method invokes the validation of the supplied
     * model against the registered validators. Any issues
     * found during validation will be reported to the
     * supplied journal.
     * 
     * @param context The protocol context
     * @param model The protocol model
     * @param journal The journal
     */
    public void validate(ProtocolContext context, org.scribble.protocol.model.ProtocolModel model,
                org.scribble.common.logging.Journal journal);
    
    /**
     * This method returns the list of protocol validators.
     * 
     * @return The list of validators
     */
    public java.util.List<ProtocolValidator> getValidators();
    
    /**
     * This method sets the list of protocol validators.
     * 
     * @param validators The list of validators
     */
    public void setValidators(java.util.List<ProtocolValidator> validators);
    
}
