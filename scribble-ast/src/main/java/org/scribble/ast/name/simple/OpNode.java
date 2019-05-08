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
import org.scribble.del.DelFactory;


public class OpNode extends SimpleNameNode<OpKind>
{
	// Determine by token text -- cannot use token type int in core, value is a ScribbleParser constant
	public static final String EMPTY_OP_TOKEN_TEXT = "__EMPTY_OP";  // Cf. Scribble.g

	public OpNode(Token t)
	{
		super(t);
	}

	// Scribble.g, IDENTIFIER<...Node>[$IDENTIFIER]
	// ttype is just ScribblParser.IDENTIFIER, t is the IDENTIFIER token
	public OpNode(int ttype, Token t)
	{
		this(t);
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
	public void decorateDel(DelFactory df)
	{
		df.OpNode(this);
	}
	
	@Override
	public Op toName()
	{
		String id = getText();
		if (id.equals(EMPTY_OP_TOKEN_TEXT))
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
	public boolean canEquals(Object o)
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
