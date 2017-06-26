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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.PayloadElem;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.local.LDelegationElem;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.GDelegationType;
import org.scribble.sesstype.name.PayloadType;
import org.scribble.visit.AstVisitor;
import org.scribble.visit.context.Projector;

// A "name pair" payload elem (current AST hierarchy induces this pattern), cf. UnaryPayloadElem (also differs in no parsing ambig against parameters)
// The this.name will be global kind, but overall this node is local kind
//public class DelegationElem extends PayloadElem<Local>
public class GDelegationElem extends ScribNodeBase implements PayloadElem<Local>
{
  // Currently no potential for ambiguity, cf. UnaryPayloadElem (DataTypeNameNode or ParameterNode)
	public final GProtocolNameNode proto;  // Becomes full name after disambiguation
	public final RoleNode role;
	
	public GDelegationElem(CommonTree source, GProtocolNameNode proto, RoleNode role)
	{
		//super(proto);
		super(source);
		this.proto = proto;
		this.role = role;
	}
	
	@Override
	public LDelegationElem project(AstFactory af)
	{
		return af.LDelegationElem(this.source, Projector.makeProjectedFullNameNode(af, this.source, this.proto.toName(), this.role.toName()));
	}

	@Override
	public boolean isGlobalDelegationElem()
	{
		return true;
	}

	@Override
	protected GDelegationElem copy()
	{
		return new GDelegationElem(this.source, this.proto, this.role);
	}
	
	@Override
	public GDelegationElem clone(AstFactory af)
	{
		GProtocolNameNode name = (GProtocolNameNode) this.proto.clone(af);
		RoleNode role = (RoleNode) this.role.clone(af);
		return af.GDelegationElem(this.source, name, role);
	}

	public GDelegationElem reconstruct(GProtocolNameNode proto, RoleNode role)
	{
		ScribDel del = del();
		GDelegationElem elem = new GDelegationElem(this.source, proto, role);
		elem = (GDelegationElem) elem.del(del);
		return elem;
	}

	@Override
	public GDelegationElem visitChildren(AstVisitor nv) throws ScribbleException
	{
		GProtocolNameNode name = (GProtocolNameNode) visitChild(this.proto, nv);
		RoleNode role = (RoleNode) visitChild(this.role, nv);
		return reconstruct(name, role);
	}
	
	@Override
	public String toString()
	{
		return this.proto + "@" + this.role;
	}

	@Override
	public PayloadType<Local> toPayloadType()
	{
		return new GDelegationType(this.proto.toName(), this.role.toName());
	}
}
