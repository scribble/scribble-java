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
import org.scribble.trace.model.Message;
import org.scribble.trace.model.MessageTransfer;

/**
 * This class provides the model adapter for the 'message transfer' parser rule.
 *
 */
public class MessageTransferModelAdaptor extends AbstractModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(ParserContext context) {
		MessageTransfer ret=new MessageTransfer();
		Message mesg=new Message();
		ret.setMessage(mesg);
		
		// Consume ';'
		context.pop();

		ret.getToRoles().add(((CommonToken)context.pop()).getText());
		
		while (((CommonToken)context.peek()).getText().equals(",")) {
			context.pop();
			
			ret.getToRoles().add(((CommonToken)context.pop()).getText());
		}
		
		// Consume 'to'
		context.pop();
		
		ret.setFromRole(((CommonToken)context.pop()).getText());
		
		// Consume 'from'
		context.pop();
		
		if (context.peek() instanceof CommonToken && ((CommonToken)context.peek()).getText().equals(")")) {
			// Consume ')'
			context.pop();
			
			do {
				String text=((CommonToken)context.pop()).getText();
				
				if (context.peek() instanceof CommonToken && ((CommonToken)context.peek()).getText().equals("=")) {
					mesg.getValues().add(0, stripQuotes(text));
					
					// Consume '='
					context.pop();
					
					mesg.getTypes().add(0, stripQuotes(((CommonToken)context.pop()).getText()));
					
				} else {
					mesg.getTypes().add(0, stripQuotes(text));
				}
			} while (((CommonToken)context.pop()).getText().equals(","));
		}

		ret.getMessage().setOperator(((CommonToken)context.pop()).getText());
		
		//setStartProperties(ret, component);
		
		context.push(ret);
		
		return ret;
	}

	protected String stripQuotes(String text) {
		return (text.substring(1, text.length()-1));
	}
}
