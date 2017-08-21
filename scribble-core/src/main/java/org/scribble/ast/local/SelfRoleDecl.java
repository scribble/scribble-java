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
package org.scribble.ast.local;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.del.ScribDel;
import org.scribble.type.kind.RoleKind;
import org.scribble.type.name.Role;

public class SelfRoleDecl extends RoleDecl
{
	public SelfRoleDecl(CommonTree source, RoleNode rn)
	{
		super(source, rn);
	}

	@Override
	protected SelfRoleDecl copy()
	{
		return new SelfRoleDecl(this.source, (RoleNode) this.name);
	}

	@Override
	public RoleDecl reconstruct(SimpleNameNode<RoleKind> name)
	{
		ScribDel del = del();
		SelfRoleDecl rd = new SelfRoleDecl(this.source, (RoleNode) name);
		rd = (SelfRoleDecl) rd.del(del);
		return rd;
	}
	
	@Override
	public SelfRoleDecl project(AstFactory af, Role self)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}
	
	@Override
	public boolean isSelfRoleDecl()
	{
		return true;
	}
}
