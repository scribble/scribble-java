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
import org.scribble.core.type.kind.OpKind;
import org.scribble.core.type.name.Op;


public class OpNode extends SimpleNameNode<OpKind>
{
	public static final String EMPTY_OP_ID = "EMPTY_OPERATOR";

	// ScribTreeAdaptor#create constructor
	public OpNode(Token t)
	{
		super(t);
	}

	// Scribble.g, IDENTIFIER<RecVarNode>[$IDENTIFIER]
	public OpNode(int ttype, Token t)
	{
		this(t);
		System.out.println("OOOOO: " + ttype + " ,, "+ t + " ,, " + t.getType() + " ,, " + toName());
	}

	// Tree#dupNode constructor
	protected OpNode(OpNode node)
	{
		super(node);
	}
	
	@Override
	public OpNode dupNode()
	{
		return new OpNode(this);
	}
	
	@Override
	public Op toName()
	{
		String id = getText();
		if (id.equals(EMPTY_OP_ID))
		{
			return Op.EMPTY_OP;
		}
		return new Op(id);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof OpNode))
		{
			return false;
		}
		return super.equals(o);  // Does canEqual
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof OpNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 347;
		hash = 31 * super.hashCode();
		return hash;
	}
}
