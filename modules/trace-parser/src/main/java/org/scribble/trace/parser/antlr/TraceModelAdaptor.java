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
package org.scribble.trace.parser.antlr;

import org.antlr.runtime.CommonToken;
import org.scribble.trace.model.Role;
import org.scribble.trace.model.Step;
import org.scribble.trace.model.Trace;

/**
 * This class provides the model adapter for the 'module' parser rule.
 *
 */
public class TraceModelAdaptor extends AbstractModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(ParserContext context) {
		Trace ret=new Trace();
		
		//setEndProperties(ret, context.peek());
		
		while (context.peek() instanceof Step) {
			ret.getSteps().add(0, (Step)context.pop());
		}
		
		while (context.peek() instanceof Role) {
			ret.getRoles().add(0, (Role)context.pop());
		}

		// Initialize trace details
		
		Object component=context.pop();
		String text="";

		if (component instanceof CommonToken
				&& ((CommonToken)component).getText().equals(";")) {
			component = context.pop(); // Replace ';'
		}
		
		do {
			if (component instanceof CommonToken) {
				
				if (((CommonToken)component).getText().equals("trace")) {
					ret.setName(text);
					text = "";
					break;
				} else if (((CommonToken)component).getText().equals("by")) {
					ret.setAuthor(text);
					text = "";
				} else if (((CommonToken)component).getText().equals("shows")) {
					ret.setDescription(text);
					text = "";
				} else {
					if (text.length() > 0) {
						text = " "+text;
					}
					text = ((CommonToken)component).getText()+text;
				}
			}
			
			component = context.pop();
			
		} while (component instanceof CommonToken);

		//setStartProperties(ret, component);
		
		context.push(ret);
		
		return ret;
	}

}
