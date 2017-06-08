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
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Constants;
import org.scribble.ast.NameDeclNode;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.ProtocolHeader;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.Role;

public class LProtocolHeader extends ProtocolHeader<Local> implements LNode
{
	public LProtocolHeader(CommonTree source, LProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
	{
		super(source, name, roledecls, paramdecls);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LProtocolHeader(this.source, getNameNode(), this.roledecls, this.paramdecls);
	}
	
	@Override
	public LProtocolHeader clone()
	{
		LProtocolNameNode name = getNameNode().clone();
		RoleDeclList roledecls = this.roledecls.clone();
		NonRoleParamDeclList paramdecls = this.paramdecls.clone();
		return AstFactoryImpl.FACTORY.LProtocolHeader(this.source, name, roledecls, paramdecls);
	}

	@Override
	public LProtocolHeader reconstruct(ProtocolNameNode<Local> name, RoleDeclList rdl, NonRoleParamDeclList pdl)
	{
		ScribDel del = del();
		LProtocolHeader gph = new LProtocolHeader(this.source, (LProtocolNameNode) name, rdl, pdl);
		gph = (LProtocolHeader) gph.del(del);
		return gph;
	}

	public Role getSelfRole()
	{
		for (NameDeclNode<RoleKind> rd : this.roledecls.getDecls())
		{
			RoleDecl tmp = (RoleDecl) rd;
			if (tmp.isSelfRoleDecl())
			{
				return tmp.getDeclName();
			}
		}
		throw new RuntimeException("Shouldn't get here: " + this.roledecls);
	}

	@Override
	public LProtocolNameNode getNameNode()
	{
		return (LProtocolNameNode) this.name;
	}
	
	@Override
	public LProtocolName getDeclName()
	{
		return (LProtocolName) super.getDeclName();
	}
	
	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LNode.super.getKind();
	}
	
	@Override
	public String toString()
	{
		return Constants.LOCAL_KW + " " + super.toString();
	}
}
