package org.scribble.del.global;

import org.scribble.ast.ScribNode;
import org.scribble.del.InteractionNodeDel;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.visit.context.Projector;

public interface GInteractionNodeDel extends InteractionNodeDel
{
	//public abstract LNode project(GNode n, Role self);  // Generalised return, e.g. returning a seq
	
	@Override
	default void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	{
		ScribDelBase.pushVisitorEnv(this, proj);
	}

	@Override
	default ScribNode leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		return ScribDelBase.popAndSetVisitorEnv(this, proj, visited);
	}
}
