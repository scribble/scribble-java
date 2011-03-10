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
public class Choice extends Activity {

	private java.util.List<When> m_whens=new ContainmentList<When>(this, When.class);
	private Role m_fromRole=null;
	private Role m_toRole=null;

	/**
	 * This is the default constructor.
	 * 
	 */
	public Choice() {
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
	 * This method returns the optional 'to' role.
	 * 
	 * @return The optional 'to' role
	 */
	public Role getToRole() {
		return(m_toRole);
	}
	
	/**
	 * This method sets the optional 'to' role.
	 * 
	 * @param part The optional 'to' role
	 */
	public void setToRole(Role part) {
		m_toRole = part;
	}
	
	/**
	 * This method returns the list of mutually exclusive
	 * activity blocks that comprise the multi-path construct.
	 * 
	 * @return The list of blocks
	 */
	public java.util.List<When> getWhens() {
		return(m_whens);
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
		
		if (getToRole() != null) {
			getToRole().visit(visitor);
		}
		
		for (When w : getWhens()) {
			w.visit(visitor);
		}
		
		visitor.end(this);
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Choice that = (Choice) o;

        return !(m_fromRole != null
                ? !m_fromRole.equals(that.m_fromRole)
                : that.m_fromRole != null)
            && !(m_toRole != null
                ? !m_toRole.equals(that.m_toRole)
                : that.m_toRole != null) 
            && m_whens.equals(that.m_whens);
    }

    @Override
    public int hashCode() {
        int result = m_whens.hashCode();
        result = 31 * result + (m_fromRole != null ? m_fromRole.hashCode() : 0);
        result = 31 * result + (m_toRole != null ? m_toRole.hashCode() : 0);
        return result;
    }

	@Override
	public String toString() {
		String result =  "choice ";
		if (m_fromRole != null) result += "from " + m_fromRole;
		if (m_toRole != null) result += "to " + m_toRole;
		result += "{\n";
		for (When w: m_whens) result += w + "\n";
		return result + "}";
	}
}
