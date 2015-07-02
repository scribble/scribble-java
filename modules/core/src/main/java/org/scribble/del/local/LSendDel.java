package org.scribble.del.local;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.MessageSigNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LSend;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.Send;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.FsmBuilder;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.env.UnfoldingEnv;


public class LSendDel extends LSimpleInteractionNodeDel
{
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		LSend gmt = (LSend) visited;
		LSend inlined = AstFactoryImpl.FACTORY.LSend(gmt.src, gmt.msg, gmt.dests);  // FIXME: clone
		builder.pushEnv(builder.popEnv().setTranslation(inlined));
		return (LSend) super.leaveProtocolInlining(parent, child, builder, gmt);
	}

	@Override
	public void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException
	{
		UnfoldingEnv env = unf.popEnv();
		env = env.noUnfold();
		unf.pushEnv(env);
	}

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
