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

/**
 * This class represents the Choice construct between
 * two or more paths.
 * 
 */
public class DirectedChoice extends Activity {

	private Role m_fromRole=null;
	private java.util.List<Role> m_toRoles=
			new ContainmentList<Role>(this, Role.class);
	private java.util.List<OnMessage> m_onMessages=new ContainmentList<OnMessage>(this, OnMessage.class);

	/**
	 * This is the default constructor.
	 * 
	 */
	public DirectedChoice() {
	}
	
	/**
	 * This method returns the from role.
	 * 
	 * @return The from role
	 */
	public Role getFromRole() {
		return(m_fromRole);
	}
	
	/**
	 * This method sets the from role.
	 * 
	 * @param part The from role
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
	
	/**
	 * This method returns the list of on-message blocks
	 * representing the different paths of the directed
	 * choice.
	 * 
	 * @return The list of on-messages
	 */
	public java.util.List<OnMessage> getOnMessages() {
		return(m_onMessages);
	}
	
	/**
	 * This method visits the model object using the supplied
	 * visitor.
	 * 
	 * @param visitor The visitor
	 */
	public void visit(Visitor visitor) {
		visitor.start(this);
		
		if (getFromRole() != null) {
			getFromRole().visit(visitor);
		}
		
		for (Role toRole : getToRoles()) {
			toRole.visit(visitor);
		}
		
		for (OnMessage om : getOnMessages()) {
			om.visit(visitor);
		}
		
		visitor.end(this);
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DirectedChoice that = (DirectedChoice) o;

        return !(m_fromRole != null
                ? !m_fromRole.equals(that.m_fromRole)
                : that.m_fromRole != null)
             && m_onMessages.equals(that.m_onMessages);
    }

    @Override
    public int hashCode() {
        int result = m_onMessages.hashCode();
        result = 31 * result + (m_fromRole != null ? m_fromRole.hashCode() : 0);
        return result;
    }

	@Override
	public String toString() {
		String result =  "";
		if (m_fromRole != null) result += "from " + m_fromRole+" ";
		if (m_toRoles.size() > 0) {
			result += "to ";
			for (int i=0; i < m_toRoles.size(); i++) {
				if (i > 0) {
					result += ", ";
				}
				result += m_toRoles.get(i);
			}
		}
		for (OnMessage b: m_onMessages) {
			if (m_onMessages.indexOf(b) > 0) {
				result += "or ";
			}
			result += b + "\n";
		}
		return result;
	}
}
