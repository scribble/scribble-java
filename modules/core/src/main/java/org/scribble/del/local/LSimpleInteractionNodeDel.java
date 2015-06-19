package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LSimpleInteractionNode;
import org.scribble.del.SimpleInteractionNodeDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ReachabilityChecker;

public abstract class LSimpleInteractionNodeDel extends SimpleInteractionNodeDel
{
	public LSimpleInteractionNodeDel()
	{

	}

	@Override
	//public ReachabilityChecker enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException
	public void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	{
		//return (ReachabilityChecker) pushEnv(parent, child, checker);
		pushVisitorEnv(parent, child, checker);
	}

	@Override
	public LSimpleInteractionNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		return (LSimpleInteractionNode) popAndSetVisitorEnv(parent, child, checker, visited);
	}

	/*@Override
	public void enterFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv)
	{
		pushVisitorEnv(parent, child, conv);
	}

	@Override
	public ModelNode leaveFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv, ModelNode visited)// throws ScribbleException;
	{
		return (LSimpleInteractionNode) popAndSetVisitorEnv(parent, child, conv, visited);
	}*/
}
