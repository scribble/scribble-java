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
import org.scribble.ast.name.NameNode;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.ProtocolName;
import org.scribble.visit.AstVisitor;

// TODO: parameterize on global/local name node and role decl list (i.e. self roles)
public abstract class ProtocolHeader<K extends ProtocolKind> extends NameDeclNode<K> implements ProtocolKindNode<K>
{
	public final RoleDeclList roledecls;
	public final NonRoleParamDeclList paramdecls;

	protected ProtocolHeader(CommonTree source, NameNode<K> name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
	{
		super(source, name);
		this.roledecls = roledecls;
		this.paramdecls = paramdecls;
	}
	
	public abstract ProtocolHeader<K> reconstruct(ProtocolNameNode<K> name, RoleDeclList rdl, NonRoleParamDeclList pdl);
	
	@Override
	public ProtocolHeader<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleDeclList rdl = (RoleDeclList) visitChild(this.roledecls, nv);
		NonRoleParamDeclList pdl = (NonRoleParamDeclList) visitChild(this.paramdecls, nv);
		return reconstruct((ProtocolNameNode<K>) this.name, rdl, pdl);
	}

	public boolean isParameterDeclListEmpty()
	{
		return this.paramdecls.isEmpty();
	}
	
	public abstract ProtocolNameNode<K> getNameNode();
	
	@Override
	public ProtocolName<K> getDeclName()
	{
		return (ProtocolName<K>) super.getDeclName();
	}

	@Override
	public String toString()
	{
		String s = Constants.PROTOCOL_KW + " " + this.name;
		if (!isParameterDeclListEmpty())
		{
			s += this.paramdecls;
		}
		return s + this.roledecls;
	}
}
