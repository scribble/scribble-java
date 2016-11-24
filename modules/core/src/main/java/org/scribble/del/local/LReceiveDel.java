package org.scribble.del.local;

import org.scribble.ast.MessageSigNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LReceive;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.EGraphBuilder;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;
import org.scribble.visit.context.UnguardedChoiceDoProjectionChecker;
import org.scribble.visit.context.env.UnguardedChoiceDoEnv;

public class LReceiveDel extends LMessageTransferDel
{
	@Override
	public LReceive leaveEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder builder, ScribNode visited) throws ScribbleException
	{
		LReceive lr = (LReceive) visited;
		Role peer = lr.src.toName();
		MessageId<?> mid = lr.msg.toMessage().getId();
		Payload payload = (lr.msg.isMessageSigNode())  // Hacky?
				? ((MessageSigNode) lr.msg).payloads.toPayload()
				: Payload.EMPTY_PAYLOAD;
		builder.util.addEdge(builder.util.getEntry(), new EReceive(peer, mid, payload), builder.util.getExit());
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
