package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LCompoundInteractionNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.ReachabilityEnv;

// For CompoundInteractionNode and ProtocolBlock
public interface LCompoundInteractionNodeDel extends LInteractionNodeDel
{

	@Override
	default LCompoundInteractionNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		// Following CompoundInteractionNodeDel#leaveInlinedProtocolUnfolding/leaveWFChoiceCheck
		ReachabilityEnv visited_env = checker.popEnv();
		ReachabilityEnv parent_env = checker.popEnv();
		setEnv(visited_env);
		parent_env = parent_env.mergeContext(visited_env);
		checker.pushEnv(parent_env);
		return (LCompoundInteractionNode) visited;
	}
}
