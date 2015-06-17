package org.scribble.ast.visit;

import org.scribble.ast.ScribNode;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.visit.env.ModelEnv;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;

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
	protected void envEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.envEnter(parent, child);
		child.del().enterModelBuilding(parent, child, this);
	}
	
	@Override
	protected ScribNode envLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveModelBuilding(parent, child, this, visited);
		return super.envLeave(parent, child, visited);
	}
}
