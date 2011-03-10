/*
 * Copyright 2009-10 www.scribble.org
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
 * This class represents the data type bound to a particular type name used
 * in the protocol.
 *
 */
public class DataType extends ModelObject {

	private String m_details=null;
	
	/**
	 * Default constructor.
	 */
	public DataType() {
	}
	
	/**
	 * Constructor used to initialise the data type details.
	 * 
	 * @param details The details
	 */
	public DataType(String details) {
		m_details = details;
	}
	
	/**
	 * This method returns the details.
	 * 
	 * @return The details
	 */
	public String getDetails() {
		return(m_details);
	}
	
	/**
	 * This method sets the details.
	 * 
	 * @param details The details
	 */
	public void setDetails(String details) {
		m_details = details;
	}

	public void visit(Visitor visitor) {
	}
}
