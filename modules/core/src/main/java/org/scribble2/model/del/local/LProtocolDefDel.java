package org.scribble2.model.del.local;

import org.scribble2.fsm.ScribbleFsm;
import org.scribble2.model.ModelNode;
import org.scribble2.model.del.ProtocolDefDel;
import org.scribble2.model.local.LProtocolDef;
import org.scribble2.model.visit.FsmConverter;
import org.scribble2.model.visit.env.FsmBuildingEnv;

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

	@Override
	public void enterFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv)
	{
		pushVisitorEnv(parent, child, conv);
	}

	@Override
	public ModelNode leaveFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv, ModelNode visited)
	{
		LProtocolDef def = (LProtocolDef) visited;
		ScribbleFsm f = ((FsmBuildingEnv) def.block.del().env()).getFsm();	
		FsmBuildingEnv env = conv.popEnv();
		conv.pushEnv(env.setFsm(f));
		return popAndSetVisitorEnv(parent, child, conv, visited);
	}
}
