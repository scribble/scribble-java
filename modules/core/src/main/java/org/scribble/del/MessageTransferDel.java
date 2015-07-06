package org.scribble.del;

import org.scribble.ast.MessageTransfer;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.MessageId;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.MessageIdCollector;
import org.scribble.visit.env.UnfoldingEnv;

public abstract class MessageTransferDel extends SimpleInteractionNodeDel
{
	public MessageTransferDel()
	{

	}

	@Override
	public void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException
	{
		UnfoldingEnv env = unf.popEnv();
		env = env.noUnfold();
		unf.pushEnv(env);
	}

	@Override
	public ScribNode leaveMessageIdCollection(ScribNode parent, ScribNode child, MessageIdCollector coll, ScribNode visited)
	{
		MessageTransfer<?> gmt = (MessageTransfer<?>) visited;
		if (gmt.msg.isMessageSigNode() || gmt.msg.isMessageSigNameNode())
		{
			coll.addName((MessageId<?>) gmt.msg.toMessage().getId());
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + gmt.msg);
		}
		return visited;
	}
}
