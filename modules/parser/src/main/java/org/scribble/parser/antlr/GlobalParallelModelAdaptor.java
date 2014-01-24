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
package org.scribble.parser.antlr;

import org.antlr.runtime.CommonToken;
import org.scribble.model.global.GBlock;
import org.scribble.model.global.GParallel;

/**
 * This class provides the model adapter for the 'parallel' parser rule.
 *
 */
public class GlobalParallelModelAdaptor extends AbstractModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(ParserContext context) {
		GParallel ret=new GParallel();
		
		while (context.peek() instanceof GBlock) {
			ret.getPaths().add(0, (GBlock)context.pop());
			
			if (context.peek() instanceof CommonToken
					&& ((CommonToken)context.peek()).getText().equals("and")) {
				context.pop();
			}
		}
		
		setEndProperties(ret, ret.getPaths().get(ret.getPaths().size()-1));
		
		setStartProperties(ret, context.pop()); // par
		
		context.push(ret);
		
		return ret;
	}

}
