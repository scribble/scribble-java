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
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.sesstype.name.RecVar;

public class RecVarNode extends SimpleNameNode<RecVarKind>
{
	public RecVarNode(CommonTree source, String identifier)
	{
		super(source, identifier);
	}
	
	// Factor up to SimpleNameNode?
	public RecVarNode reconstruct(String id)
	{
		ScribDel del = del();
		RecVarNode rv = new RecVarNode(this.source, id);
		rv = (RecVarNode) rv.del(del);
		return rv;
	}

	@Override
	protected RecVarNode copy()
	{
		return new RecVarNode(this.source, getIdentifier());
	}
	
	@Override
	public RecVarNode clone(AstFactory af)
	{
		return (RecVarNode) af.SimpleNameNode(this.source, RecVarKind.KIND, getIdentifier());
	}

	@Override
	public RecVar toName()
	{
		return new RecVar(getIdentifier());
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
		return ((RecVarNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
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
