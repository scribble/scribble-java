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

import java.util.Collections;

import org.scribble.protocol.conformance.ConformanceHandler;
import org.scribble.protocol.model.Interaction;

/**
 * This class defines the interaction conformance rule.
 *
 */
public class InteractionConformanceRule implements ConformanceRule<Interaction> {

    @Override
    public boolean conforms(Interaction model, Interaction ref, ConformanceHandler handler) {
        boolean ret=false;
        
        if (model.getMessageSignature().equals(ref.getMessageSignature())) {
            
            // TODO: NEED TO HANDLE ROLE MAPPING!
            
            // Check roles
            if ((model.getFromRole() == null || ref.getFromRole() == null
                    || model.getFromRole().equals(ref.getFromRole())
                    && (model.getToRoles().size() == 0
                    || ref.getToRoles().size() == 0
                    || !Collections.disjoint(model.getToRoles(), ref.getToRoles())))) {
                ret = true;
            }
        }
        
        return (ret);
    }

}
