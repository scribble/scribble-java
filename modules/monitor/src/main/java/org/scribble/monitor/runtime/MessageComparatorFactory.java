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

/**
 * This interface represents a message comparator factory.
 *
 */
public interface MessageComparatorFactory {

	/**
	 * This method creates a new MessageComparator instance if the
	 * factory is appropriate for the supplied message node.
	 * 
	 * @param node The message node
	 * @return The message comparator, or null if factory not appropriate for the message node
	 */
	public MessageComparator create(MessageNode node);
	
}
