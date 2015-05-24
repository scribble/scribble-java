package org.scribble2.model.del.local;

import org.scribble2.fsm.Receive;
import org.scribble2.model.MessageSigNode;
import org.scribble2.model.ModelNode;
import org.scribble2.model.local.LReceive;
import org.scribble2.model.visit.FsmConstructor;
import org.scribble2.sesstype.Payload;
import org.scribble2.sesstype.name.MessageId;
import org.scribble2.sesstype.name.Role;


public class LReceiveDel extends LSimpleInteractionNodeDel
{
	@Override
	public LReceive leaveFsmConstruction(ModelNode parent, ModelNode child, FsmConstructor conv, ModelNode visited)
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
