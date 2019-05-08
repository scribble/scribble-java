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

// N.B. without surrounding quotes "..." -- cf. ScribTreeAdaptor.create
// N.B. no del attached, so not currently visited
public class ExtIdNode extends IdNode  // CHECKME: e.g., toName gives Id
{
	// ScribTreeAdaptor#create constructor
	public ExtIdNode(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected ExtIdNode(ExtIdNode node)
	{
		super(node);
	}
	
	@Override
	public ExtIdNode dupNode()
	{
		return new ExtIdNode(this);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ExtIdNode))
		{
			return false;
		}
		return ((ExtIdNode) o).canEquals(this) && super.equals(o);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof ExtIdNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 13007;
		hash = 31 * super.hashCode();
		return hash;
	}
}
