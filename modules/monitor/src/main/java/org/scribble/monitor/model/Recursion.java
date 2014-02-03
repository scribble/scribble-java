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

import org.scribble.monitor.MonitorContext;
import org.scribble.monitor.SessionScope;

/**
 * This class represents a Recursion action.
 *
 */
public class Recursion extends Node {
	
	private int _blockIndex;

	/**
	 * This method returns the block index.
	 * 
	 * @return The block index
	 */
	public int getBlockIndex() {
		return (_blockIndex);
	}
	
	/**
	 * This method sets the block index.
	 * 
	 * @param blockIndex The block index
	 */
	public void setBlockIndex(int blockIndex) {
		_blockIndex = blockIndex;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean evaluate(MonitorContext context, SessionType type, int index, SessionScope scope) {
		SessionScope subScope=new SessionScope();
		
		if (Node._nameSessions) {
			subScope.setName("Recursion/"+index);
		}
		
		subScope.setCompletionIndex(getNext());
		
		Node blockNode=type.getNode(getBlockIndex());
		blockNode.evaluate(context, type, getBlockIndex(), subScope);
		
		scope.addSubScope(subScope);
		
		return (false);
	}

}
