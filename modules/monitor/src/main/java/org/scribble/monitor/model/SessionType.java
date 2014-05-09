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

import org.scribble.monitor.SessionInstance;
import org.scribble.monitor.SessionScope;

/**
 * This class represents the monitorable version of a local protocol.
 *
 */
public class SessionType {

	private java.util.List<Node> _nodes=new java.util.ArrayList<Node>();
	
	/**
	 * This method returns the nodes.
	 * 
	 * @return The nodes
	 */
	public java.util.List<Node> getNodes() {
		return (_nodes);
	}
	
	/**
	 * This method sets the nodes.
	 * 
	 * @param nodes The nodes
	 */
	public void setNodes(java.util.List<Node> nodes) {
		_nodes = nodes;
	}
	
	/**
	 * This method returns the node at the specified index.
	 * 
	 * @param index The index
	 * @return The node
	 */
	public Node getNode(int index) {
		return (_nodes.get(index));
	}
	
	/**
	 * This method initializes the supplied session instance to monitor
	 * against this session type.
	 * 
	 * @param instance The new session instance
	 */
	public void initialize(SessionInstance instance) {
		
		// Create a new top level session scope
		SessionScope scope=new SessionScope();
		
		if (Node._nameSessions) {
			scope.setName("Main");
		}
				
		Node node=getNode(0);
		 
		node.evaluate(this, 0, scope);
		
		instance.setScope(scope);
	}
	
}
