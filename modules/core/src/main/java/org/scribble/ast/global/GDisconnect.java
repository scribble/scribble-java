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

import java.util.Collections;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ConnectionAction;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageSigNode;
import org.scribble.ast.local.LNode;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.OpKind;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

public class GDisconnect extends ConnectionAction<Global> implements GSimpleInteractionNode
{
	// Duplicated in GWrap
  // FIXME: inconsistent wrt. this.source -- it is essentially parsed (in the sense of *omitted* syntax), but not recorded
	public static final MessageSigNode UNIT_MESSAGE_SIG_NODE =  // Hacky?
			AstFactoryImpl.FACTORY.MessageSigNode(null, 
				(OpNode) AstFactoryImpl.FACTORY.SimpleNameNode(null, OpKind.KIND, Op.EMPTY_OPERATOR.toString()),
				AstFactoryImpl.FACTORY.PayloadElemList(null, Collections.emptyList()));  // Payload.EMPTY_PAYLOAD?

	public GDisconnect(CommonTree source, RoleNode src, RoleNode dest)
	{
		super(source, src, UNIT_MESSAGE_SIG_NODE, dest);
	}

	public LNode project(Role self)
	{
		Role srcrole = this.src.toName();
		Role destrole = this.dest.toName();
		LNode projection = null;
		if (srcrole.equals(self) || destrole.equals(self))
		{
			RoleNode src = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(this.src.getSource(), RoleKind.KIND, this.src.toName().toString());  // clone?
			RoleNode dest = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(this.dest.getSource(), RoleKind.KIND, this.dest.toName().toString());
			if (srcrole.equals(self))
			{
				projection = AstFactoryImpl.FACTORY.LDisconnect(this.source, src, dest);
			}
			if (destrole.equals(self))
			{
				projection = AstFactoryImpl.FACTORY.LDisconnect(this.source, dest, src);
			}
		}
		return projection;
	}


	@Override
	protected GDisconnect copy()
	{
		return new GDisconnect(this.source, this.src, this.dest);
	}
	
	@Override
	public GDisconnect clone()
	{
		RoleNode src = this.src.clone();
		RoleNode dest = this.dest.clone();
		return AstFactoryImpl.FACTORY.GDisconnect(this.source, src, dest);
	}

	@Override
	public GDisconnect reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	//public GDisconnect reconstruct(RoleNode src, RoleNode dest)
	{
		ScribDel del = del();
		GDisconnect gd = new GDisconnect(this.source, src, dest);
		gd = (GDisconnect) gd.del(del);
		return gd;
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GSimpleInteractionNode.super.getKind();
	}

	@Override
	public String toString()
	{
		return Constants.DISCONNECT_KW + " " + this.src + " " + Constants.TO_KW + " " + this.dest + ";";
	}
}
