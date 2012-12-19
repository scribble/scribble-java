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
package org.scribble.protocol.model.local;

import org.scribble.protocol.model.Role;

/**
 * This class represents the Enter construct.
 * 
 */
public class LEnter extends LActivity {

    private Role _role=null;
    private String _protocol=null;

    /**
     * This is the default constructor.
     * 
     */
    public LEnter() {
    }
    
    /**
     * This is the copy constructor.
     * 
     * @param copy The copy
     */
    public LEnter(LEnter copy) {
        super(copy);
        _role = copy.getRole();
        _protocol = copy.getProtocol();
    }
    
    /**
     * This method returns the role.
     * 
     * @return The role
     */
    public Role getRole() {
        return (_role);
    }
    
    /**
     * This method sets the role.
     * 
     * @param role The role
     */
    public void setRole(Role role) {
    	_role = role;
    }
    
    /**
     * This method returns the protocol.
     * 
     * @return The protocol
     */
    public String getProtocol() {
        return (_protocol);
    }
    
    /**
     * This method sets the protocol.
     * 
     * @param protocol The protocol
     */
    public void setProtocol(String protocol) {
    	_protocol = protocol;
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(LVisitor visitor) {
    	visitor.accept(this);
    }
    
	/**
	 * {@inheritDoc}
	 */
    public void toText(StringBuffer buf, int level) {
		
    	indent(buf, level);
    	
    	buf.append("enter ");
    	
    	buf.append(_protocol);
    	
		buf.append(";\r\n");
	}
}
