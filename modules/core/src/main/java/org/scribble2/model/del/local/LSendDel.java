package org.scribble2.model.del.local;

import java.util.Collections;

import org.scribble2.fsm.FsmBuilder;
import org.scribble2.fsm.ProtocolState;
import org.scribble2.fsm.ScribbleFsm;
import org.scribble2.model.ModelNode;
import org.scribble2.model.local.LSend;
import org.scribble2.model.visit.FsmConverter;
import org.scribble2.model.visit.env.FsmBuildingEnv;


public class LSendDel extends LSimpleInteractionNodeDel
{
	@Override
	public LSend leaveFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv, ModelNode visited)
	{
		LSend send = (LSend) visited;
		FsmBuilder b = new FsmBuilder();
		ProtocolState init = b.makeInit(Collections.emptySet());
		ProtocolState term = b.newState(Collections.emptySet());
		b.addEdge(init, send.msg.toMessage().getId(), term);
		ScribbleFsm f = b.build();
		FsmBuildingEnv env = conv.popEnv();
		conv.pushEnv(env.setFsm(f));
		return (LSend) super.popAndSetVisitorEnv(parent, child, conv, send);
	}
}
