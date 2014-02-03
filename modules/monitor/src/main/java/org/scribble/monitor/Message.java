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
package org.scribble.monitor;

/**
 * This class represents a message being sent or received by the
 * role being monitored.
 *
 */
public class Message {

	private String _operator;
	private java.util.List<String> _types=new java.util.ArrayList<String>();
	private java.util.List<Object> _values=new java.util.ArrayList<Object>();
	
	/**
	 * This method returns the operator.
	 * 
	 * @return The operator
	 */
	public String getOperator() {
		return (_operator);
	}
	
	/**
	 * This method sets the operator.
	 * 
	 * @param operator The operator
	 * @return The message
	 */
	public Message setOperator(String operator) {
		_operator = operator;
		return (this);
	}
	
	/**
	 * This method returns the types.
	 * 
	 * @return The types
	 */
	public java.util.List<String> getTypes() {
		return (_types);
	}
	
	/**
	 * This method sets the types.
	 * 
	 * @param types The types
	 * @return The message
	 */
	public Message setTypes(java.util.List<String> types) {
		_types = types;
		return (this);
	}
	
	/**
	 * This method returns the values.
	 * 
	 * @return The values
	 */
	public java.util.List<Object> getValues() {
		return (_values);
	}
	
	/**
	 * This method sets the values.
	 * 
	 * @param values The values
	 * @return The message
	 */
	public Message setValues(java.util.List<Object> values) {
		_values = values;
		return (this);
	}
}
