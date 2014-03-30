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

import org.scribble.monitor.Message;
import org.scribble.monitor.SessionScope;

/**
 * This class represents the base class for message related
 * nodes.
 *
 */
public abstract class MessageNode extends Node {
	
	private String _operator;
	private java.util.List<String> _types=new java.util.ArrayList<String>();
	
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
	 */
	public void setOperator(String operator) {
		_operator = operator;
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
	 */
	public void setTypes(java.util.List<String> types) {
		_types = types;
	}
	
	/**
	 * This method checks the message against the message node.
	 * 
	 * @param type The session type
	 * @param scope The scope
	 * @param scopeIndex The scope index
	 * @param message The message
	 * @return Whether the message is valid
	 */
	protected boolean checkMessage(SessionType type,
							SessionScope scope, int scopeIndex, Message message) {
		boolean ret=false;
		
		if (_operator.equals(message.getOperator())
				&& _types.size() == message.getTypes().size()) {
			
			ret = true;
			
			for (int i=0; ret && i < _types.size(); i++) {
				ret = _types.get(i).equals(message.getTypes().get(i));
			}
		}
		
		if (ret) {
			handled(type, scope, scopeIndex);
		}
		
		return (ret);
	}
}
