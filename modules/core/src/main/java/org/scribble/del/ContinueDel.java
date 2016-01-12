package org.scribble.del;

import org.scribble.ast.Continue;
import org.scribble.ast.Recursion;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.ProtocolDefInliner;

public abstract class ContinueDel extends SimpleInteractionNodeDel
{
	public ContinueDel()
	{

	}

	@Override
	public Continue<?> leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		Continue<?> lc = (Continue<?>) visited;
		Continue<?> inlined = (Continue<?>) lc.clone();
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (Continue<?>) super.leaveProtocolInlining(parent, child, inl, lc);
	}

	@Override
	public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		Continue<?> cont = (Continue<?>) visited;
		RecVar rv = cont.recvar.toName();
		if (unf.isContinueUnguarded(rv))  // Without this, graph building becomes sensitive to the order of choice blocks (specifically, the relative position were the side effect (re-set entry to rec state) of an unguarded continue is performed)
		{
			Recursion<?> tmp = unf.getRecVar(rv);
			if (tmp == null)  // Hacky?  for recursive unfolding of cached blocks
			{
				return cont;
			}
			return unf.getRecVar(rv).clone();
		}
		else if (unf.shouldUnfoldForUnguardedRec(rv))
		{
			return unf.getRecVar(rv).clone();
		}
		return cont;
	}
}
