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
import org.scribble.model.ParameterDecl;

/**
 * This class provides the model adapter for the 'roleDef' parser rule.
 *
 */
public class ParameterDeclListModelAdaptor extends AbstractModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(ParserContext context) {
		java.util.List<ParameterDecl> ret=new java.util.ArrayList<ParameterDecl>();
		boolean f_iterate=false;
		
		// consume '>'
		context.pop();
		
		do {
			f_iterate = false;
			
			ParameterDecl pd=new ParameterDecl();
			
			setEndProperties(pd, context.peek());
			
			pd.setName(((CommonToken)context.pop()).getText());
		
			if (context.peek() instanceof CommonToken
					&& ((CommonToken)context.peek()).getText().equals("as")) {
				context.pop(); // 'as'
				
				pd.setAlias(pd.getName());
				
				pd.setName(((CommonToken)context.pop()).getText());
			}
			
			setStartProperties(pd, context.peek());

			String type=((CommonToken)context.pop()).getText();
			
			if (type.equals("type")) {
				pd.setType(ParameterDecl.ParameterType.Type);
			} else if (type.equals("sig")) {
				pd.setType(ParameterDecl.ParameterType.Sig);
			}
			
			ret.add(0, pd);
			
			if (context.peek() instanceof CommonToken
					&& ((CommonToken)context.peek()).getText().equals(",")) {
				context.pop(); // ,
				f_iterate = true;
			}
		} while (f_iterate);
		
		// consume '<'
		context.pop();
		
		context.push(ret);
		
		return ret;
	}

}
