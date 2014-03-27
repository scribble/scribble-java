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
package org.scribble.model.local;

import org.scribble.model.ProtocolDecl;
import org.scribble.model.Role;

/**
 * This class represents the local protocol declaration.
 */
public abstract class LProtocolDecl extends ProtocolDecl {
    
	private Role _localRole=null;

    /**
     * The default constructor.
     */
    public LProtocolDecl() {
    }
    
    /**
     * This method returns the local role. This
     * field is set when the protocol represents a local
     * model.
     * 
     * @return The local role
     */
    public Role getLocalRole() {
        return (_localRole);
    }
    
    /**
     * This method sets the local role. This
     * field is set when the protocol represents a local
     * model.
     * 
     * @param role The local role
     */
    public void setLocalRole(Role role) {
        _localRole = role;
    }
    
}
