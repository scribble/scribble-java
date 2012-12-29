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
import org.scribble.protocol.model.FullyQualifiedName;

/**
 * This class provides the model adapter for the 'packageDecl' parser rule.
 *
 */
public class PackageDeclModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(Stack<Object> components) {
		Object component=components.pop();
		String packageName="";
		FullyQualifiedName ret=null;
		
		do {
			if (component instanceof CommonToken) {
				packageName = ((CommonToken)component).getText()+packageName;
			}
			
			component = components.pop();
			
		} while (!(component instanceof CommonToken && ((CommonToken)component).getText().equals("package")));

		if (packageName.length() > 0) {
			ret = new FullyQualifiedName();
			ret.setName(packageName);
			
			components.push(ret);
		}
		
		return ret;
	}

}
