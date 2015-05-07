package org.scribble2.model.del.local;

import java.util.LinkedList;
import java.util.List;

import org.scribble2.model.InteractionNode;
import org.scribble2.model.del.InteractionSeqDel;
import org.scribble2.model.local.LInteractionNode;
import org.scribble2.model.local.LInteractionSeq;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.model.visit.env.ReachabilityEnv;
import org.scribble2.sesstype.kind.Local;
import org.scribble2.util.ScribbleException;


// FIXME: should be a CompoundInteractionDelegate? -- no: compound interaction delegates for typing contexts (done for block only, not seqs)
public class LInteractionSeqDel extends InteractionSeqDel
{
	// Replaces visitChildrenInSubprotocols for LocalInteractionSequence 
	public LInteractionSeq visitForReachabilityChecking(ReachabilityChecker checker, LInteractionSeq child) throws ScribbleException
	{
		//List<T> actions = visitChildListWithClassCheck(this, this.actions, nv);  // OK to require all nodes to keep the same class? Maybe better to leave abstract and implement in the global/local subclasses
		List<LInteractionNode> visited = new LinkedList<>();
		//for (LocalInteractionNode li : child.actions)
		for (InteractionNode<Local> li : child.actions)
		{
			ReachabilityEnv re = checker.peekEnv();
			if (!re.isExitable())
			{
				throw new ScribbleException("Bad sequence to: " + li);
			}
			//visited.add((LocalInteractionNode) li.visitChildrenInSubprotocols(this));
			visited.add((LInteractionNode) li.accept(checker));
		}
		//return reconstruct(this.ct, actions);
		return child;
	}
}
