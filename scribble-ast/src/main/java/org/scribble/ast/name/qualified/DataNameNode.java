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
import org.scribble.core.type.kind.DataKind;
import org.scribble.core.type.name.DataName;
import org.scribble.core.type.session.Arg;
import org.scribble.del.DelFactory;

//public class DataTypeNode extends MemberNameNode<DataTypeKind> implements PayloadElemNameNode
public class DataNameNode extends MemberNameNode<DataKind>
		implements PayElemNameNode<DataKind>
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
	public void decorateDel(DelFactory df)
	{
		df.DataNameNode(this);
	}

	@Override
	public DataName toName()
	{
		DataName membname = new DataName(getLastElement());
		return isPrefixed()
				? new DataName(getModuleNamePrefix(), membname)
		    : membname;
	}

	@Override
	public boolean isDataNameNode()
	{
		return true;
	}

	@Override
	public Arg<DataKind> toArg()
	{
		return toPayloadType();
	}

	@Override
	public DataName toPayloadType()
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
		return ((DataNameNode) o).canEquals(this) && super.equals(o);
	}
	
	@Override
	public boolean canEquals(Object o)
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
