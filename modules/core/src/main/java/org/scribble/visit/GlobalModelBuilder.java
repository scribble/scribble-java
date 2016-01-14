package org.scribble.visit;

import org.scribble.ast.ScribNode;
import org.scribble.ast.ProtocolDecl;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.env.ModelEnv;

// This pass is incomplete -- do not use yet
public class GlobalModelBuilder extends OffsetSubprotocolVisitor<ModelEnv>
{
	public GlobalModelBuilder(Job job)
	{
		super(job);
	}

	@Override
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
	}
}
