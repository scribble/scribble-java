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
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.del.ScribDel;
import org.scribble.util.Constants;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

// TODO: parameterize on global/local name node and role decl list (i.e. self roles)
public abstract class ProtocolHeader<K extends ProtocolKind>
		extends NameDeclNode<K> implements ProtocolKindNode<K>
{
	public static final int NAMENODE_CHILD = 0;
	public static final int PARAMDECLLIST_CHILD = 1;
	public static final int ROLEDECLLIST_CHILD = 2;
	
	// ScribTreeAdaptor#create constructor
	public ProtocolHeader(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected ProtocolHeader(ProtocolHeader<K> node)
	{
		super(node);
	}
	
	// Simple name
	@Override
	public abstract ProtocolNameNode<K> getNameNodeChild();
	
	public NonRoleParamDeclList getParamDeclListChild()
	{
		return (NonRoleParamDeclList) getChild(PARAMDECLLIST_CHILD);
	}
	
	public RoleDeclList getRoleDeclListChild()
	{
		return (RoleDeclList) getChild(ROLEDECLLIST_CHILD);  // TODO: swap order with paramdecllist (in grammar)
	}

	public boolean isParamDeclListEmpty()
	{
		return getParamDeclListChild().isEmpty();
	}
	
	public abstract ProtocolHeader<K> dupNode();
	
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
	public ProtocolHeader<K> visitChildren(AstVisitor nv) throws ScribException
	{
		/*ProtocolNameNode<K> nameNodeChild = (ProtocolNameNode<K>) visitChild(
				getNameNodeChild(), nv);*/  // Don't really need to visit, and can avoid generic cast
		RoleDeclList rdl = (RoleDeclList) visitChild(getRoleDeclListChild(), nv);
		NonRoleParamDeclList pdl = (NonRoleParamDeclList) 
				visitChild(getParamDeclListChild(), nv);
		return reconstruct(getNameNodeChild(), rdl, pdl);
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
		if (!isParamDeclListEmpty())
		{
			s += getParamDeclListChild();
		}
		return s + getRoleDeclListChild();
	}
}
