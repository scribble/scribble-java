package org.scribble.ast.del.local;

import org.scribble.ast.del.ProtocolDefDel;

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

	/*@Override
	public void enterFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv)
	{
		pushVisitorEnv(parent, child, conv);
	}

	@Override
	public ModelNode leaveFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv, ModelNode visited)
	{
		/*LProtocolDef def = (LProtocolDef) visited;
		ScribbleFsm f = ((FsmBuildingEnv) def.block.del().env()).getFsm();	
		FsmBuildingEnv env = conv.popEnv();
		conv.pushEnv(env.setFsm(f));* /
		return popAndSetVisitorEnv(parent, child, conv, visited);
	}*/
}
