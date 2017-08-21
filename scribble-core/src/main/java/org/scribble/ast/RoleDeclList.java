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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.del.ScribDel;
import org.scribble.type.kind.RoleKind;
import org.scribble.type.name.Role;
import org.scribble.util.ScribUtil;

public class RoleDeclList extends HeaderParamDeclList<RoleKind>
{
	public RoleDeclList(CommonTree source, List<RoleDecl> decls)
	{
		super(source, decls);
	}

	@Override
	protected RoleDeclList copy()
	{
		return new RoleDeclList(this.source, getDecls());
	}
	
	@Override
	public RoleDeclList clone(AstFactory af)
	{
		List<RoleDecl> decls = ScribUtil.cloneList(af, getDecls());
		return af.RoleDeclList(this.source, decls);
	}

	@Override
	public HeaderParamDeclList<RoleKind> reconstruct(List<? extends HeaderParamDecl<RoleKind>> decls)
	{
		ScribDel del = del();
		RoleDeclList rdl = new RoleDeclList(this.source, castRoleDecls(decls));
		rdl = (RoleDeclList) rdl.del(del);
		return rdl;
	}
	
	@Override
	public List<RoleDecl> getDecls()
	{
		return castRoleDecls(super.getDecls());
	}

	public List<Role> getRoles()
	{
		return getDecls().stream().map((decl) -> decl.getDeclName()).collect(Collectors.toList());
	}

	// Move to del?
	@Override
	public RoleDeclList project(AstFactory af, Role self)
	{
		return af.RoleDeclList(this.source, getDecls());
	}

	@Override
	public String toString()
	{
		return "(" + super.toString() + ")";
	}
	
	private static List<RoleDecl> castRoleDecls(List<? extends HeaderParamDecl<RoleKind>> decls)
	{
		return decls.stream().map((d) -> (RoleDecl) d).collect(Collectors.toList());
	}
}
