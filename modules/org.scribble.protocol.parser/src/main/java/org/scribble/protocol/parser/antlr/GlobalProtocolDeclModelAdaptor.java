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
import org.scribble.protocol.model.RoleDefn;
import org.scribble.protocol.model.global.GBlock;
import org.scribble.protocol.model.global.GProtocol;

/**
 * This class provides the model adapter for the 'globalProtocolDecl' parser rule.
 *
 */
public class GlobalProtocolDeclModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(Stack<Object> components) {
		GProtocol ret=new GProtocol();
		
		ret.setBlock((GBlock)components.pop());
		
		while (components.peek() instanceof RoleDefn) {
			ret.getRoleDefinitions().add(0, (RoleDefn)components.pop());			
		}
		
		ret.setName(((CommonToken)components.pop()).getText());
		
		components.pop(); // protocol
		components.pop(); // global
		
		components.push(ret);
		
		return ret;
	}

}
