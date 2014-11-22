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
import org.scribble.trace.model.MonitorRoleSimulator;
import org.scribble.trace.model.Role;

/**
 * This class provides the model adapter for the 'role' parser rule.
 *
 */
public class RoleModelAdaptor extends AbstractModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(ParserContext context) {
		Role ret=new Role();
		MonitorRoleSimulator sim=new MonitorRoleSimulator();
		
		// Initialize trace details
		
		Object component=context.pop();
		String text="";

		if (component instanceof CommonToken
				&& ((CommonToken)component).getText().equals(";")) {
			component = context.pop(); // Replace ';'
		}
		
		do {
			if (component instanceof CommonToken) {
				
				if (((CommonToken)component).getText().equals("role")) {
					ret.setName(text);
					text = "";
					break;
				} else if (((CommonToken)component).getText().equals("as")) {
					sim.setRole(text);
					text = "";
				} else if (((CommonToken)component).getText().equals("protocol")) {
					sim.setProtocol(text);
					text = "";
				} else if (((CommonToken)component).getText().equals("simulating")) {
					sim.setModule(text);
					text = "";
					ret.setSimulator(sim);
				} else {
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
