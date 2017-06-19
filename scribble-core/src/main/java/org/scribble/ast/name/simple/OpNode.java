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
import org.scribble.ast.AstFactoryImpl;
import org.scribble.sesstype.kind.OpKind;
import org.scribble.sesstype.name.Op;


public class OpNode extends SimpleNameNode<OpKind>
{
	public static final String EMPTY_OPERATOR_IDENTIFIER = "";
	
	public OpNode(CommonTree source, String identifier)
	{
		super(source, identifier);
	}

	@Override
	protected OpNode copy()
	{
		return new OpNode(this.source, getIdentifier());
	}
	
	@Override
	public OpNode clone()
	{
		return (OpNode) AstFactoryImpl.FACTORY.SimpleNameNode(this.source, OpKind.KIND, getIdentifier());
	}
	
	@Override
	public Op toName()
	{
		String id = getIdentifier();
		if (id.equals(EMPTY_OPERATOR_IDENTIFIER))
		{
			return Op.EMPTY_OPERATOR;
		}
		return new Op(id);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof OpNode))
		{
			return false;
		}
		return ((OpNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof OpNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 347;
		hash = 31 * super.hashCode();
		return hash;
	}
}
