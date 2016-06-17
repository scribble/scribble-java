package org.scribble.visit;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.del.local.LChoiceDel;
import org.scribble.del.local.LInteractionSeqDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.LGraphBuilder;

// Changed from offsetsubprot visitor to inlined visitor to reduce state label accumulation to rec only -- then, wfc-checking for "unguarded" recursive-do-as-continue in choice blocks handled by unfolding inlineds
// Inlined visitor, not unfolding -- but the inlined is already statically unfolded; this just means we don't do a "dynamic" unfolding as part of the AST visit
public class EndpointGraphBuilder extends NoEnvInlinedProtocolVisitor
//public class EndpointGraphBuilder extends NoEnvUnfoldingVisitor  // Doesn't work
{
	public final LGraphBuilder builder = new LGraphBuilder();
	
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
		else if (child instanceof LChoice)
		{
			return visitOverrideForLChoice((LInteractionSeq) parent, (LChoice) child);
		}
		else
		{
			return super.visitInlinedProtocol(parent, child);
		}
	}

	protected LInteractionSeq visitOverrideForLInteractionSeq(LProtocolBlock parent, LInteractionSeq child) throws ScribbleException
	{
		return ((LInteractionSeqDel) child.del()).visitForFsmConversion(this, child);
	}

	protected LChoice visitOverrideForLChoice(LInteractionSeq parent, LChoice child)
	{
		return ((LChoiceDel) child.del()).visitForFsmConversion(this, child);
	}

	@Override
	protected final void inlinedProtocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	//protected final void unfoldingEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.inlinedProtocolEnter(parent, child);
		//super.unfoldingEnter(parent, child);
		child.del().enterEndpointGraphBuilding(parent, child, this);
	}
	
	@Override
	protected ScribNode inlinedProtocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	//protected ScribNode unfoldingLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveEndpointGraphBuilding(parent, child, this, visited);
		return super.inlinedProtocolLeave(parent, child, visited);
		//return super.unfoldingLeave(parent, child, visited);
	}
}
