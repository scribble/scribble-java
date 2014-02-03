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

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.scribble.monitor.Message;
import org.scribble.monitor.MonitorContext;
import org.scribble.monitor.SessionScope;

/**
 * This class represents the base class for all session nodes.
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({@Type(value=Choice.class),
    @Type(value=Continue.class),
    @Type(value=Do.class),
    @Type(value=Interruptible.class),
    @Type(value=MessageNode.class),
    @Type(value=Parallel.class),
    @Type(value=Receive.class),
    @Type(value=Recursion.class),
    @Type(value=Send.class) })
public abstract class Node {
	
	protected static boolean _nameSessions=false;
	
	private int _next=-1;
	
	/**
	 * This method sets whether to name the
	 * sessions.
	 * 
	 * @param name Whether to name the sessions
	 */
	public static void setNameNodes(boolean name) {
		_nameSessions = name;
	}
	
	/**
	 * This method returns the next index.
	 * 
	 * @return The next index, or -1 if not defined
	 */
	public int getNext() {
		return (_next);
	}
	
	/**
	 * This method sets the next index.
	 * 
	 * @param next The next index, or -1 if not defined
	 */
	public void setNext(int next) {
		_next = next;
	}

	/**
	 * This method checks whether the node can be evaluated without external trigger. If
	 * not, then the index should be added to the scope for later processing.
	 *
	 * @param context The monitor's context
	 * @param type The session type
	 * @param index The index
	 * @param scope The session scope
	 * @return Whether the node was evaluated
	 */
	public boolean evaluate(MonitorContext context, SessionType type, int index, SessionScope scope) {
		
		// Associate the node index with the scope, as it cannot be evaluated
		scope.addNodeIndex(index);
		
		return (false);
	}
	
	/**
	 * This method unschedules the current node, if part of the session scope, and
	 * if a next index is identified, this will be evaluated.
	 * 
	 * @param context The monitor's context
	 * @param type The session type
	 * @param scope The scope
	 * @param scopeIndex The index in the scope, or -1 if not contained in the scope
	 */
	protected void handled(MonitorContext context, SessionType type, SessionScope scope, int scopeIndex) {
		
		if (scopeIndex != -1) {
			// Remove index from scope
			scope.getNodeIndexes().remove(scopeIndex);
		}

		if (getNext() != -1) {
			// Check if node can be directly evaluated
			Node nextNode=type.getNode(getNext());
			
			nextNode.evaluate(context, type, getNext(), scope);
		}
	}
	
	/**
	 * This method checks whether the sent message is valid.
	 *
	 * @param context The monitor's context
	 * @param type The session type
	 * @param scope The session scope
	 * @param scopeIndex The index within the scope, or -1 if not currently part of scope
	 * @param message The message
	 * @param toRole The optional 'to' role
	 * @return Whether the sent message was expected
	 */
	public boolean sent(MonitorContext context, SessionType type,
						SessionScope scope, int scopeIndex, Message message, String toRole) {
		return (false);
	}

	/**
	 * This method checks whether the sent message is valid.
	 *
	 * @param context The monitor's context
	 * @param type The session type
	 * @param scope The session scope
	 * @param scopeIndex The index within the scope, or -1 if not currently part of scope
	 * @param message The message
	 * @param fromRole The optional 'from' role
	 * @return Whether the sent message was expected
	 */
	public boolean received(MonitorContext context, SessionType type,
						SessionScope scope, int scopeIndex, Message message, String fromRole) {
		return (false);
	}

}
