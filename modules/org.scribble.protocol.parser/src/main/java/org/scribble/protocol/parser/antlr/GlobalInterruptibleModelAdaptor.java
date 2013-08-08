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
import org.scribble.protocol.model.global.GBlock;
import org.scribble.protocol.model.global.GInterruptible;

/**
 * This class provides the model adapter for the 'interruptible' parser rule.
 *
 */
public class GlobalInterruptibleModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(ParserContext context) {
		GInterruptible ret=new GInterruptible();
		
		context.pop(); // '}'
		
		while (context.peek() instanceof GInterruptible.Interrupt) {
			ret.getInterrupts().add(0, (GInterruptible.Interrupt)context.pop());
		}
		
		context.pop(); // '{'
		
		context.pop(); // 'with'

		ret.setBlock((GBlock)context.pop());
		
		if (((CommonToken)context.peek()).getText().equals(":")) {
			context.pop(); // ':'
			
			ret.setScope(((CommonToken)context.pop()).getText());
		}
		
		context.pop(); // interruptible
		
		context.push(ret);
		
		return ret;
	}

}
