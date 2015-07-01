package org.scribble.del;

import java.util.List;

import org.scribble.ast.Choice;
import org.scribble.ast.Continue;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.ProtocolDefInliner;


public abstract class ProtocolBlockDel extends CompoundInteractionDel
{
	@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder) throws ScribbleException
	{
		pushVisitorEnv(parent, child, builder);
	}

	@Override
	public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		ProtocolBlock<?> gpb = (ProtocolBlock<?>) visited;
		// FIXME: hacky to do this here?
		if (parent instanceof Choice<?>)
		{
			InteractionSeq<?> gis = gpb.getInteractionSeq();
			List<? extends InteractionNode<?>> actions = gis.getActions();
			if (actions.size() > 0 && actions.get(0) instanceof Continue)
			{
				Continue<?> gc = (Continue<?>) actions.get(0);
				return unf.getRecVar(gc.recvar.toName());
			}
			// FIXME: if first action is recursion
			// FIXME: the action check should be role sensitive?
		}
		return visited;
	}
}
