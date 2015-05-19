package org.scribble2.model.visit;

import org.scribble2.model.ModelNode;
import org.scribble2.model.ProtocolDecl;
import org.scribble2.model.visit.env.FsmBuildingEnv;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.util.ScribbleException;

public class FsmConverter extends EnvVisitor<FsmBuildingEnv>
{
	public FsmConverter(Job job)
	{
		super(job);
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
