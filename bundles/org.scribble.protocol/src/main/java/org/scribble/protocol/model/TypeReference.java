/*
 * Copyright 2009 www.scribble.org
 *
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
 * This class represents a type reference.
 */
public class TypeReference extends ModelObject {
	
	private String m_name=null;

	/**
	 * This is the default constructor for the type reference.
	 */
	public TypeReference() {
	}

	/**
	 * This is the copy constructor for the type reference.
	 * 
	 * @param ref The reference to copy
	 */
	public TypeReference(TypeReference ref) {
		m_name = ref.m_name;
	}
	
	/**
	 * This is the constructor for the type reference.
	 * 
	 * @param name The name
	 */
	public TypeReference(String name) {
		setName(name);
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
	 * This method visits the model object using the supplied
	 * visitor.
	 * 
	 * @param visitor The visitor
	 */
	public void visit(Visitor visitor) {
	}
	
	/**
	 * This method returns the hash code.
	 * 
	 * @return The hash code
	 */
	public int hashCode() {
		int ret=0;
		
		if (getName() != null) {
			ret = getName().hashCode();
		}
		
		return(ret);
	}

    public boolean equals(Object o) {
        if (!(o instanceof TypeReference)) return false;
        TypeReference tr = (TypeReference) o;
        return tr.m_name == null ? m_name == null : tr.m_name.equals(m_name);
    }

    public String toString() {
		return("TypeRef["+getName()+"]");
	}
}
