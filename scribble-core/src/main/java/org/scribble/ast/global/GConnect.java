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
import org.scribble.ast.ConnectionAction;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.local.LNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.Role;

public class GConnect extends ConnectionAction<Global> implements GSimpleInteractionNode
{
	public GConnect(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)
	//public GConnect(RoleNode src, RoleNode dest)
	{
		super(source, src, msg, dest);
		//super(src, dest);
	}

	public LNode project(AstFactory af, Role self)
	{
		Role srcrole = this.src.toName();
		Role destrole = this.dest.toName();
		LNode projection = null;
		if (srcrole.equals(self) || destrole.equals(self))
		{
			RoleNode src = (RoleNode) af.SimpleNameNode(this.src.getSource(), RoleKind.KIND, this.src.toName().toString());  // clone?
			MessageNode msg = (MessageNode) this.msg;  // FIXME: need namespace prefix update?
			RoleNode dest = (RoleNode) af.SimpleNameNode(this.dest.getSource(), RoleKind.KIND, this.dest.toName().toString());
			if (srcrole.equals(self))
			{
				projection = af.LConnect(this.source, src, msg, dest);
				//projection = AstFactoryImpl.FACTORY.LConnect(src, dest);  // src and dest (not self and peer)
			}
			if (destrole.equals(self))
			{
				projection = af.LAccept(this.source, src, msg, dest);
				//projection = AstFactoryImpl.FACTORY.LAccept(src, dest);
			}
		}
		return projection;
	}

	@Override
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
	}

	@Override
	public GConnect reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	//public GConnect reconstruct(RoleNode src, RoleNode dest)
	{
		ScribDel del = del();
		GConnect gc = new GConnect(this.source, src, msg, dest);
		//GConnect gc = new GConnect(src, dest);
		gc = (GConnect) gc.del(del);
		return gc;
	}

	@Override
	public String toString()
	{
		return (isUnitMessage() ? "" : this.msg + " ") + Constants.CONNECT_KW + " " + this.src + " " + Constants.TO_KW + " " + this.dest + ";";
		//return Constants.CONNECT_KW + " " + this.src + " " + Constants.TO_KW + " " + this.dest + ";";
	}

	/*// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GSimpleInteractionNode.super.getKind();
	}*/
}
