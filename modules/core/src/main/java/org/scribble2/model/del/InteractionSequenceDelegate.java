package org.scribble2.model.del;



// FIXME: should be a CompoundInteractionDelegate? -- no: compound interaction delegates for typing contexts (done for block only, not seqs)
public class InteractionSequenceDelegate extends ModelDelegateBase
{
	// Projection only done for global nodes, Reachability Check only for local

	/*@Override
	public Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	{
		return (Projector) enter(parent, child, proj);
	}
	
	@Override
	public ModelNode leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException
	{
		return leave(parent, child, proj, visited);
	}

	@Override
	public ReachabilityChecker enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException
	{
		return (ReachabilityChecker) enter(parent, child, checker);
	}
	
	@Override
	public ModelNode leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		return leave(parent, child, checker, visited);
	}*/
}
