/*
 * Copyright 2009-14 www.scribble.org
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
package org.scribble.trace.simulation;

import org.scribble.monitor.model.MessageNode;
import org.scribble.monitor.runtime.MessageComparator;
import org.scribble.trace.model.Message;

/**
 * This class provides the default message comparator implementation.
 *
 */
public class TraceMessageComparator implements MessageComparator {

	private MessageNode _node;
	
	/**
	 * This is the constructor to initialize the message comparator.
	 * 
	 * @param node The message node
	 */
	public TraceMessageComparator(MessageNode node) {
		_node = node;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isMatch(Object message) {
		boolean ret=false;
		
		if (_node.getOperator().equals(((Message)message).getOperator())
				&& _node.getParameters().size() == ((Message)message).getTypes().size()) {
			
			ret = true;
			
			for (int i=0; ret && i < _node.getParameters().size(); i++) {
				ret = _node.getParameters().get(i).getType().equals(((Message)message).getTypes().get(i));
			}
		}
		
		return (ret);
	}

}
