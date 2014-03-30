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

import org.scribble.monitor.SessionScope;

/**
 * This class represents a Choice action.
 *
 */
public class Do extends Node {
	
	private int _protocolIndex;

	/**
	 * This method returns the protocol index.
	 * 
	 * @return The protocol index
	 */
	public int getProtocolIndex() {
		return (_protocolIndex);
	}
	
	/**
	 * This method sets the protocol index.
	 * 
	 * @param protocolIndex The protocol index
	 */
	public void setProtocolIndex(int protocolIndex) {
		_protocolIndex = protocolIndex;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean evaluate(SessionType type, int index, SessionScope scope) {
		SessionScope subScope=new SessionScope();
		
		if (Node._nameSessions) {
			subScope.setName("Do/"+index);
		}
		
		subScope.setCompletionIndex(getNext());
		
		Node protocolNode=type.getNode(getProtocolIndex());
		protocolNode.evaluate(type, getProtocolIndex(), subScope);
		
		scope.addSubScope(subScope);
		
		return (false);
	}

}
