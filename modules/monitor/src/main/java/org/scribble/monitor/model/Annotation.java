/*
 * Copyright 2009-14 www.scribble.org
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
 * This class represents an annotation.
 *
 */
public class Annotation {
	
	private String _name;
	private java.util.List<NameValuePair> _properties=new java.util.ArrayList<NameValuePair>();
	
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
	 * This method returns the properties.
	 * 
	 * @return The properties
	 */
	public java.util.List<NameValuePair> getProperties() {
		return (_properties);
	}
	
	/**
	 * This method sets the properties.
	 * 
	 * @param properties The properties
	 */
	public void setProperties(java.util.List<NameValuePair> properties) {
		_properties = properties;
	}
	
	/**
	 * This method adds a property to the annotation.
	 * 
	 * @param name The name
	 * @param value The value
	 */
	public void addProperty(String name, String value) {
		_properties.add(new NameValuePair(name, value));
	}
	
	/**
	 * This method returns the value associated with the specified
	 * name.
	 * 
	 * @param name The name
	 * @return The value, or null if not found
	 */
	public String getValue(String name) {
		for (NameValuePair nvp : _properties) {
			if (nvp.getName().equals(name)) {
				return (nvp.getValue());
			}
		}
		
		return (null);
	}
	
	/**
	 * This class represents a name/value pair.
	 *
	 */
	public static class NameValuePair {
		
		private String _name;
		private String _value;
		
		/**
		 * This is the default constructor.
		 */
		public NameValuePair() {
		}
		
		/**
		 * This constructor initializes the name and value.
		 * 
		 * @param name The name
		 * @param value The value
		 */
		public NameValuePair(String name, String value) {
			_name = name;
			_value = value;
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
		 * This method returns the value.
		 * 
		 * @return The value
		 */
		public String getValue() {
			return (_value);
		}
		
		/**
		 * This method sets the value.
		 * 
		 * @param value The value
		 */
		public void setValue(String value) {
			_value = value;
		}
	}
}
