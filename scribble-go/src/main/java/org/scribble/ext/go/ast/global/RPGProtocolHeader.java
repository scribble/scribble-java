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
package org.scribble.ext.go.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Constants;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.global.GProtocolHeader;
import org.scribble.ast.local.LProtocolHeader;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.go.ast.RPAstFactory;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;
import org.scribble.visit.AstVisitor;

public class RPGProtocolHeader extends GProtocolHeader
{
	public final String annot;
	
	public RPGProtocolHeader(CommonTree source, GProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls, String annot)
	{
		super(source, name, roledecls, paramdecls);
		this.annot = annot;
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new RPGProtocolHeader(this.source, getNameNode(), this.roledecls, this.paramdecls, this.annot);
	}
	
	@Override
	public RPGProtocolHeader clone(AstFactory af)
	{
		GProtocolNameNode name = getNameNode().clone(af);
		RoleDeclList roledecls = this.roledecls.clone(af);
		NonRoleParamDeclList paramdecls = this.paramdecls.clone(af);
		return ((RPAstFactory) af).RPGProtocolHeader(this.source, name, roledecls, paramdecls, this.annot);
	}
	
	@Override
	public RPGProtocolHeader visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleDeclList rdl = (RoleDeclList) visitChild(this.roledecls, nv);
		NonRoleParamDeclList pdl = (NonRoleParamDeclList) visitChild(this.paramdecls, nv);
		return reconstruct((GProtocolNameNode) this.name, rdl, pdl, this.annot);
	}

	@Override
	public RPGProtocolHeader reconstruct(ProtocolNameNode<Global> name, RoleDeclList rdl, NonRoleParamDeclList pdl)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}

	public RPGProtocolHeader reconstruct(ProtocolNameNode<Global> name, RoleDeclList rdl, NonRoleParamDeclList pdl, String annot)
	{
		ScribDel del = del();
		RPGProtocolHeader gph = new RPGProtocolHeader(this.source, (GProtocolNameNode) name, rdl, pdl, annot);
		gph = (RPGProtocolHeader) gph.del(del);
		return gph;
	}

	public LProtocolHeader project(AstFactory af, Role self, LProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}
	
	@Override
	public String toString()
	{
		return Constants.GLOBAL_KW + " " + super.toString() + " @\"" + this.annot + "\"";
	}
}
