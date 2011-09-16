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
package org.scribble.protocol.model;

import java.util.Collection;

/**
 * This class represents an interaction: the communication
 * of a message from one role to another, or several others.
 * 
 */
public class Interaction extends Activity {

    private MessageSignature _messageSignature=null;
    private Role _fromRole=null;
    private java.util.List<Role> _toRoles=
                new ContainmentList<Role>(this, Role.class);

    /**
     * The default constructor.
     */
    public Interaction() {
    }

    /**
     * The copy constructor.
     * 
     * @param i The interaction to copy
     */
    public Interaction(Interaction i) {
        if (i._messageSignature != null) {
            _messageSignature = new MessageSignature(i._messageSignature);
        }
        if (i._fromRole != null) {
            _fromRole = new Role(i._fromRole);
        }
        for (Role r : i._toRoles) {
            _toRoles.add(new Role(r));
        }
    }

    /**
     * This constructor initializes the 'from' role and message
     * signature.
     * 
     * @param fromRole The 'from' role
     * @param sig The message signature
     */
    public Interaction(Role fromRole, MessageSignature sig) {
        _fromRole = fromRole;
        _messageSignature = sig;
    }

    /**
     * This method initializes the 'from' and 'to' roles, and
     * message signature.
     * 
     * @param fromRole The 'from' role
     * @param toRole The 'to' role
     * @param sig The message signature
     */
    public Interaction(Role fromRole, Role toRole, MessageSignature sig) {
        this(fromRole, sig);
        if (toRole != null) {
            _toRoles.add(toRole);
        }
    }

    /**
     * This method initializes the 'from' and 'to' roles, and
     * message signature.
     * 
     * @param fromRole The 'from' role
     * @param toRoles The 'to' roles
     * @param sig The message signature
     */
    public Interaction(Role fromRole, Collection<Role> toRoles, MessageSignature sig) {
        this(fromRole, sig);
        _toRoles.addAll(toRoles);
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
    
    /**
     * This method returns the optional (one or many) 'to' roles.
     * 
     * @return The optional 'to' roles
     */
    public java.util.List<Role> getToRoles() {
        return (_toRoles);
    }
    
    @Override
    public String toString() {
        StringBuffer ret=new StringBuffer();
        
        if (getMessageSignature() != null) {
            ret.append(getMessageSignature());
            ret.append(" ");
        }
        
        if (getFromRole() != null) {
            ret.append(getFromRole());
            ret.append("->");
            
            for (int i=0; i < getToRoles().size(); i++) {
                if (i > 0) {
                    ret.append(",");
                }
                ret.append(getToRoles().get(i));
            }
        } else {
            ret.append("->");
            
            for (int i=0; i < getToRoles().size(); i++) {
                if (i > 0) {
                    ret.append(",");
                }
                ret.append(getToRoles().get(i));
            }
        }
        
        return (ret.toString());
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(Visitor visitor) {
        visitor.accept(this);
        
        if (getMessageSignature() != null) {
            getMessageSignature().visit(visitor);
        }
        
        if (getFromRole() != null) {
            getFromRole().visit(visitor);
        }
        
        for (Role r : getToRoles()) {
            r.visit(visitor);
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

        Interaction that = (Interaction) o;

        return !(_fromRole != null
                ? !_fromRole.equals(that._fromRole)
                : that._fromRole != null)
            && !(_messageSignature != null
                ? !_messageSignature.equals(that._messageSignature)
                : that._messageSignature != null) 
            && _toRoles.equals(that._toRoles);
    }

    @Override
    public int hashCode() {
        int result = _messageSignature != null ? _messageSignature.hashCode() : 0;
        result = 31 * result + (_fromRole != null ? _fromRole.hashCode() : 0);
        result = 31 * result + (_toRoles != null ? _toRoles.hashCode() : 0);
        return result;
    }

}
