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
import org.scribble.model.FullyQualifiedName;
import org.scribble.model.ImportDecl;

/**
 * This class provides the model adapter for the 'importDecl' parser rule.
 *
 */
public class ImportDeclModelAdaptor extends AbstractModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(ParserContext context) {
		
		ImportDecl ret=new ImportDecl();
		
		setEndProperties(ret, context.pop()); // consume ';'

		String text=((CommonToken)context.pop()).getText();
		
		if (((CommonToken)context.peek()).getText().equals("as")) {
			context.pop(); // as
			ret.setAlias(text);
			
			ret.setMemberName(((CommonToken)context.pop()).getText());
			
			context.pop(); // import
			
			text=((CommonToken)context.pop()).getText();
		} else if (((CommonToken)context.peek()).getText().equals("import")) {
			context.pop(); // import

			ret.setMemberName(text);
			
			text=((CommonToken)context.pop()).getText();
		}
		
		while (((CommonToken)context.peek()).getText().equals(".")) {
			text = ((CommonToken)context.pop()).getText()+text;
			text = ((CommonToken)context.pop()).getText()+text;
		}
		
		ret.setModuleName(new FullyQualifiedName(text));
		
		setStartProperties(ret, context.pop()); // consume 'from' or 'import' depending on which path was taken

		/* Import first approach....
		 * 
		 *
		String text=((CommonToken)context.pop()).getText();
		
		if (((CommonToken)context.peek()).getText().equals("as")) {
			context.pop(); // as
			ret.setAlias(text);
			
			text=((CommonToken)context.pop()).getText();
		}
		
		while (((CommonToken)context.peek()).getText().equals(".")) {
			text = ((CommonToken)context.pop()).getText()+text;
			text = ((CommonToken)context.pop()).getText()+text;
		}
		
		ret.setModuleName(new FullyQualifiedName(text));
		
		if (((CommonToken)context.peek()).getText().equals("from")) {
			context.pop(); // from
			ret.setMemberName(((CommonToken)context.pop()).getText());
		}

		context.pop(); // import
		*/
		
		context.push(ret);
			
		return ret;
	}

}
