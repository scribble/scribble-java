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

import org.scribble.monitor.runtime.MonitorContext;
import org.scribble.monitor.runtime.SessionScope;

/**
 * This class represents an Interruptible action.
 *
 */
public class Interruptible extends Node {
	
	/**
	 * {@inheritDoc}
	 */
	protected void init(MonitorContext context) {
	}
	
	private int _blockIndex;
	private int _catches=-1;
	private int _throws=-1;

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
	 * This method returns the node index associated with catch
	 * specifications.
	 * 
	 * @return The catch node index
	 */
	public int getCatches() {
		return (_catches);
	}
	
	/**
	 * This method sets the node index associated with catch
	 * specifications.
	 * 
	 * @param index The catch node index
	 */
	public void setCatches(int index) {
		_catches = index;
	}

	/**
	 * This method returns the node index associated with throw
	 * specifications.
	 * 
	 * @return The throw node index
	 */
	public int getThrows() {
		return (_throws);
	}
	
	/**
	 * This method sets the node index associated with throw
	 * specifications.
	 * 
	 * @param index The throw node index
	 */
	public void setThrows(int index) {
		_throws = index;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean evaluate(SessionType type, int index, SessionScope scope) {
		SessionScope subScope=new SessionScope();
		
		if (Node._nameSessions) {
			subScope.setName("Interruptible/"+index);
		}
		
		subScope.setCompletionIndex(getNext());
		
		subScope.setCatches(_catches);
		subScope.setThrows(_throws);
		
		Node blockNode=type.getNode(getBlockIndex());
		blockNode.evaluate(type, getBlockIndex(), subScope);
		
		scope.addSubScope(subScope);
		
		return (false);
	}

}
