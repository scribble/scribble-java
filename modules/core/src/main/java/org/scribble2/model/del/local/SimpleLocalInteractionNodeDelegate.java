package org.scribble2.model.del.local;

import org.scribble2.model.ModelNode;
import org.scribble2.model.del.SimpleInteractionNodeDelegate;
import org.scribble2.model.local.SimpleLocalInteractionNode;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.util.ScribbleException;

// For CompoundInteractionNode and ProtocolBlock
public class SimpleLocalInteractionNodeDelegate extends SimpleInteractionNodeDelegate
{
	//public CompoundInteractionDelegate(Env env)
	public SimpleLocalInteractionNodeDelegate()
	{
		//super(env);
	}

	@Override
	public ReachabilityChecker enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException
	{
		return (ReachabilityChecker) pushEnv(parent, child, checker);
	}

	@Override
	public SimpleLocalInteractionNode leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		return (SimpleLocalInteractionNode) popAndSetEnv(parent, child, checker, visited);
	}
}
