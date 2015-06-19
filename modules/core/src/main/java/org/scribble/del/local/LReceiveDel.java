package org.scribble.del.local;

import org.scribble.ast.MessageSigNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LReceive;
import org.scribble.model.local.Receive;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.FsmConstructor;


public class LReceiveDel extends LSimpleInteractionNodeDel
{
	@Override
	public LReceive leaveFsmConstruction(ScribNode parent, ScribNode child, FsmConstructor conv, ScribNode visited)
	{
		LReceive lr = (LReceive) visited;
		/*FsmBuilder b = new FsmBuilder();
		ProtocolState init = b.makeInit(Collections.emptySet());
		ProtocolState term = b.newState(Collections.emptySet());
		Role peer = lr.src.toName();
		MessageId mid = lr.msg.toMessage().getId();
		b.addEdge(init, new Receive(peer, mid), term);
		ScribbleFsm f = b.build();
		FsmBuildingEnv env = conv.popEnv();
		conv.pushEnv(env.setFsm(f));*/
		Role peer = lr.src.toName();
		MessageId mid = lr.msg.toMessage().getId();
		Payload payload;
		if (lr.msg.isMessageSigNode())  // Hacky?
		{
			payload = ((MessageSigNode) lr.msg).payload.toPayload();
		}
		else
		{
			payload = Payload.EMPTY_PAYLOAD;
		}
		conv.builder.addEdge(conv.builder.getEntry(), new Receive(peer, mid, payload), conv.builder.getExit());
		return (LReceive) super.leaveFsmConstruction(parent, child, conv, lr);
	}
}
