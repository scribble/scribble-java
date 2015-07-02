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
			if (in instanceof Continue<?>)
			{
				Continue<?> gc = (Continue<?>) in;
				RecVar rv = gc.recvar.toName();
				if (unf.isTodo(rv))
				{
					//return unf.getRecVar(rv);
					//ProtocolBlock<?> pb = unf.getRecVar(rv);
					//visited.addAll((List<InteractionNode<K>>) ((ProtocolBlock<?>) pb.accept(unf)).seq.actions);  // FIXME: clone? or reconstruct inside visit is enough
					//visited.addAll((List<InteractionNode<K>>) pb.seq.actions);
					//visited.add(AstFactoryImpl.FACTORY.);
					Recursion<K> rec = (Recursion<K>) unf.getRecVar(rv);
					visited.add(rec.reconstruct(rec.recvar, rec.getBlock()));  // FIXME: clone the block? inlined wf-choice check relies on ast pointer equality
				}
				else
				{
					visited.add((InteractionNode<K>) in.accept(unf));
				}
			}
			else
			{
				visited.add((InteractionNode<K>) in.accept(unf));
			}
		}
		return child.reconstruct(visited);
	}
}
