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
package org.scribble.ast.global;

import org.antlr.runtime.Token;
import org.scribble.ast.PayElem;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.name.qualified.GProtoNameNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.GDelegType;
import org.scribble.core.type.name.PayElemType;
import org.scribble.del.DelFactory;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

// A "name pair" payload elem (current AST hierarchy induces this pattern), cf. UnaryPayloadElem (also differs in no parsing ambig against parameters)
// The this.name will be global kind, but overall this node is local kind
//public class DelegationElem extends PayloadElem<Local>
public class GDelegPayElem extends ScribNodeBase implements PayElem<Local>
{
	public static final int PROTO_CHILD_INDEX = 0;
	public static final int ROLE_CHILD_INDEX = 1;

	// ScribTreeAdaptor#create constructor
	public GDelegPayElem(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public GDelegPayElem(GDelegPayElem node)
	{
		super(node);
	}

  // Becomes full name after disambiguation
	public GProtoNameNode getProtocolChild()
	{
		return (GProtoNameNode) getChild(PROTO_CHILD_INDEX);
	}
	
	public RoleNode getRoleChild()
	{
		return (RoleNode) getChild(ROLE_CHILD_INDEX);
	}

	// "add", not "set"
	public void addScribChildren(GProtoNameNode proto, RoleNode role)
	{
		// Cf. above getters and Scribble.g children order
		addChild(proto);
		addChild(role);
	}
	
	@Override
	public GDelegPayElem dupNode()
	{
		return new GDelegPayElem(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.GDelegPayElem(this);
	}

	public GDelegPayElem reconstruct(GProtoNameNode proto, RoleNode role)
	{
		GDelegPayElem dup = dupNode();
		dup.addScribChildren(proto, role);
		dup.setDel(del());  // No copy
		return dup;
	}

	@Override
	public GDelegPayElem visitChildren(AstVisitor nv) throws ScribException
	{
		GProtoNameNode name = (GProtoNameNode) 
				visitChild(getProtocolChild(), nv);
		RoleNode role = (RoleNode) visitChild(getRoleChild(), nv);
		return reconstruct(name, role);
	}

	@Override
	public boolean isGlobalDelegationElem()
	{
		return true;
	}

	@Override
	public PayElemType<Local> toPayloadType()
	{
		return new GDelegType(getProtocolChild().toName(),
				getRoleChild().toName());
	}
	
	@Override
	public String toString()
	{
		return getProtocolChild() + "@" + getRoleChild();
	}
}
