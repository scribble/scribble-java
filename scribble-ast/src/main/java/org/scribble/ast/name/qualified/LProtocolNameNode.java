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
package org.scribble.ast.name.qualified;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.LProtocolName;
import org.scribble.core.type.session.Arg;

public class LProtocolNameNode extends ProtocolNameNode<Local>
		implements PayloadElemNameNode<Local>
{
	// ScribTreeAdaptor#create constructor
	public LProtocolNameNode(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected LProtocolNameNode(LProtocolNameNode node)
	{
		super(node);
	}
	
	@Override
	public LProtocolNameNode dupNode()
	{
		return new LProtocolNameNode(this);
	}
	
	@Override
	public LProtocolName toName()
	{
		LProtocolName membname = new LProtocolName(getLastElement());
		return isPrefixed()
				? new LProtocolName(getModuleNamePrefix(), membname)
				: membname;
	}

	@Override
	public LProtocolName toPayloadType()
	{
		return toName();
	}

	@Override
	public Arg<Local> toArg()
	{
		return toPayloadType();
	}
		
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof LProtocolNameNode))
		{
			return false;
		}
		return super.equals(o);  // Does canEqual
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof LProtocolNameNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 421;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
}
