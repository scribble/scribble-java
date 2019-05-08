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
import org.scribble.del.DelFactory;

public class RecVarNode extends SimpleNameNode<RecVarKind>
{
	// Scribble.g, IDENTIFIER<...Node>[$IDENTIFIER]
	// N.B. ttype (an "imaginary node" type) is discarded, t is a ScribbleParser.ID token type
	public RecVarNode(int ttype, Token t)
	{
		super(t);
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
	public void decorateDel(DelFactory df)
	{
		df.RecVarNode(this);
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
	public boolean canEquals(Object o)
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
