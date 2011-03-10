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
 * This class represents an import definition associated with a model.
 * 
 */
public class TypeImportList extends ImportList {

	private String m_format=null;
	private String m_location=null;
	private java.util.List<TypeImport> m_types=
		new ContainmentList<TypeImport>(this, TypeImport.class);

	/**
	 * The default constructor.
	 */
	public TypeImportList() {
	}
	
	/**
	 * This method returns the format.
	 * 
	 * @return The format
	 */
	public String getFormat() {
		return(m_format);
	}
	
	/**
	 * This method sets the format.
	 * 
	 * @param format The format
	 */
	public void setFormat(String format) {
		m_format = format;
	}
	
	/**
	 * This method returns the location of the schema.
	 * 
	 * @return
	 */
	public String getLocation() {
		return(m_location);
	}
	
	/**
	 * This method sets the location of the schema.
	 * 
	 * @param location The location
	 */
	public void setLocation(String location) {
		m_location = location;
	}
	
	/**
	 * This method returns the list of imported types.
	 * 
	 * @return The list of imported types
	 */
	public java.util.List<TypeImport> getTypeImports() {
		return(m_types);
	}
	
	/**
	 * This method returns the imported type associated with
	 * the supplied name.
	 * 
	 * @param name The type name
	 * @return The type, or null if not found
	 */
	public TypeImport getTypeImport(String name) {
		TypeImport ret=null;
		
		for (int i=0; ret == null &&
					i < m_types.size(); i++) {
			if (m_types.get(i).getName().equals(name)) {
				ret = m_types.get(i);
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
		
		for (TypeImport t : getTypeImports()) {
			t.visit(visitor);
		}
	}
}
