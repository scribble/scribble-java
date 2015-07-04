package org.scribble.del;

import java.util.LinkedList;
import java.util.List;

import org.scribble.ast.Continue;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.InteractionSeq;
import org.scribble.ast.Recursion;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.RecVar;
import org.scribble.util.ScribUtil;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.ProtocolDefInliner;


public abstract class InteractionSeqDel extends ScribDelBase
{
	@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder) throws ScribbleException
	{
		pushVisitorEnv(parent, child, builder);
	}

	//@Override
	//public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	public <K extends ProtocolKind> ScribNode visitForUnfolding(InlinedProtocolUnfolder unf, InteractionSeq<K> child) throws ScribbleException
	{
		List<InteractionNode<K>> visited = new LinkedList<>();
		for (InteractionNode<K> in : child.actions)
		{
			if (in instanceof Continue)
			{
				Continue<K> cont = (Continue<K>) in;
				RecVar rv = cont.recvar.toName();
				if (unf.isTodo(rv))
				{
					Recursion<K> rec = (Recursion<K>) unf.getRecVar(rv);  // Could parameterise recvars to be global/local
					//visited.add(rec.reconstruct(rec.recvar, rec.getBlock()));  // FIXME: clone the block? inlined wf-choice check relies on ast pointer equality
					visited.add(rec.clone());
				}
				else
				{
					visited.add(ScribUtil.checkNodeClass(in, in.accept(unf)));
				}
			}
			else
			{
				visited.add(ScribUtil.checkNodeClass(in, in.accept(unf)));
			}
		}
		return child.reconstruct(visited);
	}
}
