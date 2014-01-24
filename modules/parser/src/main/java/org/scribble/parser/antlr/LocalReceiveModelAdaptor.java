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
import org.scribble.model.Message;
import org.scribble.model.Role;
import org.scribble.model.local.LReceive;

/**
 * This class provides the model adapter for the 'receive' parser rule.
 *
 */
public class LocalReceiveModelAdaptor extends AbstractModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(ParserContext context) {
		
		LReceive ret=new LReceive();

		setEndProperties(ret, context.pop()); // ';'

		Role r=new Role();
		
		setStartProperties(r, context.peek());
		setEndProperties(r, context.peek());
		
		r.setName(((CommonToken)context.pop()).getText());

		ret.setFromRole(r);
		
		context.pop(); // from
	
		ret.setMessage((Message)context.pop());
		
		setStartProperties(ret, ret.getMessage());
		
		context.push(ret);
			
		return ret;
	}

}
