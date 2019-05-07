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
import org.scribble.ast.name.PayElemNameNode;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.LProtoName;
import org.scribble.core.type.session.Arg;
import org.scribble.del.DelFactory;

public class LProtoNameNode extends ProtoNameNode<Local>
		implements PayElemNameNode<Local>
{
	// ScribTreeAdaptor#create constructor
	public LProtoNameNode(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected LProtoNameNode(LProtoNameNode node)
	{
		super(node);
	}
	
	@Override
	public LProtoNameNode dupNode()
	{
		return new LProtoNameNode(this);
	}

	@Override
	public void decorateDel(DelFactory df)
	{
		df.LProtoNameNode(this);
	}
	
	@Override
	public LProtoName toName()
	{
		LProtoName membname = new LProtoName(getLastElement());
		return isPrefixed()
				? new LProtoName(getModuleNamePrefix(), membname)
				: membname;
	}

	@Override
	public LProtoName toPayloadType()
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
		if (!(o instanceof LProtoNameNode))
		{
			return false;
		}
		return super.equals(o);  // Does canEqual
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LProtoNameNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 421;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
}
