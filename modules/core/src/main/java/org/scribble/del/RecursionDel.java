package org.scribble.del;

import org.scribble.ast.Recursion;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.env.UnfoldingEnv;

public abstract class RecursionDel extends CompoundInteractionNodeDel
{
	public RecursionDel()
	{

	}

	@Override
	public void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException
	{
		super.enterInlinedProtocolUnfolding(parent, child, unf);
		Recursion<?> lr = (Recursion<?>) child;
		RecVar recvar = lr.recvar.toName();
		//LInteractionSeq gis = gr.getBlock().getInteractionSeq();
		//ProtocolBlock<?> pb = lr.getBlock();
		//unf.setRecVar(recvar, lpb);
		unf.setRecVar(recvar, lr);  // Cloned on use (on continue)
	}

	@Override
	public Recursion<?> leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		Recursion<?> rec = (Recursion<?>) visited;
		RecVar recvar = rec.recvar.toName();
		unf.removeRecVar(recvar);
		UnfoldingEnv merged = unf.popEnv().mergeContext((UnfoldingEnv) rec.block.del().env());
		unf.pushEnv(merged);
		return (Recursion<?>) super.leaveInlinedProtocolUnfolding(parent, child, unf, rec);
	}
}
