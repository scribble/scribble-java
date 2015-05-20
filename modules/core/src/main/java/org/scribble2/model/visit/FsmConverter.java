package org.scribble2.model.visit;

import java.util.HashMap;
import java.util.Map;

import org.scribble2.fsm.FsmBuilder;
import org.scribble2.fsm.ProtocolState;
import org.scribble2.model.ModelNode;
import org.scribble2.model.ProtocolDecl;
import org.scribble2.model.visit.env.FsmBuildingEnv;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.name.RecVar;
import org.scribble2.util.ScribbleException;

public class FsmConverter extends EnvVisitor<FsmBuildingEnv>
{
	public final FsmBuilder builder = new FsmBuilder();

	private final Map<RecVar, ProtocolState> labelled = new HashMap<>();
	
	public FsmConverter(Job job)
	{
		super(job);
	}
	
	public void addLabelledState(ProtocolState s)
	{
		for (RecVar rv : s.getLabels())
		{
			this.labelled.put(rv, s);
		}
	}
	
	public ProtocolState getLabelledState(RecVar rv)
	{
		return this.labelled.get(rv);
	}

	@Override
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
	}
}
