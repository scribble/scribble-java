package org.scribble2.model.visit;

import org.scribble2.fsm.GraphBuilder;
import org.scribble2.model.ModelNode;
import org.scribble2.model.del.local.LInteractionSeqDel;
import org.scribble2.model.local.LInteractionSeq;
import org.scribble2.model.local.LProtocolBlock;
import org.scribble2.util.ScribbleException;

// FIXME: doesn't need to be an EnvVisitor?
//public class FsmConverter extends EnvVisitor<FsmBuildingEnv>
public class FsmConverter extends ModelVisitor
{
	//public final FsmBuilder builder = new FsmBuilder();
	public final GraphBuilder builder = new GraphBuilder();

	//private final Map<RecVar, ProtocolState> labelled = new HashMap<>();
	
	public FsmConverter(Job job)
	{
		super(job);
	}

	@Override
	public ModelNode visit(ModelNode parent, ModelNode child) throws ScribbleException
	{
		if (child instanceof LInteractionSeq)
		{
			return visitOverrideForLInteractionSeq((LProtocolBlock) parent, (LInteractionSeq) child);
		}
		else
		{
			return super.visit(parent, child);
		}
	}

	protected LInteractionSeq visitOverrideForLInteractionSeq(LProtocolBlock parent, LInteractionSeq child)
	{
		return ((LInteractionSeqDel) child.del()).visitForFsmConversion(this, (LInteractionSeq) child);
	}

	@Override
	protected final void enter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		super.enter(parent, child);
		child.del().enterFsmConversion(parent, child, this);
	}
	
	@Override
	protected ModelNode leave(ModelNode parent, ModelNode child, ModelNode visited) throws ScribbleException
	{
		visited = visited.del().leaveFsmConversion(parent, child, this, visited);
		return super.leave(parent, child, visited);
	}
	
	/*public void addLabelledState(ProtocolState s)
	{
		for (RecVar rv : s.getLabels())
		{
			this.labelled.put(rv, s);
		}
	}
	
	public ProtocolState getLabelledState(RecVar rv)
	{
		return this.labelled.get(rv);
	}*/

	/*@Override
	protected FsmBuildingEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new FsmBuildingEnv();
	}

	@Override
	protected ModelNode visitForSubprotocols(ModelNode parent, ModelNode child) throws ScribbleException
	{
		return child.visitChildren(this);
	}
	
	@Override
	protected final void envEnter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		super.envEnter(parent, child);
		child.del().enterFsmConversion(parent, child, this);
	}
	
	@Override
	protected ModelNode envLeave(ModelNode parent, ModelNode child, ModelNode visited) throws ScribbleException
	{
		visited = visited.del().leaveFsmConversion(parent, child, this, visited);
		return super.envLeave(parent, child, visited);
	}*/
}
