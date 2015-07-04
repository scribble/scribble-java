package org.scribble.del.local;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LContinue;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.FsmBuilder;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.ReachabilityEnv;

public class LContinueDel extends LSimpleInteractionNodeDel
{
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		LContinue lc = (LContinue) visited;
		RecVarNode recvar = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, lc.recvar.toName().toString());
		LContinue inlined = AstFactoryImpl.FACTORY.LContinue(recvar);
		builder.pushEnv(builder.popEnv().setTranslation(inlined));
		return (LContinue) super.leaveProtocolInlining(parent, child, builder, lc);
	}

	@Override
	public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		LContinue gc = (LContinue) visited;
		RecVar rv = gc.recvar.toName();
		if (unf.isTodo(rv))
		{
			return unf.getRecVar(rv).clone();
		}
		return gc;
	}

	@Override
	public LContinue leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		// "Entering" the continue here in leave, where we can merge the new state into the parent Env
		// Generally: if side effecting Env state to be merged into the parent (not just popped and discarded), leave must be overridden to do so
		LContinue lc = (LContinue) visited;
		ReachabilityEnv env = checker.popEnv().leaveContinue(lc.recvar.toName());
		setEnv(env);  // Env recording probably not needed for all LocalInteractionNodes, just the compound ones, like for WF-choice checking
		checker.pushEnv(checker.popEnv().mergeContext(env));
		return lc;
	}

	@Override
	public LContinue leaveFsmBuilder(ScribNode parent, ScribNode child, FsmBuilder conv, ScribNode visited)
	{
		LContinue lr = (LContinue) visited;
		RecVar rv = lr.recvar.toName();
		conv.builder.setEntry(conv.builder.getRecursionEntry(rv));
		return (LContinue) super.leaveFsmBuilder(parent, child, conv, lr);
	}
}
