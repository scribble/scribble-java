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
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.NameNode;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.core.job.ScribbleException;
import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.del.ScribDel;
import org.scribble.util.Constants;
import org.scribble.visit.AstVisitor;

// TODO: parameterize on global/local name node and role decl list (i.e. self roles)
public abstract class ProtocolHeader<K extends ProtocolKind>
		extends NameDeclNode<K> implements ProtocolKindNode<K>
{
	// ScribTreeAdaptor#create constructor
	public ProtocolHeader(Token t)
	{
		super(t);
		this.roledecls = null;
		this.paramdecls = null;
	}
	
	// Tree#dupNode constructor
	protected ProtocolHeader(ProtocolHeader<K> node)
	{
		super(node);
		this.roledecls = null;
		this.paramdecls = null;
	}
	
	public abstract ProtocolHeader<K> dupNode();
	
	// Simple name
	@Override
	public abstract ProtocolNameNode<K> getNameNodeChild();
	
	public RoleDeclList getRoleDeclListChild()
	{
		return (RoleDeclList) getChild(2);  // TODO: swap order with paramdecllist (in grammar)
	}
	
	public NonRoleParamDeclList getParamDeclListChild()
	{
		return (NonRoleParamDeclList) getChild(1);
	}
	
	public ProtocolHeader<K> reconstruct(ProtocolNameNode<K> name,
			RoleDeclList rdl, NonRoleParamDeclList pdl)
	{
		ProtocolHeader<K> pd = dupNode();
		ScribDel del = del();
		pd.addChild(name);
		pd.addChild(pdl);  // Cf. above TODO (rdl/pdl child order)
		pd.addChild(rdl);
		pd.setDel(del);  // No copy
		return pd;
	}
	
	@Override
	public ProtocolHeader<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		/*ProtocolNameNode<K> nameNodeChild = (ProtocolNameNode<K>) visitChild(
				getNameNodeChild(), nv);*/  // Don't really need to visit, and can avoid generic cast
		RoleDeclList rdl = (RoleDeclList) visitChild(getRoleDeclListChild(), nv);
		NonRoleParamDeclList pdl = (NonRoleParamDeclList) 
				visitChild(getParamDeclListChild(), nv);
		return reconstruct(getNameNodeChild(), rdl, pdl);
	}

	public boolean isParameterDeclListEmpty()
	{
		return getParamDeclListChild().isEmpty();
	}

	@Override
	public ProtocolName<K> getDeclName()
	{
		return getNameNodeChild().toName();
	}

	@Override
	public String toString()
	{
		String s = Constants.PROTOCOL_KW + " " + getNameNodeChild();
		if (!isParameterDeclListEmpty())
		{
			s += getParamDeclListChild();
		}
		return s + getRoleDeclListChild();
	}

	
	
	
	
	
	
	private final RoleDeclList roledecls;
	private final NonRoleParamDeclList paramdecls;

	// name = simple name
	protected ProtocolHeader(CommonTree source, NameNode<K> name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
	{
		super(source, name);
		this.roledecls = roledecls;
		this.paramdecls = paramdecls;
	}
}
