package org.scribble.del.local;

import org.scribble.ast.ModelNode;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.visit.ReachabilityChecker;
import org.scribble.del.ProtocolBlockDel;
import org.scribble.util.ScribbleException;


public class LProtocolBlockDel extends ProtocolBlockDel
{
	@Override
	//public ReachabilityChecker enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException
	public void enterReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker) throws ScribbleException
	{
		//return (ReachabilityChecker) pushEnv(parent, child, checker);
		pushVisitorEnv(parent, child, checker);
	}

	@Override
	public LProtocolBlock leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		return (LProtocolBlock) popAndSetVisitorEnv(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env
		/*LocalProtocolBlock lpd = (LocalProtocolBlock) visited;
		LocalInteractionSequence seq = (LocalInteractionSequence) ((ProjectionEnv) lpd.seq.del().getEnv()).getProjection();	
		LocalProtocolBlock projection = ModelFactoryImpl.FACTORY.LocalProtocolBlock(seq);
		ReachabilityEnv env = checker.popEnv();
		checker.pushEnv(new ReachabilityEnv(env.getJobContext(), env.getModuleDelegate(), ...));
		return (LocalProtocolBlock) super.popAndSetEnv(parent, child, checker, lpd);*/
	}

	/*@Override
	public void enterFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv)
	{
		pushVisitorEnv(parent, child, conv);
	}

	@Override
	public ModelNode leaveFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv, ModelNode visited)
	{
		/*LProtocolBlock block = (LProtocolBlock) visited;
		ScribbleFsm f = ((FsmBuildingEnv) block.seq.del().env()).getFsm();	
		FsmBuildingEnv env = conv.popEnv();
		conv.pushEnv(env.setFsm(f));* /
		return popAndSetVisitorEnv(parent, child, conv, visited);
	}*/

	/*@Override
	public EnvVisitor envEnter(ModelNode parent, ModelNode child, EnvVisitor ev) throws ScribbleException
	{
		WellFormedChoiceEnv env = checker.peekEnv().push();
		env = env.clear();
		env = env.enableChoiceSubject(((GlobalChoice) child).subj.toName());
		checker.pushEnv(env);
		return checker;
		
		//.. push env for blocks in EnvVitistor.subprotocolEnter; or override every visitor pass in block delegate -- better in ProtocolBlockDelegate?
	}*/
	
	/*@Override
	public GlobalProtocolBlock leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		return (GlobalProtocolBlock) super.leaveWFChoiceCheck(parent, child, checker, visited);
	}*/
}
