package org.scribble2.model.del.local;

import java.util.Collections;

import org.scribble2.fsm.FsmBuilder;
import org.scribble2.fsm.ProtocolState;
import org.scribble2.fsm.Receive;
import org.scribble2.fsm.ScribbleFsm;
import org.scribble2.model.ModelNode;
import org.scribble2.model.local.LReceive;
import org.scribble2.model.visit.FsmConverter;
import org.scribble2.model.visit.env.FsmBuildingEnv;
import org.scribble2.sesstype.name.MessageId;
import org.scribble2.sesstype.name.Role;


public class LReceiveDel extends LSimpleInteractionNodeDel
{
	@Override
	public LReceive leaveFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv, ModelNode visited)
	{
		LReceive lr = (LReceive) visited;
		FsmBuilder b = new FsmBuilder();
		ProtocolState init = b.makeInit(Collections.emptySet());
		ProtocolState term = b.newState(Collections.emptySet());
		Role peer = lr.src.toName();
		MessageId mid = lr.msg.toMessage().getId();
		b.addEdge(init, new Receive(peer, mid), term);
		ScribbleFsm f = b.build();
		FsmBuildingEnv env = conv.popEnv();
		conv.pushEnv(env.setFsm(f));
		return (LReceive) super.leaveFsmConversion(parent, child, conv, lr);
	}
}
