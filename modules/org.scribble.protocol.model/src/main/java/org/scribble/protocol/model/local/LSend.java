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
public class LSend extends LActivity {

    private Message _messageSignature=null;
    private java.util.List<Role> _toRoles=new java.util.ArrayList<Role>();

    /**
     * The default constructor.
     */
    public LSend() {
    }

    /**
     * The copy constructor.
     * 
     * @param i The interaction to copy
     */
    public LSend(LSend i) {
        super(i);
        
        if (i._messageSignature != null) {
            _messageSignature = new Message(i._messageSignature);
        }
        for (Role r : i._toRoles) {
        	_toRoles.add(new Role(r));
        }
    }

    /**
     * This constructor initializes the 'to' role and message
     * signature.
     * 
     * @param sig The message signature
     * @param toRole The 'to' role
     */
    public LSend(Message sig, Role toRole) {
        _messageSignature = sig;
        _toRoles.add(toRole);
    }

    /**
     * This method returns the message signature.
     * 
     * @return The message signature
     */
    public Message getMessageSignature() {
        return (_messageSignature);
    }
    
    /**
     * This method sets the message signature.
     * 
     * @param signature The message signature
     */
    public void setMessageSignature(Message signature) {
        
        if (_messageSignature != null) {
            _messageSignature.setParent(null);
        }
        
        _messageSignature = signature;
        
        if (_messageSignature != null) {
            _messageSignature.setParent(this);
        }
    }
    
    /**
     * This method returns the 'to' roles.
     * 
     * @return The 'to' roles
     */
    public java.util.List<Role> getToRoles() {
        return (_toRoles);
    }
    
    /**
     * This method sets the 'to' roles.
     * 
     * @param part The 'to' roles
     */
    public void setToRoles(java.util.List<Role> part) {
    	_toRoles = part;
    }
    
    @Override
    public String toString() {
        StringBuffer ret=new StringBuffer();
        
        if (getMessageSignature() != null) {
            ret.append(getMessageSignature());
        }
        
        ret.append(" to ");
        
        for (int i=0; i < getToRoles().size(); i++) {
        	if (i > 0) {
        		ret.append(",");
        	}
        	ret.append(getToRoles().get(i));
        }
        
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

        LSend that = (LSend) o;

        boolean ret = !(_messageSignature != null
                ? !_messageSignature.equals(that._messageSignature)
                : that._messageSignature != null);
        
        if (ret) {
        	if (_toRoles.size() != that.getToRoles().size()) {
        		return false;
        	}
        	for (int i=0; i < _toRoles.size(); i++) {
        		Role r=_toRoles.get(i);
        		if (!r.equals(that.getToRoles().get(i))) {
        			return false;
        		}
        	}
        }
        
        return ret;
    }

    @Override
    public int hashCode() {
        int result = _messageSignature != null ? _messageSignature.hashCode() : 0;
        result = 31 * result + (_toRoles.size()>0 ? _toRoles.get(0).hashCode() : 0);
        return result;
    }


	/**
	 * {@inheritDoc}
	 */
    public void toText(StringBuffer buf, int level) {
		
    	indent(buf, level);
    	
    	_messageSignature.toText(buf, level);
    	
    	if (_toRoles != null) {
    		buf.append(" to ");
            for (int i=0; i < getToRoles().size(); i++) {
            	if (i > 0) {
            		buf.append(",");
            	}
            	_toRoles.get(i).toText(buf, level);
            }
    		
    	}
    	
		buf.append(";\n");
	}
}
