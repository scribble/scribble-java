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
import org.scribble.ast.ConnectAction;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageSigNode;
import org.scribble.ast.local.LNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.type.kind.Global;
import org.scribble.type.kind.RoleKind;
import org.scribble.type.name.Role;

public class GWrap extends ConnectAction<Global> implements GSimpleInteractionNode
{
	// ScribTreeAdaptor#create constructor
	public GWrap(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public GWrap(GWrap node)
	{
		super(node);
	}

	public LNode project(AstFactory af, Role self)
	{
		RoleNode srcNode = getSourceChild();
		RoleNode destNode = getDestinationChild();
		Role srcrole = srcNode.toName();
		Role destrole = destNode.toName();
		LNode projection = null;
		if (srcrole.equals(self) || destrole.equals(self))
		{
			RoleNode srcNode1 = (RoleNode) af.SimpleNameNode(srcNode.getSource(),
					RoleKind.KIND, srcNode.toName().toString()); // clone?
			RoleNode destNode1 = (RoleNode) af.SimpleNameNode(destNode.getSource(),
					RoleKind.KIND, destNode.toName().toString());
			if (srcrole.equals(self))
			{
				projection = af.LWrapClient(this.source, srcNode1, destNode1);  // src and dest (not self and peer)
			}
			if (destrole.equals(self))
			{
				projection = af.LWrapServer(this.source, srcNode1, destNode1);
			}
		}
		return projection;
	}
	
	public GWrap dupNode()
	{
		return new GWrap(this);
	}

	@Override
	public String toString()
	{
		return Constants.WRAP_KW + " " + getSourceChild() + " " + Constants.TO_KW
				+ " " + getDestinationChild() + ";";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	//public GWrap(CommonTree source, RoleNode src, RoleNode dest)
	public GWrap(CommonTree source, MessageSigNode unit, RoleNode src, RoleNode dest)
	{
		super(source, src, unit, dest);
	}

	/*@Override
	protected GWrap copy()
	{
		return new GWrap(this.source, (MessageSigNode) this.msg, this.src, this.dest);
	}
	
	@Override
	public GWrap clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		RoleNode dest = this.dest.clone(af);
		return af.GWrap(this.source, src, dest);
	}

	@Override
	public GWrap reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	//public GWrap reconstruct(RoleNode src, RoleNode dest)
	{
		ScribDel del = del();
		GWrap gc = new GWrap(this.source, (MessageSigNode) this.msg, src, dest);  //this.msg);
		gc = (GWrap) gc.del(del);
		return gc;
	}*/
}
