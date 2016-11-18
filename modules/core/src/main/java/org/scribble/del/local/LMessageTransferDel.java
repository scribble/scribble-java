package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LMessageTransfer;
import org.scribble.del.MessageTransferDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.wf.ExplicitCorrelationChecker;

public abstract class LMessageTransferDel extends MessageTransferDel implements LSimpleInteractionNodeDel
{
	@Override
	public LMessageTransfer leaveExplicitCorrelationCheck(ScribNode parent, ScribNode child, ExplicitCorrelationChecker checker, ScribNode visited) throws ScribbleException
	{
		LMessageTransfer lmt = (LMessageTransfer) visited;
		checker.pushEnv(checker.popEnv().disableAccept());
		return lmt;
	}
}
