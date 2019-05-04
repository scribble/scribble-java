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
import org.scribble.ast.MsgNode;
import org.scribble.core.type.kind.SigKind;
import org.scribble.core.type.name.SigName;
import org.scribble.del.DelFactory;

public class SigParamNode extends NonRoleParamNode<SigKind>
		implements MsgNode
{
	// Scribble.g, IDENTIFIER<...Node>[$IDENTIFIER]
	// N.B. ttype (an "imaginary node" type) is discarded, t is a ScribbleParser.ID token type
	public SigParamNode(int ttype, Token t)
	{
		super(t, SigKind.KIND);
	}

	// Tree#dupNode constructor
	protected SigParamNode(SigParamNode node)
	{
		super(node);
	}
	
	@Override
	public SigParamNode dupNode()
	{
		return new SigParamNode(this);
	}

	@Override
	public void decorateDel(DelFactory df)
	{
		df.SigParamNode(this);
	}

  // N.B. no "SigParamName"
	@Override
	public SigName toName()
	{
		return new SigName(getText());
	}

	@Override
	public SigName toArg()
	{
		return toMsg();
	}

	@Override
	public SigName toMsg()
	{
		return toName();
	}

	@Override
	public boolean isSigParamNode()
	{
		return true;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 8609;
		hash = 31 * super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof SigParamNode))
		{
			return false;
		}
		return super.equals(o);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof SigParamNode;
	}
}
