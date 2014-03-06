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
package org.scribble.trace.model;

import java.util.Map;

import org.scribble.monitor.Message;
import org.scribble.trace.SimulatorContext;

/**
 * This class represents a message being transferred between the specified
 * roles.
 *
 */
public class MessageTransfer extends Step {

	private String _fromRole;
	private java.util.List<String> _toRoles=new java.util.ArrayList<String>();
	private Message _message;
	
	/**
	 * This method sets the role.
	 * 
	 * @param role The role
	 * @return The message transfer
	 */
	public MessageTransfer setFromRole(String role) {
		_fromRole = role;
		return (this);
	}
	
	/**
	 * This method returns the role.
	 * 
	 * @return The role
	 */
	public String getFromRole() {
		return (_fromRole);
	}
	
	/**
	 * This method sets the 'to' roles.
	 * 
	 * @param roles The 'to' roles
	 * @return The message transfer
	 */
	public MessageTransfer setToRoles(java.util.List<String> roles) {
		_toRoles = roles;
		return (this);
	}
	
	/**
	 * This method returns the 'to' roles.
	 * 
	 * @return The 'to' roles
	 */
	public java.util.List<String> getToRoles() {
		return (_toRoles);
	}
	
	/**
	 * This method returns the values.
	 * 
	 * @return The values
	 */
	public Message getMessage() {
		return (_message);
	}
	
	/**
	 * This method sets the message.
	 * 
	 * @param message The message
	 * @return The message transfer
	 */
	public MessageTransfer setMessage(Message message) {
		_message = message;
		return (this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean simulate(SimulatorContext context, Map<String, RoleSimulator> roleSimulators) {
		boolean ret=true;
		RoleSimulator rs=roleSimulators.get(_fromRole);
		
		if (rs != null) {
			for (String toRole : _toRoles) {
				ret = (rs.send(context, _message, toRole) == getSucceeds());
				if (!ret) {
					break;
				}
			}
		}
		
		if (ret) {
			for (String toRole : _toRoles) {
				rs = roleSimulators.get(toRole);
				if (rs != null) {
					ret = (rs.receive(context, _message, _fromRole) == getSucceeds());
				}
			}
		}
		
		return (ret);
	}
}
