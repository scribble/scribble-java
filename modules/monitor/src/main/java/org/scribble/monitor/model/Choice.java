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
public class Choice extends Node {
	
	private java.util.List<Integer> _pathIndexes=new java.util.ArrayList<Integer>();

	/**
	 * This method returns the choice path indexes.
	 * 
	 * @return The choice path indexes
	 */
	public java.util.List<Integer> getPathIndexes() {
		return (_pathIndexes);
	}
	
	/**
	 * This method sets the choice path indexes.
	 * 
	 * @param indexes The choice path indexes
	 */
	public void getPathIndexes(java.util.List<Integer> indexes) {
		_pathIndexes = indexes;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean evaluate(SessionType type, int index, SessionScope scope) {
		
		java.util.List<SessionScope> subs=new java.util.ArrayList<SessionScope>();
		
		// Create a sub-scope per path
		for (int i=0; i < _pathIndexes.size(); i++) {
			SessionScope sub=new SessionScope();
			
			if (Node._nameSessions) {
				sub.setName("Choice/"+index+"/"+i);
			}
			
			int subIndex=_pathIndexes.get(i);
			
			Node node=type.getNode(subIndex);
			
			if (node.evaluate(type, subIndex, sub)) {
				sub.setCompletionIndex(getNext());
				scope.addSubScope(sub);
				return (false);
			} else {
				subs.add(sub);
			}
		}
		
		// Need to add sub scopes to mutually exclusive scope
		SessionScope choiceScope=new SessionScope();
		
		if (Node._nameSessions) {
			choiceScope.setName("Choice/"+index);
		}
		
		choiceScope.setCompletionIndex(getNext());
		choiceScope.setMutuallyExclusive(true);
		choiceScope.setSubScopes(subs);
		
		scope.addSubScope(choiceScope);
		
		return (false);
	}

}
