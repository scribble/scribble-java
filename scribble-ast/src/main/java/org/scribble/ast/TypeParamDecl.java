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
package org.scribble.ast;

import org.antlr.runtime.Token;
import org.scribble.ast.name.simple.TypeParamNode;
import org.scribble.core.type.kind.DataTypeKind;
import org.scribble.core.type.name.DataType;
import org.scribble.core.type.name.Role;
import org.scribble.util.Constants;

public class TypeParamDecl extends NonRoleParamDecl<DataTypeKind>
{
	// ScribTreeAdaptor#create constructor
	public TypeParamDecl(Token t)
	{
		super(t, DataTypeKind.KIND);
	}

	// Tree#dupNode constructor
	public TypeParamDecl(TypeParamDecl node)
	{
		super(node);
	}
	
	@Override
	public TypeParamDecl dupNode()
	{
		return new TypeParamDecl(this);
	}
	
	@Override
	public TypeParamNode getNameNodeChild()
	{
		return (TypeParamNode) getRawNameNodeChild();  // CHECKME: make Type/SigParamNode?
	}

	@Override
	public TypeParamDecl project(AstFactory af, Role self)
	{
		return dupNode();
	}

	@Override
	//public Name<DataTypeKind> getDeclName()
	public DataType getDeclName()
	{
		return (DataType) getNameNodeChild().toName();
	}
	
	@Override
	public String getKeyword()
	{
		return Constants.TYPE_KW;
	}
}
