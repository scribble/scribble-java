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

import org.scribble.protocol.model.MessageSignature;
import org.scribble.protocol.model.Role;

/**
 * This class represents an interaction: the communication
 * of a message from one role to another, or several others.
 * 
 */
public class LReceive extends LActivity {

    private MessageSignature _messageSignature=null;
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
        
        if (i._messageSignature != null) {
            _messageSignature = new MessageSignature(i._messageSignature);
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
    public LReceive(MessageSignature sig, Role fromRole) {
        _messageSignature = sig;
        _fromRole = fromRole;
    }

    /**
     * This method returns the message signature.
     * 
     * @return The message signature
     */
    public MessageSignature getMessageSignature() {
        return (_messageSignature);
    }
    
    /**
     * This method sets the message signature.
     * 
     * @param signature The message signature
     */
    public void setMessageSignature(MessageSignature signature) {
        
        if (_messageSignature != null) {
            _messageSignature.setParent(null);
        }
        
        _messageSignature = signature;
        
        if (_messageSignature != null) {
            _messageSignature.setParent(this);
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
        
        if (getMessageSignature() != null) {
            ret.append(getMessageSignature());
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
        
        if (getMessageSignature() != null) {
            getMessageSignature().visit(visitor);
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
            && !(_messageSignature != null
                ? !_messageSignature.equals(that._messageSignature)
                : that._messageSignature != null);
    }

    @Override
    public int hashCode() {
        int result = _messageSignature != null ? _messageSignature.hashCode() : 0;
        result = 31 * result + (_fromRole != null ? _fromRole.hashCode() : 0);
        return result;
    }

	/**
	 * {@inheritDoc}
	 */
    public void toText(StringBuffer buf, int level) {
		
    	indent(buf, level);
    	
    	_messageSignature.toText(buf, level);
    	
    	if (_fromRole != null) {
    		buf.append(" from ");
    		_fromRole.toText(buf, level);
    	}
    	
		buf.append(";\n");
	}
}
