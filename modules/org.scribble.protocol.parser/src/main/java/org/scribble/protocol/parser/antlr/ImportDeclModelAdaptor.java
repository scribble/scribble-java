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
import org.scribble.protocol.model.ImportDecl;

/**
 * This class provides the model adapter for the 'importDecl' parser rule.
 *
 */
public class ImportDeclModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(Stack<Object> components) {
		
		ImportDecl ret=new ImportDecl();

		String text=((CommonToken)components.pop()).getText();
		
		if (((CommonToken)components.peek()).getText().equals("as")) {
			components.pop(); // as
			ret.setAlias(text);
			
			text=((CommonToken)components.pop()).getText();
		}
		
		while (((CommonToken)components.peek()).getText().equals(".")) {
			text = ((CommonToken)components.pop()).getText()+text;
			text = ((CommonToken)components.pop()).getText()+text;
		}
		
		ret.setModuleName(new FullyQualifiedName(text));
		
		if (((CommonToken)components.peek()).getText().equals("from")) {
			components.pop(); // from
			ret.setMemberName(((CommonToken)components.pop()).getText());
		}

		components.pop(); // import
		
		components.push(ret);
			
		return ret;
	}

}
