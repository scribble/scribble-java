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
import org.scribble.ast.name.PayElemNameNode;
import org.scribble.core.type.kind.DataKind;
import org.scribble.core.type.name.DataName;
import org.scribble.del.DelFactory;

public class DataParamNode extends NonRoleParamNode<DataKind>
		implements PayElemNameNode<DataKind>  // As a payload, can only be a DataType (so hardcode)
{
	// Scribble.g, IDENTIFIER<...Node>[$IDENTIFIER]
	// N.B. ttype (an "imaginary node" type) is discarded, t is a ScribbleParser.ID token type
	public DataParamNode(int ttype, Token t)
	{
		super(t, DataKind.KIND);
	}

	// Tree#dupNode constructor
	protected DataParamNode(DataParamNode node)
	{
		super(node);
	}
	
	@Override
	public DataParamNode dupNode()
	{
		return new DataParamNode(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.DataParamNode(this);
	}
	
  // N.B. no "DataParamName"
	@Override
	public DataName toName()
	{
		return new DataName(getText());
	}

	@Override
	public DataName toArg()
	{
		// As a payload kind, currently hardcorded to data type kinds (protocol payloads not supported)
		return toPayloadType();
	}

	@Override
	public DataName toPayloadType()  // Currently can assume the only possible kind for NonRoleParamNode is DataTypeKind
	{
		return toName();
	}

	@Override
	public boolean isDataParamNode()
	{
		return true;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 8599;
		hash = 31 * super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof DataParamNode))
		{
			return false;
		}
		return super.equals(o);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof DataParamNode;
	}
}
