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
package org.scribble.model.global;

import org.scribble.model.Message;
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;
import org.scribble.model.global.GActivity;

/**
 * This class represents an interaction: the communication
 * of a message from one role to another, or several others.
 * 
 */
public class GMessageTransfer extends GActivity {

    private Message _message=null;
    private Role _fromRole=null;
    private java.util.List<Role> _toRoles=new java.util.ArrayList<Role>();

    /**
     * The default constructor.
     */
    public GMessageTransfer() {
    }

    /**
     * The copy constructor.
     * 
     * @param i The interaction to copy
     */
    public GMessageTransfer(GMessageTransfer i) {
        super(i);
        
        if (i._message != null) {
            _message = new Message(i._message);
        }
        
        _fromRole = i._fromRole;
        
        for (Role r : i._toRoles) {
        	_toRoles.add(new Role(r));
        }
    }

    /**
     * This method initializes the 'from' and 'to' roles, and
     * message signature.
     * 
     * @param sig The message signature
     * @param fromRole The 'from' role
     * @param toRoles The 'to' roles
     */
    public GMessageTransfer(Message sig, Role fromRole, java.util.List<Role> toRoles) {
        _message = sig;
        _fromRole = fromRole;
        _toRoles = toRoles;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isRoleInvolved(RoleDecl role) {
    	boolean ret=false;
    	
    	if (_fromRole != null) {
    		ret = role.isRole(_fromRole);
    	}
    	
    	for (int i=0; !ret && i < _toRoles.size(); i++) {
    		ret = role.isRole(_toRoles.get(i));
    	}
    	
    	return (ret);
    }
    
    /**
     * {@inheritDoc}
     */
    public void identifyInvolvedRoles(java.util.Set<Role> roles) {
    	
    	if (_fromRole != null) {
    		roles.add(_fromRole);
    	}
    	
    	roles.addAll(_toRoles);
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
     * This method returns the 'from' role.
     * 
     * @return The 'from' role
     */
    public Role getFromRole() {
        return (_fromRole);
    }
    
    /**
     * This method sets the 'from' role.
     * 
     * @param part The 'from' role
     */
    public void setFromRole(Role part) {
        _fromRole = part;
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
        
        if (getMessage() != null) {
            ret.append(getMessage());
            ret.append(" ");
        }
        
        ret.append(getFromRole());
        ret.append("->");
        
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
    public void visit(GVisitor visitor) {
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

        GMessageTransfer that = (GMessageTransfer) o;

        boolean ret=!(_fromRole != null
                ? !_fromRole.equals(that._fromRole)
                : that._fromRole != null)
            && !(_message != null
                ? !_message.equals(that._message)
                : that._message != null);
        
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
