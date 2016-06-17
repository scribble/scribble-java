package org.scribble.visit;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.model.global.GGraphBuilder;

// Builds the "synchronous" global model
// Duplicated from EndpointGraphBuilder
//public class GlobalModelBuilder extends OffsetSubprotocolVisitor<ModelEnv>
@Deprecated
public class GlobalModelBuilder extends NoEnvInlinedProtocolVisitor  // FIXME: should be unfolded?
{
	public final GGraphBuilder builder = new GGraphBuilder();

	public GlobalModelBuilder(Job job)
	{
		super(job);
	}

	/*// Override visitInlinedProtocol, not visit, or else enter/exit is lost
	@Override
	public ScribNode visitInlinedProtocol(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof GInteractionSeq)
		{
			return visitOverrideForGInteractionSeq((GProtocolBlock) parent, (GInteractionSeq) child);
		}
		else if (child instanceof GChoice)
		{
			return visitOverrideForGChoice((GInteractionSeq) parent, (GChoice) child);
		}
		else
		{
			return super.visitInlinedProtocol(parent, child);
		}
	}

	protected GInteractionSeq visitOverrideForGInteractionSeq(GProtocolBlock parent, GInteractionSeq child)
	{
		return ((GInteractionSeqDel) child.del()).visitForGlobalModelBuilding(this, child);
	}

	protected GChoice visitOverrideForGChoice(GInteractionSeq parent, GChoice child)
	{
		return ((GChoiceDel) child.del()).visitForGlobalModelBuilding(this, child);
	}*/

	@Override
	protected final void inlinedProtocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	//protected final void unfoldingEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.inlinedProtocolEnter(parent, child);
		////super.unfoldingEnter(parent, child);
		//child.del().enterModelBuilding(parent, child, this);
	}
	
	@Override
	protected ScribNode inlinedProtocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	//protected ScribNode unfoldingLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		//visited = visited.del().leaveModelBuilding(parent, child, this, visited);
		return super.inlinedProtocolLeave(parent, child, visited);
		////return super.unfoldingLeave(parent, child, visited);
	}


	/*@Override
	protected ModelEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new ModelEnv();
	}
	
	@Override
	protected void offsetSubprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.envEnter(parent, child);
		child.del().enterModelBuilding(parent, child, this);
	}
	
	@Override
	protected ScribNode offsetSubprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveModelBuilding(parent, child, this, visited);
		return super.envLeave(parent, child, visited);
	}*/
}
