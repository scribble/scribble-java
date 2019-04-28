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
import org.scribble.ast.MsgNode;
import org.scribble.core.type.kind.SigKind;
import org.scribble.core.type.name.SigName;
import org.scribble.del.DelFactory;

public class SigNameNode extends MemberNameNode<SigKind>
		implements MsgNode
{
	// ScribTreeAdaptor#create constructor
	public SigNameNode(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected SigNameNode(SigNameNode node)
	{
		super(node);
	}
	
	@Override
	public SigNameNode dupNode()
	{
		return new SigNameNode(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.SigNameNode(this);
	}

	@Override
	public SigName toName()
	{
		SigName membname = new SigName(getLastElement());
		return isPrefixed() ? new SigName(getModuleNamePrefix(), membname)
				: membname;
	}

	@Override
	public boolean isSigNameNode()
	{
		return true;
	}

	// Difference between toName and toMessage is scope? does that make sense?
	@Override
	public SigName toMsg() 
	{
		return toName();
	}

	@Override
	public SigName toArg()
	{
		return toMsg();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof SigNameNode))
		{
			return false;
		}
		return ((SigNameNode) o).canEquals(this) && super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof SigNameNode;
	}

	@Override
	public int hashCode()
	{
		int hash = 421;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
}
