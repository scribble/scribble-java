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
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.NameNode;
import org.scribble.type.kind.Kind;
import org.scribble.type.name.Name;

public abstract class NameDeclNode<K extends Kind> extends ScribNodeBase
{ 
	// ScribTreeAdaptor#create constructor
	public NameDeclNode(Token t)
	{
		super(t);
		this.name = null;
	}

	// Tree#dupNode constructor
	public NameDeclNode(NameDeclNode<K> node)
	{
		super(node);
		this.name = null;
	}	

	protected final NameNode<?> getRawNameNodeChild()
	{
		if (getChildCount() != 1)
		{
			throw new RuntimeException("Shouldn't get in here: " + getChildren());
		}
		NameNode<?> name = (NameNode<?>) getChild(0);
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
	
	// reconstruct left to subclass-specific, extra fields
	//protected abstract NameDeclNode<K> reconstruct(NameNode<K> name);
	
	
	
	
	
	
	
	private final NameNode<K> name;
	
	protected NameDeclNode(CommonTree source, NameNode<K> name)
	{
		super(source);
		this.name = name;
	}
}
