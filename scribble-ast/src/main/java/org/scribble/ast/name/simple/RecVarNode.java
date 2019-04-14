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
import org.scribble.core.type.kind.RecVarKind;
import org.scribble.core.type.name.RecVar;

public class RecVarNode extends SimpleNameNode<RecVarKind>
{
	// ScribTreeAdaptor#create constructor
	public RecVarNode(Token t)
	{
		super(t);
	}

	// Scribble.g, IDENTIFIER<RecVarNode>[$IDENTIFIER]
	public RecVarNode(int ttype, Token t)
	{
		this(t);
		System.out.println("VVVV: " + ttype + " ,, "+ t + " ,, " + t.getType());
	}

	// Tree#dupNode constructor
	protected RecVarNode(RecVarNode node)
	{
		super(node);
	}
	
	@Override
	public RecVarNode dupNode()
	{
		return new RecVarNode(this);
	}

	@Override
	public RecVar toName()
	{
		return new RecVar(getText());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RecVarNode))
		{
			return false;
		}
		return super.equals(o);  // Does canEqual
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof RecVarNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 349;
		hash = 31 * super.hashCode();
		return hash;
	}
}
