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

import org.antlr.runtime.CommonToken;
import org.scribble.protocol.model.local.LBlock;
import org.scribble.protocol.model.local.LInterruptible;

/**
 * This class provides the model adapter for the 'interruptible' parser rule.
 *
 */
public class LocalInterruptibleModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(ParserContext context) {
		LInterruptible ret=new LInterruptible();
		
		while (context.peek() instanceof LInterruptible.Catch) {
			ret.getCatches().add(0, (LInterruptible.Catch)context.pop());
		}
		
		if (context.peek() instanceof LInterruptible.Throw) {
			ret.setThrows((LInterruptible.Throw)context.pop());
		}
		
		ret.setBlock((LBlock)context.pop());
		
		if (((CommonToken)context.peek()).getText().equals(":")) {
			context.pop(); // ':'
			
			ret.setScope(((CommonToken)context.pop()).getText());
		}
		
		context.pop(); // interruptible
		
		context.push(ret);
		
		return ret;
	}

}
