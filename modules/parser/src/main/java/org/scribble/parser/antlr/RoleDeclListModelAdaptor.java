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
import org.scribble.model.RoleDecl;

/**
 * This class provides the model adapter for the 'roleDef' parser rule.
 *
 */
public class RoleDeclListModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(ParserContext context) {
		java.util.List<RoleDecl> ret=new java.util.ArrayList<RoleDecl>();
		boolean f_iterate=false;
		
		// consume ')'
		context.pop();
		
		do {
			f_iterate = false;
			
			RoleDecl rd=new RoleDecl();
			rd.setName(((CommonToken)context.pop()).getText());
		
			if (context.peek() instanceof CommonToken
					&& ((CommonToken)context.peek()).getText().equals("as")) {
				context.pop(); // 'as'
				
				rd.setAlias(rd.getName());
				
				rd.setName(((CommonToken)context.pop()).getText());
			}

			context.pop(); // role
			
			ret.add(0, rd);
			
			if (context.peek() instanceof CommonToken
					&& ((CommonToken)context.peek()).getText().equals(",")) {
				context.pop(); // ,
				f_iterate = true;
			}
		} while (f_iterate);
		
		// consume '('
		context.pop();
		
		context.push(ret);
		
		return ret;
	}

}
