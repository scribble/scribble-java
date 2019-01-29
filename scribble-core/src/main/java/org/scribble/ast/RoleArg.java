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
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.type.kind.RoleKind;
import org.scribble.type.name.Role;

public class RoleArg extends DoArg<RoleNode>
{
	// ScribTreeAdaptor#create constructor
	public RoleArg(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public RoleArg(RoleArg node)
	{
		super(node);
	}
	
	@Override
	public RoleNode getValChild()
	{
		return (RoleNode) getChild(0);
	}
	
	@Override
	public RoleArg dupNode()
	{
		return new RoleArg(this);
	}
	
	// CHECKME: move to delegate?
	@Override
	public RoleArg project(AstFactory af, Role self)
	{
		RoleNode r = getValChild();
		RoleNode rn = (RoleNode) af.SimpleNameNode(r.source, RoleKind.KIND,
				r.toName().toString());
		return af.RoleArg(this.source, rn);
	}
	
	
	
	
	
	
	
	
	
	
	public RoleArg(CommonTree source, RoleNode arg)
	{
		super(source, arg);
	}

	/*@Override
	protected ScribNodeBase copy()
	{
		return new RoleArg(this.source, getValChild());
	}	
	
	@Override
	public RoleArg clone(AstFactory af)
	{
		RoleNode role = getValChild().clone(af);
		return af.RoleArg(this.source, role);
	}

	@Override
	public RoleArg reconstruct(RoleNode arg)
	{
		ScribDel del = del();
		RoleArg ri = new RoleArg(this.source, arg);
		ri = (RoleArg) ri.del(del);
		return ri;
	}
	
	@Override
	public RoleNode getValChild()
	{
		return (RoleNode) super.getValChild();
	}*/
}
