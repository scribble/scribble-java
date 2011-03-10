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
 * This class represents a protocol reference.
 */
public class ProtocolReference extends ModelObject {

	public static final String LOCATED_REFERENCE_SEPARATOR="@";

	private String m_name=null;
	private Role m_role=null;

	/**
	 * This is the default constructor for the protocol reference.
	 */
	public ProtocolReference() {
	}
	
	/**
	 * This is the constructor for the protocol reference.
	 * 
	 * @param name The name
	 */
	public ProtocolReference(String name) {
		setName(name);
	}
	
	/**
	 * This is the copy constructor for the model reference.
	 * 
	 * @param ref The reference to copy
	 */
	public ProtocolReference(ProtocolReference ref) {
		super(ref);
		
		m_name = ref.m_name;
		m_role = ref.m_role;
	}
	
	/**
	 * This method returns the name associated with the
	 * model reference.
	 * 
	 * @return The name
	 */
	public String getName() {
		return(m_name);
	}
	
	/**
	 * This method sets the name associated with the
	 * model reference.
	 * 
	 * @param name The name
	 */
	public void setName(String name) {
		m_name = name;
	}
	
	/**
	 * This method sets the located role.
	 * 
	 * @param role The role
	 */
	public void setRole(Role role) {
		m_role = role;
	}
	
	/**
	 * This method returns the located role.
	 * 
	 * @return The role
	 */
	public Role getRole() {
		return(m_role);
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
