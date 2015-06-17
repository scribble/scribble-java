package org.scribble.del;

import org.scribble.ast.ModelNode;
import org.scribble.ast.visit.WellFormedChoiceChecker;
import org.scribble.util.ScribbleException;

// For CompoundInteractionNode and ProtocolBlock
public abstract class CompoundInteractionDel extends ModelDelBase
{
	//public CompoundInteractionDelegate(Env env)
	public CompoundInteractionDel()
	{
		//super(env);
	}

	@Override
	//public WellFormedChoiceChecker enterWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker) throws ScribbleException
	public void enterWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker) throws ScribbleException
	{
		/*//WellFormedChoiceEnv env = new WellFormedChoiceEnv(checker.peekEnv());
		WellFormedChoiceEnv env = checker.peekEnv().push();
		/*env.initial.clear();
		env.initialInterrupts.clear();*
		//env = env.enableChoiceSubject(((GlobalChoice) child).subj.toName());
		//checker.setEnv(env);
		checker.pushEnv(env);
		return checker;*/
		//return (WellFormedChoiceChecker) pushEnv(parent, child, checker);
		pushVisitorEnv(parent, child, checker);
	}
	//public void enter(Choice<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> cho, WellFormedChoiceChecker checker)
	
	@Override
	public ModelNode leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		/*WellFormedChoiceEnv env = checker.popEnv();
		//env = checker.popEnv().merge(env);  // No merge here: merging of child blocks is handled "manually" by the compound interaction nodes
		//checker.pushEnv(env);
		setEnv(env);
		return visited;*/
		return popAndSetVisitorEnv(parent, child, checker, visited);
	}

	// Projection only done for global nodes, Reachability Check only for local
	
	/*@Override
	public Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	{
		/*ProjectionEnv env = proj.peekEnv().push();
		proj.pushEnv(env);
		return proj;*
		return (Projector) enter(parent, child, proj);
	}
	
	@Override
	public ModelNode leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException
	{
		/*ProjectionEnv env = proj.popEnv();
		//env = checker.popEnv().merge(env);  // No merge here: merging of child blocks is handled "manually" by the compound interaction nodes
		//checker.pushEnv(env);
		setEnv(env);
		return visited;*
		return leave(parent, child, proj, visited);
	}*/

	/*@Override
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
