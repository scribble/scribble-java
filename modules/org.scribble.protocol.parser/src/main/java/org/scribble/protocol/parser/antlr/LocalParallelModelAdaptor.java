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

import org.antlr.runtime.CommonToken;
import org.scribble.protocol.model.local.LBlock;
import org.scribble.protocol.model.local.LParallel;

/**
 * This class provides the model adapter for the 'localParallel' parser rule.
 *
 */
public class LocalParallelModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(Stack<Object> components) {
		LParallel ret=new LParallel();
		
		while (components.peek() instanceof LBlock) {
			ret.getPaths().add(0, (LBlock)components.pop());
			
			if (components.peek() instanceof CommonToken
					&& ((CommonToken)components.peek()).getText().equals("and")) {
				components.pop();
			}
		}
		
		components.pop(); // par
		
		components.push(ret);
		
		return ret;
	}

}
