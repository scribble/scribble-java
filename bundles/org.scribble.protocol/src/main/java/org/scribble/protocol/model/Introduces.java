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
 * This class represents the list of roles introduced by
 * an existing role within a Scribble definition.
 */
public class Introduces extends Activity {
	
	private Role m_introducer=null;
	private java.util.List<Role> m_roles=
			new ContainmentList<Role>(this, Role.class);

	public Introduces() {
	}
	
	public Introduces(Introduces rl) {
		super(rl);
		
		for (Role r : rl.getRoles()) {
			getRoles().add(new Role(r));
		}
	}
	
	/**
	 * This method returns the introducing role.
	 * 
	 * @return The introducer
	 */
	public Role getIntroducer() {
		return(m_introducer);
	}
	
	/**
	 * This method sets the introducing role.
	 * 
	 * @param role The introducer
	 */
	public void setIntroducer(Role role) {
		m_introducer = role;
	}
	
	/**
	 * This method returns the list of roles.
	 * 
	 * @return The list of roles
	 */
	public java.util.List<Role> getRoles() {
		return(m_roles);
	}
	
	/**
	 * This method returns the role associated with
	 * the supplied name.
	 * 
	 * @param name The role name
	 * @return The role, or null if not found
	 */
	public Role getIntroducedRole(String name) {
		Role ret=null;
		
		for (int i=0; ret == null &&
					i < m_roles.size(); i++) {
			if (m_roles.get(i).getName().equals(name)) {
				ret = m_roles.get(i);
			}
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
		visitor.accept(this);		
	}
}
