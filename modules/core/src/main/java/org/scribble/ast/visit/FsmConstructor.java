package org.scribble.ast.visit;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LDo;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.del.local.LDoDel;
import org.scribble.del.local.LInteractionSeqDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.GraphBuilder;

// FIXME: doesn't need to be an EnvVisitor?
//public class FsmConverter extends EnvVisitor<FsmBuildingEnv>
//public class FsmConverter extends ModelVisitor
public class FsmConstructor extends SubprotocolVisitor  // For "inlining" Do
{
	//public final FsmBuilder builder = new FsmBuilder();
	public final GraphBuilder builder = new GraphBuilder();

	//private final Map<RecVar, ProtocolState> labelled = new HashMap<>();
	
	public FsmConstructor(Job job)
	{
		super(job);
	}

	// Override visitForSubprotocols, not visit, or else enter/exit is lost
	@Override
	public ScribNode visitForSubprotocols(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof LInteractionSeq)
		{
			return visitOverrideForLInteractionSeq((LProtocolBlock) parent, (LInteractionSeq) child);
		}
		else if (child instanceof LDo)
		{
			return visitOverrideForLDo((LInteractionSeq) parent, (LDo) child);
		}
		else
		{
			return super.visitForSubprotocols(parent, child);
		}
	}

	protected LInteractionSeq visitOverrideForLInteractionSeq(LProtocolBlock parent, LInteractionSeq child)
	{
		return ((LInteractionSeqDel) child.del()).visitForFsmConversion(this, child);
	}

	protected LDo visitOverrideForLDo(LInteractionSeq parent, LDo child) throws ScribbleException
	{
		if (!isCycle())
		{
			return (LDo) super.visitForSubprotocols(parent, child);
		}
		return ((LDoDel) child.del()).visitForFsmConversion(this, child);  // If cycle, super routine does nothing anyway, so we can just replace with new stuff here
	}

	@Override
	protected final void subprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.subprotocolEnter(parent, child);
		child.del().enterFsmConstruction(parent, child, this);
	}
	
	@Override
	protected ScribNode subprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveFsmConstruction(parent, child, this, visited);
		return super.subprotocolLeave(parent, child, visited);
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
