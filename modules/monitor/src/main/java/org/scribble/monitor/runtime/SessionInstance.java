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
package org.scribble.monitor.runtime;

/**
 * This class represents the state associated with an instance of
 * a session type.
 *
 */
public class SessionInstance {
	
	private SessionScope _scope;

	/**
	 * This method returns the top level scope associated with this
	 * session instance.
	 * 
	 * @return The session scope
	 */
	public SessionScope getScope() {
		return (_scope);
	}

	/**
	 * This method sets the top level scope associated with this
	 * session instance.
	 * 
	 * @param scope The session scope
	 */
	public void setScope(SessionScope scope) {
		_scope = scope;
	}
	
	/**
	 * This method determines whether the session instance has completed.
	 * 
	 * @return Whether session completed
	 */
	public boolean hasCompleted() {
		return (_scope.completed());
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		String ret=null;
		
		try {
			ret = new String(MonitorUtil.serializeSessionInstance(this));
		} catch (Exception e) {
			ret = "<Unable to serialize instance: "+e.getMessage()+">";
		}
		
		return (ret);
	}
}
