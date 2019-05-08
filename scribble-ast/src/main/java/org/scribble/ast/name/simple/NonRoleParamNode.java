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
import org.scribble.core.type.kind.NonRoleParamKind;

// An unambiguous kinded parameter (ambiguous parameters handled by disambiguation) that isn't a role -- e.g. DataType/MessageSigName param
public abstract class NonRoleParamNode<K extends NonRoleParamKind> extends
		SimpleNameNode<K>  // As a payload, can only be a DataType (so hardcode)
{
	public final K kind;  // CHECKME: still useful, now there are concrete subclasses?

	public NonRoleParamNode(Token t, K kind)
	{
		super(t);
		this.kind = kind;
	}

	// Tree#dupNode constructor
	protected NonRoleParamNode(NonRoleParamNode<K> node)//, String id)
	{
		super(node);
		this.kind = node.kind;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 317;
		hash = 31 * super.hashCode();
		hash = 31 * this.kind.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof NonRoleParamNode))
		{
			return false;
		}
		NonRoleParamNode<? extends NonRoleParamKind> them = (NonRoleParamNode<?>) o;
		return super.equals(o)  // Does canEqual
				&& this.kind.equals(them.kind);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof NonRoleParamNode;
	}
}
