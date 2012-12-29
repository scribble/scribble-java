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
import org.scribble.protocol.model.Role;
import org.scribble.protocol.model.RoleInstantiation;

/**
 * This class provides the model adapter for the 'roleInstantiationList' parser rule.
 *
 */
public class RoleInstantiationListModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(Stack<Object> components) {
		java.util.List<RoleInstantiation> ret=new java.util.ArrayList<RoleInstantiation>();
		boolean f_iterate=false;
		
		do {
			f_iterate = false;
			
			RoleInstantiation ri=new RoleInstantiation();
			
			ri.setAs((Role)components.pop());
			
			components.pop(); // as
			
			ri.setRole((Role)components.pop());
			
			ret.add(0, ri);
			
			if (components.peek() instanceof CommonToken
					&& ((CommonToken)components.peek()).getText().equals(",")) {
				components.pop(); // ,
				f_iterate = true;
			}
		} while (f_iterate);
		
		components.push(ret);
		
		return ret;
	}

}
