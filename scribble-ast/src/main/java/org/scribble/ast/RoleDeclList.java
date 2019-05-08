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

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.scribble.core.type.kind.RoleKind;
import org.scribble.core.type.name.Role;
import org.scribble.del.DelFactory;

public class RoleDeclList extends ParamDeclList<RoleKind>
{
	// ScribTreeAdaptor#create constructor
	public RoleDeclList(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public RoleDeclList(RoleDeclList node)
	{
		super(node);
	}
	
	@Override
	public List<RoleDecl> getDeclChildren()
	{
		return ((List<?>) getChildren()).stream().map(x -> (RoleDecl) x)
				.collect(Collectors.toList());
	}

	public List<Role> getRoles()
	{
		return getDeclChildren().stream().map(decl -> decl.getDeclName())
				.collect(Collectors.toList());
	}
	
	@Override
	public RoleDeclList dupNode()
	{
		return new RoleDeclList(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.RoleDeclList(this);
	}

	@Override
	public String toString()
	{
		return "(" + super.toString() + ")";
	}
}
