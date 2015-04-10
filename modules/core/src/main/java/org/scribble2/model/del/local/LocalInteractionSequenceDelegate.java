package org.scribble2.model.del.local;

import java.util.LinkedList;
import java.util.List;

import org.scribble2.model.del.InteractionSequenceDelegate;
import org.scribble2.model.local.LocalInteractionNode;
import org.scribble2.model.local.LocalInteractionSequence;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.model.visit.env.ReachabilityEnv;
import org.scribble2.util.ScribbleException;


// FIXME: should be a CompoundInteractionDelegate? -- no: compound interaction delegates for typing contexts (done for block only, not seqs)
public class LocalInteractionSequenceDelegate extends InteractionSequenceDelegate
{
	// Replaces visitChildrenInSubprotocols for LocalInteractionSequence 
	public LocalInteractionSequence visitForReachabilityChecking(ReachabilityChecker checker, LocalInteractionSequence child) throws ScribbleException
	{
		//List<T> actions = visitChildListWithClassCheck(this, this.actions, nv);  // OK to require all nodes to keep the same class? Maybe better to leave abstract and implement in the global/local subclasses
		List<LocalInteractionNode> visited = new LinkedList<>();
		for (LocalInteractionNode li : child.actions)
		{
			ReachabilityEnv re = checker.peekEnv();
			if (!re.isExitable())
			{
				throw new ScribbleException("Bad sequence to: " + li);
			}
			//visited.add((LocalInteractionNode) li.visitChildrenInSubprotocols(this));
			visited.add((LocalInteractionNode) li.accept(checker));
		}
		//return reconstruct(this.ct, actions);
		return child;
	}
}
