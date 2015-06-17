package org.scribble.del.local;

import org.scribble.ast.ModelNode;
import org.scribble.ast.local.LCompoundInteractionNode;
import org.scribble.ast.visit.ReachabilityChecker;
import org.scribble.ast.visit.env.ReachabilityEnv;
import org.scribble.del.CompoundInteractionNodeDel;
import org.scribble.util.ScribbleException;

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
	public void enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException
	{
		//return (ReachabilityChecker) pushEnv(parent, child, checker);
		pushVisitorEnv(parent, child, checker);
	}

	@Override
	public ModelNode leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		/*ProjectionEnv env = proj.popEnv();
		//env = checker.popEnv().merge(env);  // No merge here: merging of child blocks is handled "manually" by the compound interaction nodes
		//checker.pushEnv(env);
		setEnv(env);
		return visited;*
		en.setEnv(this);
		checker.setEnv(pop());
		//en.setEnv(copy());
		//ReachabilityEnv parent = pop();
		//parent = parent.merge(en, this);  // No: blocks don't merge themselves into parent, parents take blocks and merge
		//checker.setEnv(parent);*/
		ReachabilityEnv env = checker.popEnv();
		setEnv(env);
		env = checker.popEnv().mergeContext(env);  // Overrides super method to merge results back into parent context
		checker.pushEnv(env);
		//setEnv(env);
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
