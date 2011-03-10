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

    private MessageSignature m_messageSignature=null;
	private Role m_fromRole=null;
	private java.util.List<Role> m_toRoles=
				new ContainmentList<Role>(this, Role.class);

    public Interaction() {
    }

    public Interaction(Interaction i) {
    	if (i.m_messageSignature != null) {
    		m_messageSignature = new MessageSignature(i.m_messageSignature);
    	}
    	if (i.m_fromRole != null) {
    		m_fromRole = new Role(i.m_fromRole);
    	}
    	for (Role r : i.m_toRoles) {
    		m_toRoles.add(new Role(r));
    	}
    }

    public Interaction(Role fromRole, MessageSignature sig) {
        m_fromRole = fromRole;
        m_messageSignature = sig;
    }

    public Interaction(Role fromRole, Role toRole, MessageSignature sig) {
        this(fromRole, sig);
        if (toRole != null) m_toRoles.add(toRole);
    }

    public Interaction(Role fromRole, Collection<Role> toRoles, MessageSignature sig) {
        this(fromRole, sig);
        m_toRoles.addAll(toRoles);
    }

	/**
	 * This method returns the message signature.
	 * 
	 * @return The message signature
	 */
	public MessageSignature getMessageSignature() {
		return(m_messageSignature);
	}
	
	/**
	 * This method sets the message signature.
	 * 
	 * @param signature The message signature
	 */
	public void setMessageSignature(MessageSignature signature) {
		
		if (m_messageSignature != null) {
			m_messageSignature.setParent(null);
		}
		
		m_messageSignature = signature;
		
		if (m_messageSignature != null) {
			m_messageSignature.setParent(this);
		}
	}
	
	/**
	 * This method returns the optional 'from' role.
	 * 
	 * @return The optional 'from' role
	 */
	public Role getFromRole() {
		return(m_fromRole);
	}
	
	/**
	 * This method sets the optional 'from' role.
	 * 
	 * @param part The optional 'from' role
	 */
	public void setFromRole(Role part) {
		m_fromRole = part;
	}
	
	/**
	 * This method returns the optional (one or many) 'to' roles.
	 * 
	 * @return The optional 'to' roles
	 */
	public java.util.List<Role> getToRoles() {
		return(m_toRoles);
	}
	
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
		
		return(ret.toString());
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interaction that = (Interaction) o;

        return !(m_fromRole != null
                ? !m_fromRole.equals(that.m_fromRole)
                : that.m_fromRole != null)
            && !(m_messageSignature != null
                ? !m_messageSignature.equals(that.m_messageSignature)
                : that.m_messageSignature != null) 
            && m_toRoles.equals(that.m_toRoles);
    }

    @Override
    public int hashCode() {
        int result = m_messageSignature != null ? m_messageSignature.hashCode() : 0;
        result = 31 * result + (m_fromRole != null ? m_fromRole.hashCode() : 0);
        result = 31 * result + (m_toRoles != null ? m_toRoles.hashCode() : 0);
        return result;
    }

}
