package org.scribble2.model.visit;

import org.scribble2.model.ModelNode;
import org.scribble2.model.ProtocolDecl;
import org.scribble2.model.visit.env.ModelEnv;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.util.ScribbleException;

public class ModelBuilder extends EnvVisitor<ModelEnv>
{
	public ModelBuilder(Job job)
	{
		super(job);
	}

	@Override
	protected ModelEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new ModelEnv();
	}
	
	@Override
	protected void envEnter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		super.envEnter(parent, child);
		child.del().enterModelBuilding(parent, child, this);
	}
	
	@Override
	protected ModelNode envLeave(ModelNode parent, ModelNode child, ModelNode visited) throws ScribbleException
	{
		visited = visited.del().leaveModelBuilding(parent, child, this, visited);
		return super.envLeave(parent, child, visited);
	}
}
