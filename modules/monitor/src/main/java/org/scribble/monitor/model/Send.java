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
 * This class represents a Send action.
 *
 */
public class Send extends MessageNode {
	
	private String _toRole;
	
	/**
	 * This method sets the 'to' role.
	 * 
	 * @param role The 'to' role
	 */
	public void setToRole(String role) {
		_toRole = role;
	}

	/**
	 * This method returns the 'to' role.
	 * 
	 * @return The 'to' role
	 */
	public String getToRole() {
		return (_toRole);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean sent(SessionType type,
					SessionScope scope, int scopeIndex, Message message, String toRole) {
		if (toRole != null && !toRole.equals(_toRole)) {
			return (false);
		}
		return (checkMessage(type, scope, scopeIndex, message));
	}

}
