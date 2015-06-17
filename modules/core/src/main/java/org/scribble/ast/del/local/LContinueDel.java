package org.scribble.ast.del.local;

import org.scribble.ast.ModelNode;
import org.scribble.ast.local.LContinue;
import org.scribble.ast.visit.FsmConstructor;
import org.scribble.ast.visit.ReachabilityChecker;
import org.scribble.ast.visit.env.ReachabilityEnv;
import org.scribble.sesstype.name.RecVar;
import org.scribble.util.ScribbleException;

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
	public LContinue leaveFsmConstruction(ModelNode parent, ModelNode child, FsmConstructor conv, ModelNode visited)
	{
		LContinue lr = (LContinue) visited;
		RecVar rv = lr.recvar.toName();
		conv.builder.setEntry(conv.builder.getRecursionEntry(rv));
		return (LContinue) super.leaveFsmConstruction(parent, child, conv, lr);
	}
}
