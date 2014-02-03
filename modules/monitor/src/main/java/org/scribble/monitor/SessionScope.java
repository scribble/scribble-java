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

// TODO: Could try:
// (1) with map of session scopes, with the parent/child relationship defined by scope names, with
// unnamed scopes being given a randomly unique id.
// (2) with subScopes in a map with scope name external to the scope
// NOTE: Some of these impl decisions may have better results for certain protocols/scaling - need
// to identify pros/cons and a way to optimise based on the nature of the protocol.

/**
 * This class represents the state information associated with a scope.
 *
 */
public class SessionScope {

	private String _name;
	private java.util.List<Integer> _indexes;
	private java.util.List<SessionScope> _subScopes;
	private int _completionIndex=-1;
	private boolean _completed=false;
	private boolean _mutuallyExclusive=false;
	private int _catches=-1;
	private int _throws=-1;
	
	/**
	 * The default constructor.
	 */
	public SessionScope() {
	}
	
	/**
	 * This method returns the optional name associated with the scope.
	 * 
	 * @return The name
	 */
	public String getName() {
		return (_name);
	}
	
	/**
	 * This method sets the optional name associated with the scope.
	 * 
	 * @param name The name
	 */
	public void setName(String name) {
		_name = name;
	}
	
	/**
	 * This method returns the list of current nodes associated with the
	 * session instance.
	 * 
	 * @return The current nodes
	 */
	public java.util.List<Integer> getNodeIndexes() {
		return (_indexes);
	}
	
	/**
	 * This method adds an index to the session scope.
	 * 
	 * @param index The index
	 */
	public void addNodeIndex(int index) {
		if (_indexes == null) {
			_indexes = new java.util.ArrayList<Integer>();
		}
		_indexes.add(index);
	}
	
	/**
	 * This method sets the list of node indexes associated with this scope.
	 * 
	 * @param indexes The current nodes
	 */
	public void setNodeIndexes(java.util.List<Integer> indexes) {
		_indexes = indexes;
	}

	/**
	 * This method returns the list of sub-scopes associated with the
	 * session instance.
	 * 
	 * @return The sub-scopes
	 */
	public java.util.List<SessionScope> getSubScopes() {
		return (_subScopes);
	}
	
	/**
	 * This method adds a sub scope to the session's scope.
	 * 
	 * @param sub The sub scope
	 */
	public void addSubScope(SessionScope sub) {
		if (_subScopes == null) {
			_subScopes = new java.util.ArrayList<SessionScope>();
		}
		_subScopes.add(sub);
	}
	
	/**
	 * This method sets the list of sub-scopes associated with this scope.
	 * 
	 * @param subScopes The sub scopes
	 */
	public void setSubScopes(java.util.List<SessionScope> subScopes) {
		_subScopes = subScopes;
	}

	/**
	 * This method returns the optional completion index. If specific
	 * (not -1) then when the scope has no more node indexes or sub-scopes,
	 * then the completion index should be scheduled in the parent scope.
	 * 
	 * @return The completion index, or -1 if not defined
	 */
	public int getCompletionIndex() {
		return (_completionIndex);
	}

	/**
	 * This method returns the optional completion index. If specific
	 * (not -1) then when the scope has no more node indexes or sub-scopes,
	 * then the completion index should be scheduled in the parent scope.
	 * 
	 * @param index The completion index, or -1 if not defined
	 */
	public void setCompletionIndex(int index) {
		_completionIndex = index;
	}
	
	/**
	 * This method sets the completion state of the session scope.
	 * 
	 * @param completed The completion state
	 */
	protected void completed(boolean completed) {
		_completed = completed;
	}
	
	/**
	 * This method determines whether the session scope has completed.
	 * 
	 * @return Whether completed
	 */
	public boolean completed() {
		return (_completed || ((_indexes == null || _indexes.size() == 0)
				&& (_subScopes == null || _subScopes.size() == 0)));
	}
	
	/**
	 * This method returns whether the session scope contains sub-scopes
	 * that are mutually exclusive.
	 * 
	 * @return Whether mutually exclusive
	 */
	public boolean getMutuallyExclusive() {
		return (_mutuallyExclusive);
	}
	
	/**
	 * This method sets whether the session scope contains sub-scopes
	 * that are mutually exclusive.
	 * 
	 * @param me Whether mutually exclusive
	 */
	public void setMutuallyExclusive(boolean me) {
		_mutuallyExclusive = me;
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
	 * This method sets the list of node indexes associated with throw
	 * specifications.
	 * 
	 * @param index The throw node index
	 */
	public void setThrows(int index) {
		_throws = index;
	}

}
