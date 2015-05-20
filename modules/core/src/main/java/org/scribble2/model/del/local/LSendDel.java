package org.scribble2.model.del.local;

import org.scribble2.fsm.Send;
import org.scribble2.model.ModelNode;
import org.scribble2.model.local.LSend;
import org.scribble2.model.visit.FsmConstructor;
import org.scribble2.sesstype.name.MessageId;
import org.scribble2.sesstype.name.Role;


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
		conv.builder.addEdge(conv.builder.getEntry(), new Send(peer, mid), conv.builder.getExit());
		return (LSend) super.leaveFsmConstruction(parent, child, conv, ls);
	}
}
