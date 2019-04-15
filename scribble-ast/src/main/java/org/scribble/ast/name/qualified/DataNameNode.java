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
import org.scribble.core.type.kind.DataTypeKind;
import org.scribble.core.type.name.DataType;
import org.scribble.core.type.session.Arg;

//public class DataTypeNode extends MemberNameNode<DataTypeKind> implements PayloadElemNameNode
public class DataNameNode extends MemberNameNode<DataTypeKind>
		implements PayElemNameNode<DataTypeKind>
{
	// ScribTreeAdaptor#create constructor
	public DataNameNode(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected DataNameNode(DataNameNode node)
	{
		super(node);
	}
	
	@Override
	public DataNameNode dupNode()
	{
		return new DataNameNode(this);
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

  // CHECKME: is equals/hashCode actually needed for these ScribNodes? (cf. Name)
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof DataNameNode))
		{
			return false;
		}
		return ((DataNameNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof DataNameNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 409;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
}
