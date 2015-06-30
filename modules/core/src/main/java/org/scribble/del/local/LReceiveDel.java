package org.scribble.del.local;

import org.scribble.ast.MessageSigNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LReceive;
import org.scribble.model.local.Receive;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.FsmBuilder;


public class LReceiveDel extends LSimpleInteractionNodeDel
{
	@Override
	public LReceive leaveFsmBuilder(ScribNode parent, ScribNode child, FsmBuilder conv, ScribNode visited)
	{
		LReceive lr = (LReceive) visited;
		Role peer = lr.src.toName();
		MessageId mid = lr.msg.toMessage().getId();
		Payload payload =
				(lr.msg.isMessageSigNode())  // Hacky?
					? ((MessageSigNode) lr.msg).payload.toPayload()
					: Payload.EMPTY_PAYLOAD;
		conv.builder.addEdge(conv.builder.getEntry(), new Receive(peer, mid, payload), conv.builder.getExit());
		return (LReceive) super.leaveFsmBuilder(parent, child, conv, lr);
	}
}
