package org.scribble.del.local;

import org.scribble.ast.MessageSigNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LSend;
import org.scribble.model.local.Send;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.FsmBuilder;


public class LSendDel extends LSimpleInteractionNodeDel
{
	@Override
	public LSend leaveFsmBuilder(ScribNode parent, ScribNode child, FsmBuilder conv, ScribNode visited)
	{
		LSend ls = (LSend) visited;
		if (ls.dests.size() > 1)
		{
			throw new RuntimeException("TODO: " + ls);
		}
		Role peer = ls.dests.get(0).toName();
		MessageId mid = ls.msg.toMessage().getId();
		Payload payload =
				ls.msg.isMessageSigNode()  // Hacky?
					? ((MessageSigNode) ls.msg).payload.toPayload()
					: Payload.EMPTY_PAYLOAD;
		conv.builder.addEdge(conv.builder.getEntry(), new Send(peer, mid, payload), conv.builder.getExit());
		return (LSend) super.leaveFsmBuilder(parent, child, conv, ls);
	}
}
