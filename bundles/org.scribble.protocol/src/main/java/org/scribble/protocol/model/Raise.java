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
 * This class represents the Raise construct.
 * 
 */
public class Raise extends Activity {

	private java.util.List<Role> m_roles=new java.util.Vector<Role>();
	private TypeReference m_type=null;

	/**
	 * This is the default constructor.
	 * 
	 */
	public Raise() {
	}
	
	/**
	 * This method returns the list of roles at which
	 * the decision is located.
	 * 
	 * @return The list of roles
	 */
	public java.util.List<Role> getRoles() {
		return(m_roles);
	}
		
	/**
	 * This method returns the reference of the type associated
	 * with the catch block.
	 * 
	 * @return The type
	 */
	public TypeReference getType() {
		return(m_type);
	}
	
	/**
	 * This method sets the reference of the type associated
	 * with the catch block.
	 * 
	 * @param type The type
	 */
	public void setType(TypeReference type) {
		
		if (m_type != null) {
			m_type.setParent(null);
		}
		
		m_type = type;
		
		if (m_type != null) {
			m_type.setParent(this);
		}
	}
	
	/**
	 * This method visits the model object using the supplied
	 * visitor.
	 * 
	 * @param visitor The visitor
	 */
	public void visit(Visitor visitor) {
		visitor.accept(this);
	}
}
