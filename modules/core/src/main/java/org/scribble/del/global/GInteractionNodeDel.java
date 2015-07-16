package org.scribble.del.global;

import org.scribble.ast.ScribNode;
import org.scribble.del.InteractionNodeDel;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.visit.GlobalModelBuilder;
import org.scribble.visit.Projector;

public interface GInteractionNodeDel extends InteractionNodeDel
{
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

	@Override
	default void enterModelBuilding(ScribNode parent, ScribNode child, GlobalModelBuilder builder) throws ScribbleException
	{
		ScribDelBase.pushVisitorEnv(this, builder);
	}

	@Override
	default ScribNode leaveModelBuilding(ScribNode parent, ScribNode child, GlobalModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		return ScribDelBase.popAndSetVisitorEnv(this, builder, visited);
	}
}
