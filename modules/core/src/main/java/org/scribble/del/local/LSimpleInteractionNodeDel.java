package org.scribble.del.local;

import org.scribble.ast.ModelNode;
import org.scribble.ast.local.LSimpleInteractionNode;
import org.scribble.ast.visit.ReachabilityChecker;
import org.scribble.del.SimpleInteractionNodeDel;
import org.scribble.util.ScribbleException;

public abstract class LSimpleInteractionNodeDel extends SimpleInteractionNodeDel
{
	public LSimpleInteractionNodeDel()
	{

	}

	@Override
	//public ReachabilityChecker enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException
	public void enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException
	{
		//return (ReachabilityChecker) pushEnv(parent, child, checker);
		pushVisitorEnv(parent, child, checker);
	}

	@Override
	public LSimpleInteractionNode leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
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
