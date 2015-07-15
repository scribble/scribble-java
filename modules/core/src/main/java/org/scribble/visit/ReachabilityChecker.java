package org.scribble.visit;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.del.local.LInteractionSeqDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.env.ReachabilityEnv;

public class ReachabilityChecker extends OffsetSubprotocolVisitor<ReachabilityEnv>
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
	protected ScribNode visitForOffsetSubprotocols(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof LInteractionSeq)
		{
			return visitOverrideForLInteractionSequence((LProtocolBlock) parent, (LInteractionSeq) child);
		}
		else
		{
			return super.visitForOffsetSubprotocols(parent, child);
		}
	}

	protected LInteractionSeq visitOverrideForLInteractionSequence(LProtocolBlock parent, LInteractionSeq child) throws ScribbleException
	{
		return ((LInteractionSeqDel) child.del()).visitForReachabilityChecking(this, (LInteractionSeq) child);
	}

	@Override
	protected void offsetSubprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.offsetSubprotocolEnter(parent, child);
		child.del().enterReachabilityCheck(parent, child, this);
	}
	
	@Override
	protected ScribNode offsetSubprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveReachabilityCheck(parent, child, this, visited);
		return super.offsetSubprotocolLeave(parent, child, visited);
	}
}
