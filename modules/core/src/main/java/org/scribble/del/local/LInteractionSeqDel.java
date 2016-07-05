package org.scribble.del.local;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.del.InteractionSeqDel;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.EndpointState;
import org.scribble.sesstype.kind.Local;
import org.scribble.visit.EndpointGraphBuilder;
import org.scribble.visit.ProjectedChoiceDoPruner;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.ReachabilityEnv;

public class LInteractionSeqDel extends InteractionSeqDel
{
	@Override
	public ScribNode leaveProjectedChoiceDoPruning(ScribNode parent, ScribNode child, ProjectedChoiceDoPruner pruner, ScribNode visited) throws ScribbleException
	{
		LInteractionSeq lc = (LInteractionSeq) visited;
		List<LInteractionNode> actions = lc.getInteractions().stream().filter((li) -> li != null).collect(Collectors.toList());
		return lc.reconstruct(actions);
	}

	// enter in super
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		LInteractionSeq lis = (LInteractionSeq) visited;
		List<LInteractionNode> lins = new LinkedList<LInteractionNode>();
		for (LInteractionNode li : lis.getInteractions())
		{
			ScribNode inlined = ((InlineProtocolEnv) li.del().env()).getTranslation();
			if (inlined instanceof LInteractionSeq)
			{
				lins.addAll(((LInteractionSeq) inlined).getInteractions());
			}
			else
			{
				lins.add((LInteractionNode) inlined);
			}
		}
		LInteractionSeq inlined = AstFactoryImpl.FACTORY.LInteractionSeq(lins);
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (LInteractionSeq) ScribDelBase.popAndSetVisitorEnv(this, inl, lis);
	}

	// Replaces visitChildrenInSubprotocols for LocalInteractionSequence 
	public LInteractionSeq visitForReachabilityChecking(ReachabilityChecker checker, LInteractionSeq child) throws ScribbleException
	{
		List<LInteractionNode> visited = new LinkedList<>();
		for (InteractionNode<Local> li : child.getInteractions())
		{
			ReachabilityEnv re = checker.peekEnv();
			if (!re.isSequenceable())
			{
				throw new ScribbleException("Bad sequence to: " + li);
			}
			visited.add((LInteractionNode) li.accept(checker));
		}
		return child;
	}

	public LInteractionSeq visitForFsmConversion(EndpointGraphBuilder conv, LInteractionSeq child) throws ScribbleException
	{
		EndpointState entry = conv.builder.getEntry();
		EndpointState exit = conv.builder.getExit();
		//try
		{
			/*for (int i = child.getInteractions().size() - 1; i >= 0; i--)  // Backwards for "tau-less" continue
			{
				if (i > 0)
				{
					EndpointState tmp = conv.builder.newState(Collections.emptySet());
					conv.builder.setEntry(tmp);
					child.getInteractions().get(i).accept(conv);
					conv.builder.setExit(conv.builder.getEntry());  // entry may not be tmp, entry/exit can be modified, e.g. continue
				}
				else
				{
					conv.builder.setEntry(entry);
					child.getInteractions().get(i).accept(conv);
				}
			}*/
			for (int i = 0; i < child.getInteractions().size(); i++)
			{
				if (i == child.getInteractions().size() - 1)
				{
					conv.builder.setExit(exit);
					child.getInteractions().get(i).accept(conv);
				}
				else
				{
					EndpointState tmp = conv.builder.newState(Collections.emptySet());
					conv.builder.setExit(tmp);
					child.getInteractions().get(i).accept(conv);
					conv.builder.setEntry(conv.builder.getExit());  // exit may not be tmp, entry/exit can be modified, e.g. continue
				}
			}
		}
		/*catch (ScribbleException e)  // Hack: EFSM building now done before reachability check, removeEdge can fail
		{
			throw new RuntimeException("Shouldn't get in here: " + e);
		}*/
		//conv.builder.setExit(exit);
		conv.builder.setEntry(entry);
		return child;	
	}
}
