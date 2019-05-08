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
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.kind.RoleKind;
import org.scribble.core.type.name.Role;
import org.scribble.del.DelFactory;
import org.scribble.util.Constants;

public class RoleDecl extends ParamDecl<RoleKind>
{
	// ScribTreeAdaptor#create constructor
	public RoleDecl(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public RoleDecl(RoleDecl node)
	{
		super(node);
	}
	
	@Override
	public RoleNode getNameNodeChild()
	{
		return (RoleNode) getRawNameNodeChild();
	}
	
	@Override
	public RoleDecl dupNode()
	{
		return new RoleDecl(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.RoleDecl(this);
	}

	@Override
	public Role getDeclName()
	{
		return getNameNodeChild().toName();
	}

	@Override
	public String getKeyword()
	{
		return Constants.ROLE_KW;
	}
}
