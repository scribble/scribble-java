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
 * This class represents a role.
 * 
 */
public class Role extends ModelObject {
	
	private String m_name=null;	

	/**
	 * This is the default constructor.
	 */
	public Role() {
	}
	
	/**
	 * This is the copy constructor.
	 * 
	 * @param role The role
	 */
	public Role(Role role) {
		m_name = role.getName();
	}
	
	/**
	 * This constructor initializes the role with a name.
	 * 
	 * @param roleName The role name
	 */
	public Role(String roleName) {
		m_name = roleName;
	}
	
	/**
	 * This method returns the name of the role.
	 * 
	 * @return The name
	 */
	public String getName() {
		return(m_name);
	}
	
	/**
	 * This method sets the name of the role.
	 * 
	 * @param name The name
	 */
	public void setName(String name) {
		m_name = name;
	}
	
	public boolean equals(Object obj) {
		boolean ret=false;
	
		if (obj instanceof Role) {
			Role other=(Role)obj;
			
			if (other.getName() != null && other.getName().equals(m_name)) {
				ret = true;
			}
		}
		
		return(ret);
	}
	
	public int hashCode() {
		int ret=super.hashCode();
		
		if (m_name != null) {
			ret = m_name.hashCode();
		}
		
		return(ret);
	}
	
	public String toString() {
		String ret=getName();
		
		if (ret == null) {
			ret = "<Unnamed Role>";
		}
		
		return(ret);
	}
	
	/**
	 * This method visits the model object using the supplied
	 * visitor.
	 * 
	 * @param visitor The visitor
	 */
	public void visit(Visitor visitor) {
	}
}
