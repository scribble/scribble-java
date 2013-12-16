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
import org.scribble.model.Argument;
import org.scribble.model.FullyQualifiedName;
import org.scribble.model.RoleInstantiation;
import org.scribble.model.global.GDo;

/**
 * This class provides the model adapter for the 'doDef' parser rule.
 *
 */
public class GlobalDoModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Object createModelObject(ParserContext context) {		
		GDo ret=new GDo();

		context.pop(); // ';'
		
		ret.getRoleInstantiations().addAll((java.util.List<RoleInstantiation>)context.pop());
		
		if (context.peek() instanceof java.util.List) {
			ret.getArguments().addAll((java.util.List<Argument>)context.pop());
		}
		
		StringBuffer protocol=new StringBuffer(((CommonToken)context.pop()).getText());
		
		// Check for module and set as separate property
		while (context.peek() instanceof CommonToken
				&& ((CommonToken)context.peek()).getText().equals(".")) {
			context.pop(); // consume '.'
			
			protocol.insert(0, ".");
			
			protocol.insert(0, ((CommonToken)context.pop()).getText());
		}
		
		ret.setProtocol(new FullyQualifiedName(protocol.toString()));
		
		if (context.peek() instanceof CommonToken
				&& ((CommonToken)context.peek()).getText().equals(":")) {
			context.pop(); // consume ':'
			
			ret.setScopeName(((CommonToken)context.pop()).getText());
		}
		
		context.pop(); // do

		context.push(ret);
			
		return ret;
	}

}
