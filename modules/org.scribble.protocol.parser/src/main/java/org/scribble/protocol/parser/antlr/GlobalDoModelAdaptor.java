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
import org.scribble.protocol.model.FullyQualifiedName;
import org.scribble.protocol.model.MessageSignature;
import org.scribble.protocol.model.RoleInstantiation;
import org.scribble.protocol.model.global.GDo;

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

		context.pop(); // )
		
		ret.getRoleInstantiations().addAll((java.util.List<RoleInstantiation>)context.pop());
		
		context.pop(); // (
		
		if (context.peek() instanceof CommonToken
				&& ((CommonToken)context.peek()).getText().equals(">")) {
			context.pop(); // >
			
			ret.getArguments().addAll((java.util.List<MessageSignature>)context.pop());

			context.pop(); // <
		}
		
		ret.setProtocol(((CommonToken)context.pop()).getText());
		
		// Check for module and set as separate property
		if (context.peek() instanceof CommonToken
				&& ((CommonToken)context.peek()).getText().equals(".")) {
			String fqname="";
			
			context.pop(); // consume '.'
			
			do {
				fqname = ((CommonToken)context.pop()).getText() + fqname;
			} while (!(context.peek() instanceof CommonToken
					&& ((CommonToken)context.peek()).getText().equals("do")));
			
			ret.setModuleName(new FullyQualifiedName(fqname));
		}
		
		context.pop(); // do

		context.push(ret);
			
		return ret;
	}

}
