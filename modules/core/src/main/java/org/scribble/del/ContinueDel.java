package org.scribble.del;

import org.scribble.ast.Continue;
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
	public Continue<?> leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		Continue<?> lc = (Continue<?>) visited;
		Continue<?> inlined = (Continue<?>) lc.clone();
		builder.pushEnv(builder.popEnv().setTranslation(inlined));
		return (Continue<?>) super.leaveProtocolInlining(parent, child, builder, lc);
	}

	@Override
	public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		Continue<?> cont = (Continue<?>) visited;
		RecVar rv = cont.recvar.toName();
		if (unf.isTodo(rv))
		{
			return unf.getRecVar(rv).clone();
		}
		return cont;
	}
}
