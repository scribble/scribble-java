package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LConnectionAction;
import org.scribble.del.ConnectionActionDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.wf.ExplicitCorrelationChecker;

public abstract class LConnectionActionDel extends ConnectionActionDel implements LSimpleInteractionNodeDel
{
	@Override
	public LConnectionAction leaveExplicitCorrelationCheck(ScribNode parent, ScribNode child, ExplicitCorrelationChecker checker, ScribNode visited) throws ScribbleException
	{
		LConnectionAction lca = (LConnectionAction) visited;
		checker.pushEnv(checker.popEnv().disableAccept());
		return lca;
	}
}
