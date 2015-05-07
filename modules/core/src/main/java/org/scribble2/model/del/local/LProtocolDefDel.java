package org.scribble2.model.del.local;

import org.scribble2.model.del.ProtocolDefDel;

public class LProtocolDefDel extends ProtocolDefDel
{
	public LProtocolDefDel()
	{

	}

	/*@Override
	public ReachabilityChecker enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException
	{
		return (ReachabilityChecker) pushEnv(parent, child, checker);
	}

	@Override
	public LocalProtocolDefinition leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		return (LocalProtocolDefinition) popAndSetEnv(parent, child, checker, visited);
	}*/
}
