package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LContinue;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.FsmConstructor;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.ReachabilityEnv;

public class LContinueDel extends LSimpleInteractionNodeDel
{
	public LContinue leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
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
	public LContinue leaveFsmConstruction(ScribNode parent, ScribNode child, FsmConstructor conv, ScribNode visited)
	{
		LContinue lr = (LContinue) visited;
		RecVar rv = lr.recvar.toName();
		conv.builder.setEntry(conv.builder.getRecursionEntry(rv));
		return (LContinue) super.leaveFsmConstruction(parent, child, conv, lr);
	}
}
