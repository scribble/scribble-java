package org.scribble.del;

import org.scribble.ast.MessageTransfer;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.MessageId;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.MessageIdCollector;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.RoleCollector;
import org.scribble.visit.env.UnfoldingEnv;

public abstract class MessageTransferDel extends SimpleInteractionNodeDel
{
	public MessageTransferDel()
	{

	}

	@Override
	public MessageTransfer<?> leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		MessageTransfer<?> lr = (MessageTransfer<?>) visited;
		MessageTransfer<?> inlined = (MessageTransfer<?>) lr.clone();
		builder.pushEnv(builder.popEnv().setTranslation(inlined));
		return (MessageTransfer<?>) super.leaveProtocolInlining(parent, child, builder, lr);
	}

	@Override
	public void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException
	{
		UnfoldingEnv env = unf.popEnv();
		env = env.noUnfold();
		unf.pushEnv(env);
	}

	@Override
	public ScribNode leaveRoleCollection(ScribNode parent, ScribNode child, RoleCollector coll, ScribNode visited)
	{
		MessageTransfer<?> mt = (MessageTransfer<?>) visited;
		coll.addName(mt.src.toName());
		mt.getDestinationRoles().stream().forEach((rd) -> coll.addName(rd));
		return visited;
	}

	@Override
	public ScribNode leaveMessageIdCollection(ScribNode parent, ScribNode child, MessageIdCollector coll, ScribNode visited)
	{
		MessageTransfer<?> mt = (MessageTransfer<?>) visited;
		if (mt.msg.isMessageSigNode() || mt.msg.isMessageSigNameNode())
		{
			coll.addName((MessageId<?>) mt.msg.toMessage().getId());
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + mt.msg);
		}
		return visited;
	}
}