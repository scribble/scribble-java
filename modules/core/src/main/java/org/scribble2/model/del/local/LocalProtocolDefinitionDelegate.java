package org.scribble2.model.del.local;

import org.scribble2.model.ModelNode;
import org.scribble2.model.del.ProtocolDefinitionDelegate;
import org.scribble2.model.local.LocalProtocolDefinition;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.util.ScribbleException;

public class LocalProtocolDefinitionDelegate extends ProtocolDefinitionDelegate
{
	public LocalProtocolDefinitionDelegate()
	{

	}

	@Override
	public ReachabilityChecker enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException
	{
		return (ReachabilityChecker) pushEnv(parent, child, checker);
	}

	@Override
	public LocalProtocolDefinition leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		return (LocalProtocolDefinition) popAndSetEnv(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
