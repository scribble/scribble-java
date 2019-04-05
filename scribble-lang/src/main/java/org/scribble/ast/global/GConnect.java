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
import org.scribble.ast.MessageNode;
import org.scribble.ast.local.LScribNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.type.kind.Global;
import org.scribble.type.kind.RoleKind;
import org.scribble.type.name.Role;
import org.scribble.util.Constants;

// TODO: make GConnectionAction and factor this class with GWrap (cf. LConnectionAction)
public class GConnect extends ConnectAction<Global>
		implements GSimpleSessionNode
{
	// ScribTreeAdaptor#create constructor
	public GConnect(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public GConnect(GConnect node)
	{
		super(node);
	}
	
	@Override
	public GConnect dupNode()
	{
		return new GConnect(this);
	}

	public LScribNode project(AstFactory af, Role self)
	{
		Role src = this.getSourceChild().toName();
		MessageNode msgNode = this.getMessageNodeChild();
		Role dest = this.getDestinationChild().toName();
		LScribNode proj = null;
		if (src.equals(self) || dest.equals(self))
		{
			if (src.equals(dest))
			{
				throw new RuntimeException(
						"Shouldn't get in here (self-connection): " + this);
			}
			
			RoleNode srcNode = getSourceChild();
			RoleNode destNode = getDestinationChild();
			RoleNode srcNode1 = (RoleNode) af.SimpleNameNode(srcNode.getSource(),
					RoleKind.KIND, srcNode.toName().toString()); // clone?
			MessageNode msgNode1 = (MessageNode) msgNode;  // CHECKME: need namespace prefix update?
			RoleNode destNode1 = (RoleNode) af.SimpleNameNode(destNode.getSource(),
					RoleKind.KIND, destNode.toName().toString());
			if (src.equals(self))
			{
				proj = af.LRequest(this.source, srcNode1, msgNode1, destNode1);
				//projection = AstFactoryImpl.FACTORY.LConnect(src, dest);  // src and dest (not self and peer)
			}
			if (dest.equals(self))
			{
				proj = af.LAccept(this.source, srcNode1, msgNode1, destNode1);
				//projection = AstFactoryImpl.FACTORY.LAccept(src, dest);
			}
		}
		return proj;
	}

	@Override
	public String toString()
	{
		return (isUnitMessage() ? "" : getMessageNodeChild() + " ")
				+ Constants.CONNECT_KW + " " + getSourceChild() + " " + Constants.TO_KW
				+ " " + getDestinationChild() + ";";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public GConnect(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)
	//public GConnect(RoleNode src, RoleNode dest)
	{
		super(source, src, msg, dest);
		//super(src, dest);
	}

	/*@Override
	protected GConnect copy()
	{
		return new GConnect(this.source, this.src, this.msg, this.dest);
		//return new GConnect(this.src, this.dest);
	}
	
	@Override
	public GConnect clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		MessageNode msg = this.msg.clone(af);
		RoleNode dest = this.dest.clone(af);
		return af.GConnect(this.source, src, msg, dest);
		//return AstFactoryImpl.FACTORY.GConnect(src, dest);
	}*/

	/*@Override
	public GConnect reconstruct(MessageNode msg, RoleNode src, RoleNode dest)
	//public GConnect reconstruct(RoleNode src, RoleNode dest)
	{
		ScribDel del = del();
		GConnect gc = new GConnect(this.source, src, msg, dest);
		//GConnect gc = new GConnect(src, dest);
		gc = (GConnect) gc.del(del);
		return gc;
	}*/
}
