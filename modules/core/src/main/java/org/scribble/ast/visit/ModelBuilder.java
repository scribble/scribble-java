package org.scribble.ast.visit;

import org.scribble.ast.ModelNode;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.visit.env.ModelEnv;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.util.ScribbleException;

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
