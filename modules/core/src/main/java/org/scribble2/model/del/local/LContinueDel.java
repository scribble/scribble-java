package org.scribble2.model.del.local;

import org.scribble2.model.ModelNode;
import org.scribble2.model.local.LContinue;
import org.scribble2.model.visit.FsmConverter;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.model.visit.env.ReachabilityEnv;
import org.scribble2.sesstype.name.RecVar;
import org.scribble2.util.ScribbleException;

public class LContinueDel extends LSimpleInteractionNodeDel
{
	public LContinue leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		LContinue lc = (LContinue) visited;
		// "Entering" the continue here in leave, where we can merge the new state into the parent Env
		// Generally: if side effecting Env state to be merged into the parent (not just popped and discarded), leave must be overridden to do so

		ReachabilityEnv env = checker.popEnv().leaveContinue(lc.recvar.toName());
		/*checker.pushEnv(env);
		setEnv(env);  // Env recording probably not needed for all LocalInteractionNodes, just the compound ones, like for WF-choice checking
		env = checker.popEnv().mergeContext(env);
		checker.pushEnv(env);*/
		setEnv(env);  // Env recording probably not needed for all LocalInteractionNodes, just the compound ones, like for WF-choice checking
		checker.pushEnv(checker.popEnv().mergeContext(env));
		return lc;
	}

	@Override
	public LContinue leaveFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv, ModelNode visited)
	{
		LContinue lr = (LContinue) visited;
		RecVar rv = lr.recvar.toName();
		conv.builder.setEntry(conv.builder.getRecursionEntry(rv));
		return (LContinue) super.leaveFsmConversion(parent, child, conv, lr);
	}
}
