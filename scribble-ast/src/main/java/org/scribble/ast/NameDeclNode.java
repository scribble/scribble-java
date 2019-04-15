/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.ast;

import org.antlr.runtime.Token;
import org.scribble.ast.name.NameNode;
import org.scribble.core.type.kind.Kind;
import org.scribble.core.type.name.Name;

public abstract class NameDeclNode<K extends Kind> extends ScribNodeBase
{ 
	public static final int NAMENODE_CHILD_INDEX = 0;

	// ScribTreeAdaptor#create constructor
	public NameDeclNode(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public NameDeclNode(NameDeclNode<K> node)
	{
		super(node);
	}	

	// CHECKME: always AmbigNameNode?
	protected final NameNode<?> getRawNameNodeChild()
	{
		if (getChildCount() < 1)  // E.g., ProtocolHeader three children
		{
			throw new RuntimeException("Shouldn't get in here: " + getClass());
		}
		NameNode<?> name = (NameNode<?>) getChild(NAMENODE_CHILD_INDEX);  
		return name;
	}

	// Concrete subclasses should use getRawNameNodeChild() and cast 
	// (Avoids needing to explicitly record the kind, cf. NonRoleParamDecl)
	// (Gets overridden anyway for return type)
	public abstract NameNode<K> getNameNodeChild();

	// Return: *simple* name (cf. ModuleDecl)
	// Concrete subclasses should use getNameNode, toName (simple name) and cast
	// (Gets overridden anyway for return type)
	public abstract Name<K> getDeclName();
}
