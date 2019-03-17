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
package org.scribble.ast.name.simple;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.NameNode;
import org.scribble.type.kind.Kind;

// "Identifier" in parser grammar
public abstract class SimpleNameNode<K extends Kind> extends NameNode<K>
{
	// ScribTreeAdaptor#create constructor
	public SimpleNameNode(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected SimpleNameNode(SimpleNameNode<K> node)//, String id)
	{
		super(node);
	}
	
	public String getText()
	{
		return getLastElement();  // i.e., second child element, after node type
	}
	
	
	
	
	
	
	
	
	
	
	
	public SimpleNameNode(CommonTree source, String id)
	{
		super(source, new String[]{id});
	}
}
