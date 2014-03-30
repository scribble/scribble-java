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

import org.scribble.monitor.SessionInstance;
import org.scribble.monitor.model.Node;
import org.scribble.monitor.model.SessionType;

/**
 * This class represents the monitorable version of a local protocol.
 *
 */
public class DefaultMonitor implements Monitor {

	/**
	 * {@inheritDoc}
	 */
	public void initialize(SessionType type, SessionInstance instance) {
		type.initialize(instance);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean sent(SessionType type, SessionInstance instance,
							Message message, String toRole) {
		return (sent(type, instance.getScope(), message, toRole));
	}
	
	/**
	 * This method checks whether the sent message is applicable to the supplied
	 * session scope or any of its child scopes.
	 * 
	 * @param type The session type
	 * @param scope The session scope
	 * @param message The message
	 * @param toRole The optional 'to' role
	 * @return Whether the sent message is applicable to the scope
	 */
	protected boolean sent(SessionType type, SessionScope scope,
							Message message, String toRole) {
		boolean ret=false;
		
		// Check throws
		if (scope.getThrows() != -1) {
			Node throwNode=type.getNode(scope.getThrows());
			
			ret = throwNode.sent(type, scope, -1, message, toRole);
			
			if (ret) {
				scope.completed(true);
				return (true);
			}
		}

		// Check indexes for this session scope
		if (scope.getNodeIndexes() != null) {
			for (int i=0; i < scope.getNodeIndexes().size(); i++) {
				int index=scope.getNodeIndexes().get(i);
				Node node=type.getNode(index);
				
				ret = node.sent(type, scope, i, message, toRole);
				
				if (ret) {
					break;
				}
			}
		}
		
		if (!ret && scope.getSubScopes() != null) {
			for (int i=0; i < scope.getSubScopes().size(); i++) {
				SessionScope subScope=scope.getSubScopes().get(i);
				
				ret = sent(type, subScope, message, toRole);
				
				if (ret) {					
					checkScope(type, scope, subScope, i);
					break;
				}
			}
		}
		
		return (ret);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean received(SessionType type, SessionInstance instance,
							Message message, String fromRole) {
		return (received(type, instance.getScope(), message, fromRole));
	}
	
	/**
	 * This method checks whether the received message is applicable to the supplied
	 * session scope or any of its child scopes.
	 * 
	 * @param type The session type
	 * @param scope The session scope
	 * @param message The message
	 * @param fromRole The optional 'from' role
	 * @return Whether the sent message is applicable to the scope
	 */
	protected boolean received(SessionType type, SessionScope scope,
							Message message, String fromRole) {
		boolean ret=false;
		
		// Check catches
		if (scope.getCatches() != -1) {
			Node catchNode=type.getNode(scope.getCatches());
			
			ret = catchNode.received(type, scope, -1, message, fromRole);
			
			if (ret) {
				scope.completed(true);
				return (true);
			}
		}

		// Check indexes for this session scope
		if (scope.getNodeIndexes() != null) {
			for (int i=0; i < scope.getNodeIndexes().size(); i++) {
				int index=scope.getNodeIndexes().get(i);
				Node node=type.getNode(index);
				
				ret = node.received(type, scope, i, message, fromRole);
				
				if (ret) {
					break;
				}
			}
		}
		
		if (!ret && scope.getSubScopes() != null) {
			for (int i=0; i < scope.getSubScopes().size(); i++) {
				SessionScope subScope=scope.getSubScopes().get(i);
				
				ret = received(type, subScope, message, fromRole);
				
				if (ret) {					
					checkScope(type, scope, subScope, i);
					break;
				}
			}
		}
		
		return (ret);
	}
	
	/**
	 * This method checks a sub-scope to see whether it has completed, and if so,
	 * remove it from the parent scope and initiate any optional completion node.
	 * 
	 * @param type The session type
	 * @param scope The parent scope
	 * @param subScope The sub scope to check
	 * @param scopeIndex The sub-scope's index within the parent scope
	 */
	protected void checkScope(SessionType type,
			SessionScope scope, SessionScope subScope, int scopeIndex) {
		
		// Check if parent scope is mutually exclusive 
		if (scope.getMutuallyExclusive()
						&& scope.getSubScopes().size() != 1) {
			scope.getSubScopes().clear();
			scope.getSubScopes().add(subScope);
			scopeIndex = 0;
		}

		// Check if sub scope is now empty
		if (subScope.completed()) {
			
			// Remove sub-scope
			// TODO: Investigate whether scope positions can possibly change
			scope.getSubScopes().remove(scopeIndex);
			
			if (subScope.getCompletionIndex() != -1) {
				Node node=type.getNode(subScope.getCompletionIndex());
 
				node.evaluate(type, subScope.getCompletionIndex(), scope);
			}
		}
	}
	
}
