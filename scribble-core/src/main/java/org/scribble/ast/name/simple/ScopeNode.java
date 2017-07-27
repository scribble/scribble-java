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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.type.kind.ScopeKind;
import org.scribble.type.name.Scope;

// Currently unused (TODO: interruptible)
public class ScopeNode extends SimpleNameNode<ScopeKind>
{
	public ScopeNode(CommonTree source, String identifier)
	{
		super(source, identifier);
	}

	@Override
	protected ScopeNode copy()
	{
		return new ScopeNode(this.source, getIdentifier());
	}
	
	@Override
	public ScopeNode clone(AstFactory af)
	{
		return (ScopeNode) af.SimpleNameNode(this.source, ScopeKind.KIND, getIdentifier());
	}

	@Override
	public Scope toName()
	{
		return new Scope(getIdentifier());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ScopeNode))
		{
			return false;
		}
		return ((ScopeNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof ScopeNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 359;
		hash = 31 * super.hashCode();
		return hash;
	}
}
