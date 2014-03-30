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
 * This class represents a Receive action.
 *
 */
public class Receive extends MessageNode {
	
	private String _fromRole;
	
	/**
	 * This method sets the 'from' role.
	 * 
	 * @param role The 'from' role
	 */
	public void setFromRole(String role) {
		_fromRole = role;
	}

	/**
	 * This method returns the 'from' role.
	 * 
	 * @return The 'from' role
	 */
	public String getFromRole() {
		return (_fromRole);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean received(SessionType type,
						SessionScope scope, int scopeIndex, Message message, String fromRole) {
		if (fromRole != null && !fromRole.equals(_fromRole)) {
			return (false);
		}
		return (checkMessage(type, scope, scopeIndex, message));
	}

}
