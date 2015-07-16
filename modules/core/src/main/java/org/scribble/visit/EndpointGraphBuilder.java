package org.scribble.visit;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.del.local.LInteractionSeqDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.GraphBuilder;

// Changed from offsetsubprot visitor to inlined visitor to reduce state label accumulation to rec only -- then, wfc-checking for "unguarded" recursive-do-as-continue in choice blocks handled by unfolding inlineds
public class EndpointGraphBuilder extends NoEnvInlinedProtocolVisitor
{
	public final GraphBuilder builder = new GraphBuilder();
	
	public EndpointGraphBuilder(Job job)
	{
		super(job);
	}

	// Override visitInlinedProtocol, not visit, or else enter/exit is lost
	@Override
	public ScribNode visitInlinedProtocol(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof LInteractionSeq)
		{
			return visitOverrideForLInteractionSeq((LProtocolBlock) parent, (LInteractionSeq) child);
		}
		else
		{
			return super.visitInlinedProtocol(parent, child);
		}
	}

	protected LInteractionSeq visitOverrideForLInteractionSeq(LProtocolBlock parent, LInteractionSeq child)
	{
		return ((LInteractionSeqDel) child.del()).visitForFsmConversion(this, child);
	}

	@Override
	protected final void inlinedProtocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.inlinedProtocolEnter(parent, child);
		child.del().enterGraphBuilding(parent, child, this);
	}
	
	@Override
	protected ScribNode inlinedProtocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveGraphBuilding(parent, child, this, visited);
		return super.inlinedProtocolLeave(parent, child, visited);
	}
}
