package org.scribble2.model.del.local;

import org.scribble2.model.ModelNode;
import org.scribble2.model.del.SimpleInteractionNodeDel;
import org.scribble2.model.local.LSimpleInteractionNode;
import org.scribble2.model.visit.FsmConverter;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.util.ScribbleException;

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

	@Override
	public void enterFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv)
	{
		pushVisitorEnv(parent, child, conv);
	}

	@Override
	public ModelNode leaveFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv, ModelNode visited)// throws ScribbleException;
	{
		return (LSimpleInteractionNode) popAndSetVisitorEnv(parent, child, conv, visited);
	}
}
