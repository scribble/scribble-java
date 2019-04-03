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
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Constants;
import org.scribble.ast.DisconnectAction;
import org.scribble.ast.local.LNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.type.kind.Global;
import org.scribble.type.kind.RoleKind;
import org.scribble.type.name.Role;

public class GDisconnect extends DisconnectAction<Global>
		implements GSimpleSessionNode
{
	// ScribTreeAdaptor#create constructor
	public GDisconnect(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public GDisconnect(GDisconnect node)
	{
		super(node);
	}
	
	@Override
	public GDisconnect dupNode()
	{
		return new GDisconnect(this);
	}

	public LNode project(AstFactory af, Role self)
	{
		RoleNode leftNode = this.getLeftChild();
		RoleNode rightNode = this.getRightChild();
		Role left = leftNode.toName();
		Role right = rightNode.toName();
		LNode proj = null;
		if (left.equals(self) || right.equals(self))
		{
			RoleNode leftNode1 = (RoleNode) af.SimpleNameNode(leftNode.getSource(),
					RoleKind.KIND, leftNode.toName().toString()); // clone?
			RoleNode rightNode1 = (RoleNode) af.SimpleNameNode(rightNode.getSource(),
					RoleKind.KIND, rightNode.toName().toString());
			// "left" of LDisconnect is used for self
			if (left.equals(self))
			{
				proj = af.LDisconnect(this.source, leftNode1, rightNode1);
			}
			else if (right.equals(self))  // CHECKME: self-comm via self-connections?  so self-disconn?
			{
				proj = af.LDisconnect(this.source, rightNode1, leftNode1);
			}
		}
		return proj;
	}

	@Override
	public String toString()
	{
		return Constants.DISCONNECT_KW + " " + getLeftChild()
				+ " " + Constants.TO_KW + " " + getRightChild() + ";";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	//public GDisconnect(CommonTree source, RoleNode src, RoleNode dest)
	public GDisconnect(CommonTree source, RoleNode left, RoleNode right)
	{
		super(source, left, right);
	}

	/*@Override
	protected GDisconnect copy()
	{
		return new GDisconnect(this.source, (MessageSigNode) this.msg, this.src, this.dest);
	}
	
	@Override
	public GDisconnect clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		RoleNode dest = this.dest.clone(af);
		return af.GDisconnect(this.source, src, dest);
	}

	@Override
	public GDisconnect reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	//public GDisconnect reconstruct(RoleNode src, RoleNode dest)
	{
		ScribDel del = del();
		GDisconnect gd = new GDisconnect(this.source, (MessageSigNode) this.msg, src, dest);
		gd = (GDisconnect) gd.del(del);
		return gd;
	}*/
}
