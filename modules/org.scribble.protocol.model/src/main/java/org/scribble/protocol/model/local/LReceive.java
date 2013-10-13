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

import org.scribble.protocol.model.Message;
import org.scribble.protocol.model.Role;

/**
 * This class represents an interaction: the communication
 * of a message from one role to another, or several others.
 * 
 */
public class LReceive extends LActivity {

    private Message _message=null;
    private Role _fromRole=null;

    /**
     * The default constructor.
     */
    public LReceive() {
    }

    /**
     * The copy constructor.
     * 
     * @param i The interaction to copy
     */
    public LReceive(LReceive i) {
        super(i);
        
        if (i._message != null) {
            _message = new Message(i._message);
        }
        _fromRole = i._fromRole;
    }

    /**
     * This constructor initializes the 'from' role and message
     * signature.
     * 
     * @param sig The message signature
     * @param fromRole The 'from' role
     */
    public LReceive(Message sig, Role fromRole) {
        _message = sig;
        _fromRole = fromRole;
    }

    /**
     * This method returns the message.
     * 
     * @return The message
     */
    public Message getMessage() {
        return (_message);
    }
    
    /**
     * This method sets the message.
     * 
     * @param message The message
     */
    public void setMessage(Message message) {
        
        if (_message != null) {
            _message.setParent(null);
        }
        
        _message = message;
        
        if (_message != null) {
            _message.setParent(this);
        }
    }
    
    /**
     * This method returns the optional 'from' role.
     * 
     * @return The optional 'from' role
     */
    public Role getFromRole() {
        return (_fromRole);
    }
    
    /**
     * This method sets the optional 'from' role.
     * 
     * @param part The optional 'from' role
     */
    public void setFromRole(Role part) {
        _fromRole = part;
    }
    
    @Override
    public String toString() {
        StringBuffer ret=new StringBuffer();
        
        if (getMessage() != null) {
            ret.append(getMessage());
        }
        
        ret.append(" from ");
        ret.append(getFromRole());
        
        return (ret.toString());
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(LVisitor visitor) {
        visitor.accept(this);
        
        if (getMessage() != null) {
            getMessage().visit(visitor);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LReceive that = (LReceive) o;

        return !(_fromRole != null
                ? !_fromRole.equals(that._fromRole)
                : that._fromRole != null)
            && !(_message != null
                ? !_message.equals(that._message)
                : that._message != null);
    }

    @Override
    public int hashCode() {
        int result = _message != null ? _message.hashCode() : 0;
        result = 31 * result + (_fromRole != null ? _fromRole.hashCode() : 0);
        return result;
    }

	/**
	 * {@inheritDoc}
	 */
    public void toText(StringBuffer buf, int level) {
		
    	indent(buf, level);
    	
    	_message.toText(buf, level);
    	
    	if (_fromRole != null) {
    		buf.append(" from ");
    		_fromRole.toText(buf, level);
    	}
    	
		buf.append(";\n");
	}
}
