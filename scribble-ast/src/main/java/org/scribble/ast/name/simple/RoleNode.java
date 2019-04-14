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
package org.scribble.ast.name.simple;

import org.antlr.runtime.Token;
import org.scribble.ast.DoArgNode;
import org.scribble.ast.ScribNode;
import org.scribble.core.type.kind.RoleKind;
import org.scribble.core.type.name.Role;

public class RoleNode extends SimpleNameNode<RoleKind> implements DoArgNode 
		// RoleDecl, RoleInstantiation
{
	// ScribTreeAdaptor#create constructor
	public RoleNode(Token t)
	{
		super(t);
	}

	// Scribble.g, IDENTIFIER<RecVarNode>[$IDENTIFIER]
	public RoleNode(int ttype, Token t)
	{
		this(t);
		System.out.println("RRRRR: " + ttype + " ,, "+ t + " ,, " + t.getType() + " ,, " + toName());
	}

	// Tree#dupNode constructor
	protected RoleNode(RoleNode node)//, String id)
	{
		super(node);
	}
	
	@Override
	public RoleNode dupNode()
	{
		return new RoleNode(this);
	}

	// RoleNode is the only NameNode with a reconstruct (so not factored up)
	protected RoleNode reconstruct(String id)
	{
		ScribNode n = getChild(0);  // TODO: factor out (ID -- currently ambignamenode?)
		RoleNode r = dupNode();
		r.addChild(n);
		r.setDel(del());  // No copy  // Default delegate assigned in ModelFactoryImpl for all simple names
		return r;
	}
	
	@Override
	public Role toName()
	{
		return new Role(getText());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RoleNode))
		{
			return false;
		}
		return ((RoleNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof RoleNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 353;
		hash = 31 * super.hashCode();
		return hash;
	}
}
