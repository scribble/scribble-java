package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LCompoundInteractionNode;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.ReachabilityEnv;

// For CompoundInteractionNode and ProtocolBlock
//public class LCompoundInteractionNodeDel extends CompoundInteractionNodeDel
public interface LCompoundInteractionNodeDel extends LInteractionNodeDel
{
	/*public LCompoundInteractionNodeDel()
	{

	}*/

	// Unlike WF-choice enter/leave for CompoundInteractionNodeDelegate (i.e. both global/local), reachability is limited to local only
	//@Override
	//public void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	default void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	{
		//pushVisitorEnv(parent, child, checker);
		ScribDelBase.pushVisitorEnv(this, checker);
	}

	//@Override
	//public ScribNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	default ScribNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		// Following leaveWFChoiceCheck
		ReachabilityEnv visited_env = checker.popEnv();
		ReachabilityEnv parent_env = checker.popEnv();
		//setEnv(visited_env);
		setEnv(visited_env);
		parent_env = parent_env.mergeContext(visited_env);
		checker.pushEnv(parent_env);
		return (LCompoundInteractionNode) visited;
	}
}
