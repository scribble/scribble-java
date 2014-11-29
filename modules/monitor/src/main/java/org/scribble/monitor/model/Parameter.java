/*
 * Copyright 2009-11 www.scribble.org
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
package org.scribble.monitor.model;

/**
 * This class represents the type name.
 *
 */
public class Parameter {
	
	private String _name;
	private String _type;
	private java.util.List<Annotation> _annotations=new java.util.ArrayList<Annotation>();
	
	/**
	 * This is the default constructor.
	 */
	public Parameter() {
	}
	
	/**
	 * This constructor initializes the parameter type.
	 * 
	 * @param type The type
	 */
	public Parameter(String type) {
		_type = type;
	}
	
	/**
	 * This constructor initializes the parameter name and type.
	 * 
	 * @param name The name
	 */
	public Parameter(String name, String type) {
		_name = name;
		_type = type;
	}
	
	/**
	 * This method returns the name.
	 * 
	 * @return The name
	 */
	public String getName() {
		return (_name);
	}
	
	/**
	 * This method sets the name.
	 * 
	 * @param name The name
	 */
	public void setName(String name) {
		_name = name;
	}
	
	/**
	 * This method returns the type.
	 * 
	 * @return The type
	 */
	public String getType() {
		return (_type);
	}
	
	/**
	 * This method sets the type.
	 * 
	 * @param type The type
	 */
	public void setType(String type) {
		_type = type;
	}
	
	/**
	 * This method returns the annotations.
	 * 
	 * @return The annotations
	 */
	public java.util.List<Annotation> getAnnotations() {
		return (_annotations);
	}
	
	/**
	 * This method sets the annotations.
	 * 
	 * @param annotations The annotations
	 */
	public void setAnnotations(java.util.List<Annotation> annotations) {
		_annotations = annotations;
	}
	
	/**
	 * This method returns the annotation with the specified name.
	 * 
	 * @param name The name
	 * @return The annotation, or null if not found
	 */
	public Annotation getAnnotation(String name) {
		for (int i=0; i < _annotations.size(); i++) {
			if (_annotations.get(i).getName().equals(name)) {
				return (_annotations.get(i));
			}
		}
		
		return (null);
	}
	
}
