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
import org.scribble.protocol.model.RoleDefn;

/**
 * This class provides the model adapter for the 'roleDef' parser rule.
 *
 */
public class RoleListModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(ParserContext context) {
		java.util.List<RoleDefn> ret=new java.util.ArrayList<RoleDefn>();
		boolean f_iterate=false;
		
		do {
			f_iterate = false;
			
			RoleDefn rd=new RoleDefn();
			rd.setName(((CommonToken)context.pop()).getText());
		
			context.pop(); // role
			
			ret.add(0, rd);
			
			if (context.peek() instanceof CommonToken
					&& ((CommonToken)context.peek()).getText().equals(",")) {
				context.pop(); // ,
				f_iterate = true;
			}
		} while (f_iterate);
		
		context.push(ret);
		
		return ret;
	}

}
