package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ProtocolDefInliner;


public abstract class InteractionSeqDel extends ScribDelBase
{
	@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder) throws ScribbleException
	{
		pushVisitorEnv(parent, child, builder);
	}

	/*@Override
	public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		return visitForUnfolding(unf, (InteractionSeq<?>) visited);
	}*/

	/*private <K extends ProtocolKind> ScribNode visitForUnfolding(InlinedProtocolUnfolder unf, InteractionSeq<K> seq) throws ScribbleException
	{
		List<InteractionNode<K>> ins = new LinkedList<>();
		for (InteractionNode<K> in : seq.actions)
		{
			if (in instanceof Continue)
			{
				Continue<K> cont = (Continue<K>) in;
				RecVar rv = cont.recvar.toName();
				if (unf.isTodo(rv))
				{
					Recursion<K> rec = castRecursion(cont, unf.getRecVar(rv));  // Could parameterise recvars to be global/local
					ins.add(rec.clone());  // original rec is cloned
				}
				else
				{
					ins.add(ScribUtil.checkNodeClass(in, in.accept(unf)));
				}
			}
			else
			{
				ins.add(ScribUtil.checkNodeClass(in, in.accept(unf)));
			}
		}
		return seq.reconstruct(ins);  // reconstruct makes sense here, actually reconstructing this seq (keep the same del etc)
	}
	
	private static <K extends ProtocolKind> Recursion<K> castRecursion(Continue<K> cont, Recursion<?> rec)
	{
		if ((cont.isGlobal() && !rec.isGlobal()) || (cont.isLocal() && !rec.isLocal()))
		{
			throw new RuntimeException("Shouldn't get in here: " + cont);
		}
		@SuppressWarnings("unchecked")
		Recursion<K> tmp = (Recursion<K>) rec;
		return tmp;
	}*/
}
