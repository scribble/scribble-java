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
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.GProtoName;
import org.scribble.del.DelFactory;

public class GProtoNameNode extends ProtoNameNode<Global>
{
	// ScribTreeAdaptor#create constructor
	public GProtoNameNode(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public GProtoNameNode(GProtoNameNode node)
	{
		super(node);
	}

	@Override
	public GProtoNameNode dupNode()
	{
		return new GProtoNameNode(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.GProtoNameNode(this);
	}
	
	@Override
	public GProtoName toName()
	{
		GProtoName membname = new GProtoName(getLastElement());
		return isPrefixed()
				? new GProtoName(getModuleNamePrefix(), membname)
				: membname;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GProtoNameNode))
		{
			return false;
		}
		return ((GProtoNameNode) o).canEquals(this) && super.equals(o);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GProtoNameNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 419;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
}
