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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.del.ScribDel;
import org.scribble.type.kind.RoleKind;
import org.scribble.type.name.Name;
import org.scribble.type.name.Role;

public class RoleDecl extends HeaderParamDecl<RoleKind>
{
	public RoleDecl(CommonTree source, RoleNode name)
	{
		super(source, name);
	}

	@Override
	protected RoleDecl copy()
	{
		return new RoleDecl(this.source, (RoleNode) this.name);
	}
	
	@Override
	public RoleDecl clone(AstFactory af)
	{
		RoleNode role = (RoleNode) this.name.clone(af);
		return af.RoleDecl(this.source, role);
	}

	@Override
	public RoleDecl reconstruct(SimpleNameNode<RoleKind> name)
	{
		ScribDel del = del();
		RoleDecl rd = new RoleDecl(this.source, (RoleNode) name);
		rd = (RoleDecl) rd.del(del);
		return rd;
	}

	@Override
	public RoleDecl project(AstFactory af, Role self)
	{
		Name<RoleKind> role = this.name.toName();
		RoleNode rn = (RoleNode) af.SimpleNameNode(this.name.source, RoleKind.KIND, role.toString());
		if (role.equals(self))
		{
			return af.SelfRoleDecl(this.name.source, rn);
		}
		return af.RoleDecl(this.source, rn);
	}
	
	@Override
	public Role getDeclName()
	{
		return (Role) super.getDeclName();
	}
	
	public boolean isSelfRoleDecl()
	{
		return false;
	}

	@Override
	public String getKeyword()
	{
		return Constants.ROLE_KW;
	}
}
