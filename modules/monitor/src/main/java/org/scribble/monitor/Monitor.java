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

import org.scribble.monitor.model.SessionType;

public interface Monitor {

	/**
	 * This method initializes a session instance based on the supplied session type.
	 * 
	 * @param type The session type
	 * @param instance The session instance
	 */
	public void initialize(SessionType type, SessionInstance instance);
	
	/**
	 * This method checks whether the sent message is valid.
	 *
	 * @param type The session type
	 * @param instance The session instance
	 * @param message The message
	 * @param toRole The optional 'to' role
	 * @return Whether the sent message was expected
	 */
	public boolean sent(SessionType type, SessionInstance instance,
					Message message, String toRole);

	/**
	 * This method checks whether the received message is valid.
	 *
	 * @param type The session type
	 * @param instance The session instance
	 * @param message The message
	 * @param fromRole The optional 'from' role
	 * @return Whether the received message was expected
	 */
	public boolean received(SessionType type, SessionInstance instance,
					Message message, String fromRole);

}
