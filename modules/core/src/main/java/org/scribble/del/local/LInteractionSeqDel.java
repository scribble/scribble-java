package org.scribble.del.local;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.del.InteractionSeqDel;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.ProtocolState;
import org.scribble.sesstype.kind.Local;
import org.scribble.visit.FsmGenerator;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.ReachabilityEnv;

public class LInteractionSeqDel extends InteractionSeqDel
{
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
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
		builder.pushEnv(builder.popEnv().setTranslation(inlined));
		return (LInteractionSeq) ScribDelBase.popAndSetVisitorEnv(this, builder, lis);
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

	public LInteractionSeq visitForFsmConversion(FsmGenerator conv, LInteractionSeq child)
	{
		ProtocolState entry = conv.builder.getEntry();
		ProtocolState exit = conv.builder.getExit();
		try
		{
			for (int i = child.getInteractions().size() - 1; i >= 0; i--)  // Backwards for "tau-less" continue
			{
				if (i > 0)
				{
					ProtocolState tmp = conv.builder.newState(Collections.emptySet());
					conv.builder.setEntry(tmp);
					child.getInteractions().get(i).accept(conv);
					conv.builder.setExit(conv.builder.getEntry());  // entry may not be tmp, entry/exit can be modified, e.g. continue
				}
				else
				{
					conv.builder.setEntry(entry);
					child.getInteractions().get(i).accept(conv);
				}
			}
		}
		catch (ScribbleException e)
		{
			throw new RuntimeException("Shouldn't get in here: " + e);
		}
		conv.builder.setExit(exit);
		return child;	
	}
}
