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

import org.scribble.model.FullyQualifiedName;
import org.scribble.model.ImportDecl;
import org.scribble.model.Module;
import org.scribble.model.PayloadTypeDecl;
import org.scribble.model.ProtocolDecl;

/**
 * This class provides the model adapter for the 'module' parser rule.
 *
 */
public class ModuleModelAdaptor extends AbstractModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(ParserContext context) {
		Module ret=new Module();
		
		setEndProperties(ret, context.peek());
		
		while (context.peek() instanceof ProtocolDecl) {
			ret.getProtocols().add(0, (ProtocolDecl)context.pop());
		}
		
		while (context.peek() instanceof PayloadTypeDecl) {
			ret.getPayloadTypeDeclarations().add(0, (PayloadTypeDecl)context.pop());
		}

		while (context.peek() instanceof ImportDecl) {
			ret.getImports().add(0, (ImportDecl)context.pop());
		}

		if (context.peek() instanceof FullyQualifiedName) {
			setStartProperties(ret, context.peek());
			
			ret.setFullyQualifiedName((FullyQualifiedName)context.pop());
		}
		
		context.push(ret);
		
		return ret;
	}

}
