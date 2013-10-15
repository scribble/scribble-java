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
import org.scribble.model.PayloadTypeDecl;

/**
 * This class provides the model adapter for the 'payloadTypeDecl' parser rule.
 *
 */
public class PayloadTypeDeclModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(ParserContext context) {
		
		context.pop(); // Consume ';'
		
		String alias=((CommonToken)context.pop()).getText();
		context.pop(); // as
		String schema=((CommonToken)context.pop()).getText();
		context.pop(); // from
		String type=((CommonToken)context.pop()).getText();
		context.pop(); // >
		String format=((CommonToken)context.pop()).getText();
		context.pop(); // <
		context.pop(); // type
		
		schema = schema.substring(1, schema.length()-1);
		type = type.substring(1, type.length()-1);
		
		PayloadTypeDecl ret=new PayloadTypeDecl();
		ret.setAlias(alias);
		ret.setSchema(schema);
		ret.setType(type);
		ret.setFormat(format);
		
		context.push(ret);
			
		return ret;
	}

}
