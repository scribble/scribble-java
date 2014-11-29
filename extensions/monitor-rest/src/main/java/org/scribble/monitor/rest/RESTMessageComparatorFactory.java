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
package org.scribble.monitor.rest;

import org.scribble.monitor.model.MessageNode;
import org.scribble.monitor.runtime.MessageComparator;
import org.scribble.monitor.runtime.MessageComparatorFactory;

/**
 * This class provides the REST implementation of the message comparator factory.
 *
 */
public class RESTMessageComparatorFactory implements MessageComparatorFactory {

	private static final String REST = "REST";

	/**
	 * {@inheritDoc}
	 */
	public MessageComparator create(MessageNode node) {
		if (node.getAnnotation(REST) != null) {
			return (new RESTMessageComparator(node));
		}
		return (null);
	}
	
}
