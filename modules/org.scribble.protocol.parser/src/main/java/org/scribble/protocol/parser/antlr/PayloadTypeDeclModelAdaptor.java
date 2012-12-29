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
import org.scribble.protocol.model.PayloadTypeDecl;

/**
 * This class provides the model adapter for the 'payloadTypeDecl' parser rule.
 *
 */
public class PayloadTypeDeclModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(Stack<Object> components) {
		
		String alias=((CommonToken)components.pop()).getText();
		components.pop(); // as
		String schema=((CommonToken)components.pop()).getText();
		components.pop(); // from
		String type=((CommonToken)components.pop()).getText();
		components.pop(); // >
		String format=((CommonToken)components.pop()).getText();
		components.pop(); // <
		components.pop(); // type
		
		schema = schema.substring(1, schema.length()-1);
		type = type.substring(1, type.length()-1);
		
		PayloadTypeDecl ret=new PayloadTypeDecl();
		ret.setAlias(alias);
		ret.setSchema(schema);
		ret.setType(type);
		ret.setFormat(format);
		
		components.push(ret);
			
		return ret;
	}

}
