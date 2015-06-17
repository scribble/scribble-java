package org.scribble.del.local;

import org.scribble.ast.MessageSigNode;
import org.scribble.ast.ModelNode;
import org.scribble.ast.local.LSend;
import org.scribble.ast.visit.FsmConstructor;
import org.scribble.model.local.Send;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;


public class LSendDel extends LSimpleInteractionNodeDel
{
	@Override
	public LSend leaveFsmConstruction(ModelNode parent, ModelNode child, FsmConstructor conv, ModelNode visited)
	{
		LSend ls = (LSend) visited;
		/*FsmBuilder b = new FsmBuilder();
		ProtocolState init = b.makeInit(Collections.emptySet());
		ProtocolState term = b.newState(Collections.emptySet());
		if (ls.dests.size() > 1)
		{
			throw new RuntimeException("TODO: " + ls);
		}
		Role peer = ls.dests.get(0).toName();
		MessageId mid = ls.msg.toMessage().getId();
		b.addEdge(init, new Send(peer, mid), term);
		ScribbleFsm f = b.build();
		FsmBuildingEnv env = conv.popEnv();
		conv.pushEnv(env.setFsm(f));*/
		if (ls.dests.size() > 1)
		{
			throw new RuntimeException("TODO: " + ls);
		}
		Role peer = ls.dests.get(0).toName();
		MessageId mid = ls.msg.toMessage().getId();
		Payload payload;
		if (ls.msg.isMessageSigNode())  // Hacky?
		{
			payload = ((MessageSigNode) ls.msg).payload.toPayload();
		}
		else
		{
			payload = Payload.EMPTY_PAYLOAD;
		}
		conv.builder.addEdge(conv.builder.getEntry(), new Send(peer, mid, payload), conv.builder.getExit());
		return (LSend) super.leaveFsmConstruction(parent, child, conv, ls);
	}
}
