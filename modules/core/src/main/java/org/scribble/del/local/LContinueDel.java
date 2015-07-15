package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LContinue;
import org.scribble.del.ContinueDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.FsmGenerator;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.ReachabilityEnv;

public class LContinueDel extends ContinueDel implements LSimpleInteractionNodeDel
{
	@Override
	public LContinue leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		// "Entering" the continue here in leave, where we can merge the new state into the parent Env
		// Generally: if side effecting Env state to be merged into the parent (not just popped and discarded), leave must be overridden to do so
		LContinue lc = (LContinue) visited;
		ReachabilityEnv env = checker.popEnv().addContinueLabel(lc.recvar.toName());
		setEnv(env);  // Env recording probably not needed for all LocalInteractionNodes, just the compound ones, like for WF-choice checking
		checker.pushEnv(checker.popEnv().mergeContext(env));
		return lc;
	}

	@Override
	public LContinue leaveFsmGeneration(ScribNode parent, ScribNode child, FsmGenerator conv, ScribNode visited)
	{
		LContinue lr = (LContinue) visited;
		RecVar rv = lr.recvar.toName();
		conv.builder.setEntry(conv.builder.getRecursionEntry(rv));
		return (LContinue) super.leaveFsmGeneration(parent, child, conv, lr);
	}
}
