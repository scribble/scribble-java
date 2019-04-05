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
package org.scribble.del.local;

import java.util.List;

import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageSigNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LMessageTransfer;
import org.scribble.ast.local.LSend;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.name.MessageId;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Payload;
import org.scribble.util.ScribException;
import org.scribble.visit.context.EGraphBuilder;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;
import org.scribble.visit.wf.ExplicitCorrelationChecker;

public class LSendDel extends LMessageTransferDel
{
	
	@Override
	public ScribNode leaveEGraphBuilding(ScribNode parent, ScribNode child,
			EGraphBuilder builder, ScribNode visited) throws ScribException
	{
		LSend ls = (LSend) visited;
		List<RoleNode> dests = ls.getDestinationChildren();
		if (dests.size() > 1)
		{
			throw new ScribException(
					"[TODO] EFSM building for multicast not supported: " + ls);
		}
		Role peer = dests.get(0).toName();
		MessageNode msg = ls.getMessageNodeChild();
		MessageId<?> mid = msg.toMessage().getId();
		Payload payload = msg.isMessageSigNode()  // Hacky?
					? ((MessageSigNode) msg).getPayloadListChild().toPayload()
					: Payload.EMPTY_PAYLOAD;
		builder.util.addEdge(builder.util.getEntry(),
				builder.lang.config.ef.newESend(peer, mid, payload),
				builder.util.getExit());
		//builder.builder.addEdge(builder.builder.getEntry(), Send.get(peer, mid, payload), builder.builder.getExit());
		return (LSend) super.leaveEGraphBuilding(parent, child, builder, ls);
	}

	// Could make a LMessageTransferDel to factor this out with LReceiveDel
	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent,
			ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LSend) child).getSourceChild().toName());
	}
	
	@Override
	public LMessageTransfer leaveExplicitCorrelationCheck(ScribNode parent,
			ScribNode child, ExplicitCorrelationChecker checker, ScribNode visited)
			throws ScribException
	{
		LMessageTransfer lmt = (LMessageTransfer) visited;
		checker.pushEnv(checker.popEnv().disableAccept());
		return lmt;
	}
}
