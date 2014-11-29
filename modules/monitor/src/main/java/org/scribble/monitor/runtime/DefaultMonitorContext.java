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
package org.scribble.monitor.runtime;

import org.scribble.monitor.model.MessageNode;

public class DefaultMonitorContext implements MonitorContext {

	private java.util.List<MessageComparatorFactory> _messageComparatorFactories=
						new java.util.ArrayList<MessageComparatorFactory>();
	
	/**
	 * This method registers a message comparator factory.
	 * 
	 * @param factory The message comparator factory
	 */
	public void register(MessageComparatorFactory factory) {
		_messageComparatorFactories.add(factory);
	}
	
	/**
	 * This method unregisters a message comparator factory.
	 * 
	 * @param factory The message comparator factory
	 */
	public void unregister(MessageComparatorFactory factory) {
		_messageComparatorFactories.remove(factory);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public MessageComparator getMessageComparator(MessageNode node) {
		
		// Iterate though the registered factories to see whether any are
		// appropriate for the supplied message node.
		for (MessageComparatorFactory factory : _messageComparatorFactories) {
			MessageComparator comparator=factory.create(node);
			if (comparator != null) {
				return (comparator);
			}
		}
		
		// Create the default comparator
		return (new MonitorMessageComparator(node));
	}

}
