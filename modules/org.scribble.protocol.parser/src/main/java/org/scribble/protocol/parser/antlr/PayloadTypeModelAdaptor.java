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
import org.scribble.protocol.model.PayloadType;

/**
 * This class provides the model adapter for the 'payloadType' parser rule.
 *
 */
public class PayloadTypeModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(Stack<Object> components) {
		
		PayloadType ret=new PayloadType();

		String text=((CommonToken)components.pop()).getText();
		
		if (components.peek() instanceof CommonToken
				&& ((CommonToken)components.peek()).getText().equals(":")) {
			components.pop(); // :
			ret.setVariable(text);
			
			text=((CommonToken)components.pop()).getText();
		}
		
		ret.setType(text);
		
		components.push(ret);
			
		return ret;
	}

}
