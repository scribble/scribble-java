package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ProtocolDefInliner;


public abstract class SimpleInteractionNodeDel extends ScribDelBase implements InteractionNodeDel
{
	@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder) throws ScribbleException
	{
		//pushVisitorEnv(parent, child, builder);
		pushVisitorEnv(this, builder);
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		//return popAndSetVisitorEnv(parent, child, builder, visited);
		return popAndSetVisitorEnv(this, builder, visited);
	}
}
