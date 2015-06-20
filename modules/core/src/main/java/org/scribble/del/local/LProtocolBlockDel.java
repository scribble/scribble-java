package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.del.ProtocolBlockDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ReachabilityChecker;


public class LProtocolBlockDel extends ProtocolBlockDel
{
	@Override
	public void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	{
		pushVisitorEnv(parent, child, checker);
	}

	@Override
	public LProtocolBlock leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		return (LProtocolBlock) popAndSetVisitorEnv(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
