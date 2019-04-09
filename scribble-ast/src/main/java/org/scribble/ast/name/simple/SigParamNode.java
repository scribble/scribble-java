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
import org.scribble.ast.MessageNode;
import org.scribble.core.type.kind.SigKind;
import org.scribble.core.type.name.MessageSigName;

public class SigParamNode extends NonRoleParamNode<SigKind>
		implements MessageNode
{
	// ScribTreeAdaptor#create constructor
	public SigParamNode(Token t)
	{
		super(t, SigKind.KIND);
	}

	// Tree#dupNode constructor
	protected SigParamNode(SigParamNode node)//, String id)
	{
		super(node);
	}
	
	@Override
	public SigParamNode dupNode()
	{
		if (getChildren().isEmpty())
		{
			throw new RuntimeException();
		}
		return new SigParamNode(this);//, getIdentifier());
	}
	
	@Override
	public MessageSigName toName()
	{
		return new MessageSigName(getText());
	}

	@Override
	public MessageSigName toArg()
	{
		return toMessage();
	}

	@Override
	public MessageSigName toMessage()
	{
		return toName();
	}

	@Override
	public boolean isTypeParamNode()
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
	public boolean canEqual(Object o)
	{
		return o instanceof SigParamNode;
	}
}
