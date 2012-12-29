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
package org.scribble.protocol.parser.antlr;

import java.util.Stack;

import org.scribble.protocol.model.local.LActivity;
import org.scribble.protocol.model.local.LBlock;

/**
 * This class provides the model adapter for the 'localInteractionBlock' parser rule.
 *
 */
public class LocalInteractionBlockModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(Stack<Object> components) {
		LBlock ret=new LBlock();
		
		while (components.peek() instanceof LActivity) {
			ret.getContents().add(0, (LActivity)components.pop());
		}
		
		components.push(ret);
		
		return ret;
	}

}
