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

import org.scribble.protocol.model.FullyQualifiedName;
import org.scribble.protocol.model.ImportDecl;
import org.scribble.protocol.model.Module;
import org.scribble.protocol.model.PayloadTypeDecl;
import org.scribble.protocol.model.Protocol;

/**
 * This class provides the model adapter for the 'module' parser rule.
 *
 */
public class ModuleModelAdaptor implements ModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	public Object createModelObject(Stack<Object> components) {
		Module ret=new Module();
		
		while (components.peek() instanceof Protocol) {
			ret.getProtocols().add(0, (Protocol)components.pop());
		}
		
		while (components.peek() instanceof PayloadTypeDecl) {
			ret.getTypeDeclarations().add(0, (PayloadTypeDecl)components.pop());
		}

		while (components.peek() instanceof ImportDecl) {
			ret.getImports().add(0, (ImportDecl)components.pop());
		}

		if (components.peek() instanceof FullyQualifiedName) {
			ret.setPackage((FullyQualifiedName)components.pop());
		}
		
		components.push(ret);
		
		return ret;
	}

}
