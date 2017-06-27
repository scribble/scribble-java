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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.name.DataType;

//public class DataTypeNode extends MemberNameNode<DataTypeKind> implements PayloadElemNameNode
public class DataTypeNode extends MemberNameNode<DataTypeKind> implements PayloadElemNameNode<DataTypeKind>
{
	public DataTypeNode(CommonTree source, String... elems)
	{
		super(source, elems);
	}

	@Override
	protected DataTypeNode copy()
	{
		return new DataTypeNode(this.source, this.elems);
	}
	
	@Override
	public DataTypeNode clone(AstFactory af)
	{
		return (DataTypeNode) af.QualifiedNameNode(this.source, DataTypeKind.KIND, this.elems);
	}

	@Override
	public DataType toName()
	{
		DataType membname = new DataType(getLastElement());
		return isPrefixed()
				? new DataType(getModuleNamePrefix(), membname)
		    : membname;
	}

	@Override
	public boolean isDataTypeNameNode()
	{
		return true;
	}

	@Override
	public Arg<DataTypeKind> toArg()
	{
		return toPayloadType();
	}

	@Override
	public DataType toPayloadType()
	{
		return toName();
	}
	
	@Override
	public boolean equals(Object o)  // FIXME: is equals/hashCode needed for these Nodes?
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof DataTypeNode))
		{
			return false;
		}
		return ((DataTypeNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof DataTypeNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 409;
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}
}
