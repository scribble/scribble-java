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
import org.scribble.protocol.model.Role;
import org.scribble.protocol.model.global.GBlock;
import org.scribble.protocol.model.global.GChoice;

/**
 * This class provides the model adapter for the 'choice' parser rule.
 *
 */
public class ChoiceModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(Stack<Object> components) {
		GChoice ret=new GChoice();
		
		while (components.peek() instanceof GBlock) {
			ret.getPaths().add(0, (GBlock)components.pop());
			
			if (components.peek() instanceof CommonToken
					&& ((CommonToken)components.peek()).getText().equals("or")) {
				components.pop();
			}
		}
		
		ret.setRole((Role)components.pop());
		
		components.pop(); // at
		components.pop(); // choice
		
		components.push(ret);
		
		return ret;
	}

}
