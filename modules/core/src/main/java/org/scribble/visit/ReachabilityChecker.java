package org.scribble.visit;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.del.local.LInteractionSeqDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.env.ReachabilityEnv;

public class ReachabilityChecker extends UnfoldingVisitor<ReachabilityEnv>
{
	public ReachabilityChecker(Job job)
	{
		super(job);
	}

	@Override
	protected ReachabilityEnv makeRootProtocolDeclEnv(ProtocolDecl<?> pd)
	{
		ReachabilityEnv env = new ReachabilityEnv();
		return env;
	}

	// Following Projector visit pattern -- for overriding base enter/visit/leave pattern
	@Override
	protected ScribNode visitForUnfolding(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof LInteractionSeq)
		{
			return visitOverrideForLInteractionSequence((LProtocolBlock) parent, (LInteractionSeq) child);
		}
		else
		{
			return super.visitForUnfolding(parent, child);
		}
	}

	protected LInteractionSeq visitOverrideForLInteractionSequence(LProtocolBlock parent, LInteractionSeq child) throws ScribbleException
	{
		return ((LInteractionSeqDel) child.del()).visitForReachabilityChecking(this, (LInteractionSeq) child);
	}

	@Override
	protected void unfoldingEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.unfoldingEnter(parent, child);
		child.del().enterReachabilityCheck(parent, child, this);
	}
	
	@Override
	protected ScribNode unfoldingLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveReachabilityCheck(parent, child, this, visited);
		return super.unfoldingLeave(parent, child, visited);
	}
}
