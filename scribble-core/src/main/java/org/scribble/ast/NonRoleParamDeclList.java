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
import org.scribble.sesstype.kind.NonRoleParamKind;
import org.scribble.sesstype.name.Name;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;

// Typing a bit awkward that this list has to use NonRoleParamKind as the "concrete" kind, while the NonRoleParamDecl elements use the actual concrete kind
// But OK because the NonRoleParamDecl nodes are immutable (the generic kind value is never rewritten after instantiation, only read)
public class NonRoleParamDeclList extends HeaderParamDeclList<NonRoleParamKind>
{
	public NonRoleParamDeclList(CommonTree source, List<NonRoleParamDecl<NonRoleParamKind>> decls)
	{
		super(source, decls);
	}

	@Override
	protected NonRoleParamDeclList copy()
	{
		return new NonRoleParamDeclList(this.source, getDecls());
	}
	
	@Override
	public NonRoleParamDeclList clone()
	{
		List<NonRoleParamDecl<NonRoleParamKind>> decls = ScribUtil.cloneList(getDecls());
		return AstFactoryImpl.FACTORY.NonRoleParamDeclList(this.source, decls);
	}

	@Override
	public NonRoleParamDeclList reconstruct(List<? extends HeaderParamDecl<NonRoleParamKind>> decls)
	{
		ScribDel del = del();
		NonRoleParamDeclList rdl = AstFactoryImpl.FACTORY.NonRoleParamDeclList(this.source, castParamDecls(decls));
		rdl = (NonRoleParamDeclList) rdl.del(del);
		return rdl;
	}

	@Override
	public List<NonRoleParamDecl<NonRoleParamKind>> getDecls()
	{
		return castParamDecls(super.getDecls());
	}

	public List<Name<NonRoleParamKind>> getParameters()
	{
		return getDecls().stream().map((decl) -> decl.getDeclName()).collect(Collectors.toList());
	}
		
	// FIXME: move to delegate?
	@Override
	public NonRoleParamDeclList project(Role self)
	{
		return AstFactoryImpl.FACTORY.NonRoleParamDeclList(this.source, getDecls());
	}

	@Override
	public String toString()
	{
		return (isEmpty())
				? ""
				: "<" + super.toString() + ">";
	}
	
	private static List<NonRoleParamDecl<NonRoleParamKind>>
			castParamDecls(List<? extends HeaderParamDecl<NonRoleParamKind>> decls)
	{
		return decls.stream().map((d) -> (NonRoleParamDecl<NonRoleParamKind>) d).collect(Collectors.toList());
	}
}
