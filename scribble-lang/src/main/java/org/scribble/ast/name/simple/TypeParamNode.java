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
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.type.kind.DataTypeKind;
import org.scribble.type.name.DataType;
import org.scribble.visit.Substitutor;

public class TypeParamNode extends NonRoleParamNode<DataTypeKind>
		implements PayloadElemNameNode<DataTypeKind>
		// As a payload, can only be a DataType (so hardcode)
{
	// ScribTreeAdaptor#create constructor
	public TypeParamNode(Token t)
	{
		super(t, DataTypeKind.KIND);
	}

	// Tree#dupNode constructor
	protected TypeParamNode(TypeParamNode node)//, String id)
	{
		super(node);
	}
	
	@Override
	public TypeParamNode dupNode()
	{
		return new TypeParamNode(this);
	}
	
	@Override
	public DataTypeNode substituteNames(Substitutor subs)
	{
		DataType arg = toArg();
		DataTypeNode an;
		an = (DataTypeNode) subs.getArgumentSubstitution(arg);  // getArgumentSubstitution returns a clone
		an = (DataTypeNode) an.del(del());
		return an;
	}
	
	@Override
	public DataType toName()
	{
		return new DataType(getText());
	}

	@Override
	public DataType toArg()
	{
		// FIXME: as a payload kind, currently hardcorded to data type kinds (protocol payloads not supported)
		return toPayloadType();
	}

	@Override
	public DataType toPayloadType()  // Currently can assume the only possible kind for NonRoleParamNode is DataTypeKind
	{
		return toName();
	}

	@Override
	public boolean isTypeParamNode()
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
		if (!(o instanceof TypeParamNode))
		{
			return false;
		}
		return super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof TypeParamNode;
	}
}
