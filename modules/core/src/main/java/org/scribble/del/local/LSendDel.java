package org.scribble.del.local;

import java.util.List;

import org.scribble.ast.MessageSigNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LSend;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.MessageTransferDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.Send;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.FsmBuilder;
import org.scribble.visit.ProtocolDefInliner;

public class LSendDel extends MessageTransferDel implements LSimpleInteractionNodeDel
{
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		LSend ls = (LSend) visited;
		//LSend inlined = AstFactoryImpl.FACTORY.LSend(gmt.src, gmt.msg, gmt.dests);  // FIXME: clone
		LSend inlined = ls.clone();
		builder.pushEnv(builder.popEnv().setTranslation(inlined));
		return (LSend) super.leaveProtocolInlining(parent, child, builder, ls);
	}

	@Override
	public LSend leaveFsmBuilding(ScribNode parent, ScribNode child, FsmBuilder conv, ScribNode visited)
	{
		LSend ls = (LSend) visited;
		List<RoleNode> dests = ls.getDestinations();
		if (dests.size() > 1)
		{
			throw new RuntimeException("TODO: " + ls);
		}
		Role peer = dests.get(0).toName();
		MessageId<?> mid = ls.msg.toMessage().getId();
		Payload payload =
				ls.msg.isMessageSigNode()  // Hacky?
					? ((MessageSigNode) ls.msg).payloads.toPayload()
					: Payload.EMPTY_PAYLOAD;
		conv.builder.addEdge(conv.builder.getEntry(), new Send(peer, mid, payload), conv.builder.getExit());
		return (LSend) super.leaveFsmBuilding(parent, child, conv, ls);
	}
}
