package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.del.InteractionNodeDel;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.visit.wf.ReachabilityChecker;

public interface LInteractionNodeDel extends InteractionNodeDel
{
	// Unlike WF-choice enter/leave for CompoundInteractionNodeDelegate (i.e. both global/local), reachability is limited to local only
	@Override
	default void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	{
		ScribDelBase.pushVisitorEnv(this, checker);
	}

	@Override
	default LInteractionNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		return (LInteractionNode) ScribDelBase.popAndSetVisitorEnv(this, checker, visited);
	}
}
