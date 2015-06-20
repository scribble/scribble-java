package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LCompoundInteractionNode;
import org.scribble.del.CompoundInteractionNodeDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.ReachabilityEnv;

// For CompoundInteractionNode and ProtocolBlock
public class LCompoundInteractionNodeDel extends CompoundInteractionNodeDel
{
	//public CompoundInteractionDelegate(Env env)
	public LCompoundInteractionNodeDel()
	{
		//super(env);
	}

	// Unlike WF-choice enter/leave for CompoundInteractionNodeDelegate (i.e. both global/local), reachability is limited to local only
	@Override
	//public ReachabilityChecker enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException
	public void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	{
		//return (ReachabilityChecker) pushEnv(parent, child, checker);
		pushVisitorEnv(parent, child, checker);
	}

	@Override
	public ScribNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		// Following leaveWFChoiceCheck
		ReachabilityEnv visited_env = checker.popEnv();
		ReachabilityEnv parent_env = checker.popEnv();
		setEnv(visited_env);
		parent_env = parent_env.mergeContext(visited_env);
		checker.pushEnv(parent_env);
		return (LCompoundInteractionNode) visited;
	}

	/*@Override
	public void enterFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv)
	{
		pushVisitorEnv(parent, child, conv);
	}

	@Override
	public ModelNode leaveFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv, ModelNode visited)
	{
		/*FsmBuildingEnv env = conv.popEnv();
		setEnv(env);
		env = conv.popEnv().mergeContext(env);  // Overrides super method to merge results back into parent context
		conv.pushEnv(env);
		return (LCompoundInteractionNode) visited;* /
		return popAndSetVisitorEnv(parent, child, conv, visited);
	}*/
}
