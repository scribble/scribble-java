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
	
	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj) {
		boolean ret=false;
		
		if (obj instanceof Message) {
			Message other=(Message)obj;
			
			if (other._operator != null
					&& _operator != null
					&& other._operator.equals(_operator)) {
				
				if (_types.size() == other._types.size()
						&& _values.size() == other._values.size()) {
					ret = true;
					
					for (int i=0; ret && i < _types.size(); i++) {
						if (_types.get(i) == null || other._types.get(i) == null
								|| !_types.get(i).equals(other._types.get(i))) {
							ret = false;
						}
					}
					
					for (int i=0; ret && i < _values.size(); i++) {
						if (_values.get(i) == null || other._values.get(i) == null
								|| !_values.get(i).equals(other._values.get(i))) {
							ret = false;
						}
					}
				}
			}
		}
		
		return (ret);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		StringBuffer buf=new StringBuffer();
		
		buf.append(_operator);
		buf.append("(");
		
		for (int i=0; i < _types.size(); i++) {
			if (i > 0) {
				buf.append(",");
			}
			buf.append(_types.get(i));
		}
		buf.append(")");
		
		return (buf.toString());
	}
}
