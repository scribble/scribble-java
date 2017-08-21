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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LNode;
import org.scribble.ast.local.LReceive;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.type.kind.Global;
import org.scribble.type.kind.RoleKind;
import org.scribble.type.name.Role;
import org.scribble.util.ScribUtil;

public class GMessageTransfer extends MessageTransfer<Global> implements GSimpleInteractionNode
{
	public GMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		super(source, src, msg, dests);
	}

	public LNode project(AstFactory af, Role self)
	{
		Role srcrole = this.src.toName();
		List<Role> destroles = this.getDestinationRoles();
		LNode projection = null;
		if (srcrole.equals(self) || destroles.contains(self))
		{
			RoleNode src = (RoleNode) af.SimpleNameNode(this.src.getSource(), RoleKind.KIND, this.src.toName().toString());  // clone?
			MessageNode msg = (MessageNode) this.msg.project(af);  // FIXME: need namespace prefix update?
			List<RoleNode> dests =
					this.getDestinations().stream().map((rn) ->
							(RoleNode) af.SimpleNameNode(rn.getSource(), RoleKind.KIND, rn.toName().toString())).collect(Collectors.toList());
			if (srcrole.equals(self))
			{
				projection = af.LSend(this.source, src, msg, dests);
			}
			if (destroles.contains(self))
			{
				if (projection == null)
				{
					projection = af.LReceive(this.source, src, msg, dests);
				}
				else
				{
					LReceive lr = af.LReceive(this.source, src, msg, dests);
					List<LInteractionNode> lis = Arrays.asList(new LInteractionNode[]{(LInteractionNode) projection, lr});
					projection = af.LInteractionSeq(this.source, lis);
				}
			}
		}
		return projection;
	}


	@Override
	protected GMessageTransfer copy()
	{
		return new GMessageTransfer(this.source, this.src, this.msg, getDestinations());
	}
	
	@Override
	public GMessageTransfer clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		MessageNode msg = this.msg.clone(af);
		List<RoleNode> dests = ScribUtil.cloneList(af, getDestinations());
		return af.GMessageTransfer(this.source, src, msg, dests);
	}

	@Override
	public GMessageTransfer reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		ScribDel del = del();
		GMessageTransfer gmt = new GMessageTransfer(this.source, src, msg, dests);
		gmt = (GMessageTransfer) gmt.del(del);
		return gmt;
	}

	@Override
	public String toString()
	{
		return this.msg + " " + Constants.FROM_KW + " " + this.src + " " + Constants.TO_KW + " "
					+ getDestinations().stream().map(dest -> dest.toString()).collect(Collectors.joining(", ")) + ";";
	}

	/*// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GSimpleInteractionNode.super.getKind();
	}*/
}
