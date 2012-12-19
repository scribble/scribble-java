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

import org.scribble.protocol.model.Protocol;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.model.Visitor;

/**
 * This class represents the protocol notation.
 */
public class LProtocol extends Protocol {
    
	private Role _role=null;
    private LBlock _block=null;

    /**
     * The default constructor.
     */
    public LProtocol() {
    }
    
    /**
     * This method returns the located role. This
     * field is set when the protocol represents a local
     * model.
     * 
     * @return The located role
     */
    public Role getRole() {
        return (_role);
    }
    
    /**
     * This method sets the located role. This
     * field is set when the protocol represents a local
     * model.
     * 
     * @param role The located role
     */
    public void setRole(Role role) {
        _role = role;
    }
    
    /**
     * This method returns the block of activities associated
     * with the definition.
     * 
     * @return The block of activities
     */
    public LBlock getBlock() {
        
        if (_block == null) {
            _block = new LBlock();
            _block.setParent(this);
        }
        
        return (_block);
    }
    
    /**
     * This method sets the block of activities associated
     * with the definition.
     * 
     * @param block The block of activities
     */
    public void setBlock(LBlock block) {
        if (_block != null) {
            _block.setParent(null);
        }
        
        _block = block;
        
        if (_block != null) {
            _block.setParent(this);
        }
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(Visitor visitor) {
        
    	if (visitor instanceof LVisitor) {
	        if (((LVisitor)visitor).start(this)) {
	        
	            if (getBlock() != null) {
	                getBlock().visit(visitor);
	            }
	        }
	        
	        ((LVisitor)visitor).end(this);
    	}
    }
    
    public String toString() {
        String ret="local protocol "+getName()+" ( ";
        
        for (Role role : getRoles()) {
            ret += "role " + role.getName() + " ";
        }
        
        ret += ")\r\n";
        
        ret += getBlock();
        
        return(ret);
    }

	/**
	 * {@inheritDoc}
	 */
    public void toText(StringBuffer buf, int level) {
		
    	indent(buf, level);
    	
    	buf.append("local protocol ");
    	
    	buf.append(getName());
    	
    	buf.append(" at ");
    	
    	if (_role != null) {
    		_role.toText(buf, level);
    	}
    	
    	buf.append("(");
    	
    	for (int i=0; i < getRoles().size(); i++) {
    		if (i > 0) {
    			buf.append(",");
    		}
    		buf.append("role ");
    		getRoles().get(i).toText(buf, level);
    	}
    	buf.append(")");
    	
    	
    	if (_block != null) {
    		_block.toText(buf, level);
    	}
    	
		buf.append("\r\n");
	}    
}
