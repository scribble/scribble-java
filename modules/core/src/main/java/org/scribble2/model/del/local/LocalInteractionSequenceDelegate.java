package org.scribble2.model.del.local;

import org.scribble2.model.del.InteractionSequenceDelegate;


// FIXME: should be a CompoundInteractionDelegate? -- no: compound interaction delegates for typing contexts (done for block only, not seqs)
public class LocalInteractionSequenceDelegate extends InteractionSequenceDelegate
{
	/*@Override
	public ReachabilityChecker enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException
	{
		return (ReachabilityChecker) pushEnv(parent, child, checker);
	}

	@Override
	public ModelNode leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		return popAndSetEnv(parent, child, checker, visited);
	}*/
}
