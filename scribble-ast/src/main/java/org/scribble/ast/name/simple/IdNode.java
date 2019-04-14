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
import org.scribble.core.type.kind.IdKind;
import org.scribble.core.type.name.Id;

// N.B. no del attached, so not currently visited
public class IdNode extends SimpleNameNode<IdKind>
{
	// ScribTreeAdaptor#create constructor
	public IdNode(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected IdNode(IdNode node)
	{
		super(node);
	}
	
	@Override
	public IdNode dupNode()
	{
		return new IdNode(this);
	}
	
	@Override
	public String getText()
	{
		return getToken().getText();
	}

	@Override
	public Id toName()
	{
		return new Id(getText());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof IdNode))
		{
			return false;
		}
		return ((IdNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof IdNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 331;
		hash = 31 * super.hashCode();
		return hash;
	}
}
