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

import org.scribble.ast.MessageSigNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LReceive;
import org.scribble.main.ScribbleException;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;
import org.scribble.visit.context.EGraphBuilder;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;
import org.scribble.visit.context.UnguardedChoiceDoProjectionChecker;
import org.scribble.visit.context.env.UnguardedChoiceDoEnv;

public class LReceiveDel extends LMessageTransferDel
{
	@Override
	public ScribNode leaveEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder builder, ScribNode visited) throws ScribbleException
	{
		LReceive lr = (LReceive) visited;
		Role peer = lr.src.toName();
		MessageId<?> mid = lr.msg.toMessage().getId();
		Payload payload = (lr.msg.isMessageSigNode())  // Hacky?
				? ((MessageSigNode) lr.msg).payloads.toPayload()
				: Payload.EMPTY_PAYLOAD;
		builder.util.addEdge(builder.util.getEntry(), builder.job.ef.newEReceive(peer, mid, payload), builder.util.getExit());
		//builder.builder.addEdge(builder.builder.getEntry(), Receive.get(peer, mid, payload), builder.builder.getExit());
		return (LReceive) super.leaveEGraphBuilding(parent, child, builder, lr);
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LReceive) child).src.toName());
	}
	
	@Override
	public void enterUnguardedChoiceDoProjectionCheck(ScribNode parent, ScribNode child, UnguardedChoiceDoProjectionChecker checker) throws ScribbleException
	{
		super.enterUnguardedChoiceDoProjectionCheck(parent, child, checker);
		LReceive lr = (LReceive) child;
		UnguardedChoiceDoEnv env = checker.popEnv();
		env = env.setChoiceSubject(lr.src.toName());
		checker.pushEnv(env);
	}
}
