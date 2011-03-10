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
 * This class represents the binding between a declaration and
 * the name of a declaration in a composed definition.
 */
public class Parameter extends ModelObject {

	private String m_localName=null;

	/**
	 * The default constructor.
	 */
	public Parameter() {
	}
	
	/**
	 * This constructor initializes the declaration (local name) and bound
	 * name.
	 * 
	 * @param localName The local name
	 * @param boundName The bound name
	 */
	public Parameter(String localName) {
		m_localName = localName;
	}
	
	public Parameter(Parameter p) {
		m_localName = p.m_localName;
	}
	
	/**
	 * This method sets the local name.
	 * 
	 * @param localName The local name
	 */
	public void setName(String localName) {
		m_localName = localName;
	}
	
	/**
	 * This method returns the local name.
	 * 
	 * @return The local name
	 */
	public String getName() {
		return(m_localName);
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
