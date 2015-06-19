package org.scribble.del.local;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.scribble.ast.InteractionNode;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.del.InteractionSeqDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.ProtocolState;
import org.scribble.sesstype.kind.Local;
import org.scribble.visit.FsmConstructor;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.ReachabilityEnv;


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

	/*@Override
	public void enterFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv)
	{
		pushVisitorEnv(parent, child, conv);  // Like Projection, Fsm Conversion uses an Env for InteractionSequences
	}
	
	@Override
	public LInteractionSeq leaveFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv, ModelNode visited)
	{
		LInteractionSeq lis = (LInteractionSeq) visited;
		FsmBuilder b = new FsmBuilder();
		ScribbleFsm f;
		if (lis.isEmpty())
		{
			b.makeInit(Collections.emptySet());
			f = b.build();
		}
		else
		{
			f = ((FsmBuildingEnv) lis.actions.get(0).del().env()).getFsm();
			for (InteractionNode<Local> li : lis.actions.subList(1, lis.actions.size()))
			{
				f = f.stitch(((FsmBuildingEnv) li.del().env()).getFsm());
			}
		}
		FsmBuildingEnv env = conv.popEnv();
		conv.pushEnv(env.setFsm(f));
		return (LInteractionSeq) super.popAndSetVisitorEnv(parent, child, conv, lis);
	}*/

	public LInteractionSeq visitForFsmConversion(FsmConstructor conv, LInteractionSeq child) //throws ScribbleException
	{
		ProtocolState entry = conv.builder.getEntry();
		ProtocolState exit = conv.builder.getExit();
		// Backwards for "tau-less" continue
		for (int i = child.actions.size() - 1; i >= 0; i--)
		{
			try
			{
				if (i > 0)
				{
					ProtocolState tmp = conv.builder.newState(Collections.emptySet());
					conv.builder.setEntry(tmp);
					child.actions.get(i).accept(conv);
					conv.builder.setExit(conv.builder.getEntry());  // entry may not be tmp, entry/exit can be modified, e.g. continue
				}
				else
				{
					conv.builder.setEntry(entry);
					child.actions.get(i).accept(conv);
				}
			}
			catch (ScribbleException e)
			{
				throw new RuntimeException("Shouldn't get in here: " + e);
			}
		}
		conv.builder.setExit(exit);
		return child;	
	}
}
