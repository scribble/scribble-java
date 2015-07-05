package org.scribble.del.global;

import org.scribble.ast.ScribNode;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ModelBuilder;
import org.scribble.visit.Projector;

//public abstract class GCompoundInteractionNodeDel extends CompoundInteractionNodeDel
public interface GCompoundInteractionNodeDel extends GInteractionNodeDel
{
	/*public GCompoundInteractionNodeDel()
	{

	}*/

	//@Override
	//public void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	default void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	{
		//pushVisitorEnv(parent, child, proj);  // Not necessary to set projection env on enter, could be done on leaving
		ScribDelBase.pushVisitorEnv(this, proj);  // Not necessary to set projection env on enter, could be done on leaving
	}

	//@Override
	//public ScribNode leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	default ScribNode leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		//return popAndSetVisitorEnv(parent, child, proj, visited);
		return ScribDelBase.popAndSetVisitorEnv(this, proj, visited);
	}

	//@Override
	//public void enterModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder) throws ScribbleException
	default void enterModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder) throws ScribbleException
	{
		//pushVisitorEnv(parent, child, builder);
		ScribDelBase.pushVisitorEnv(this, builder);
	}

	//@Override
	//public ScribNode leaveModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder, ScribNode visited) throws ScribbleException
	default ScribNode leaveModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		//return popAndSetVisitorEnv(parent, child, builder, visited);
		//return popAndSetVisitorEnv(this, builder, visited);
		return ScribDelBase.popAndSetVisitorEnv(this, builder, visited);
	}
}
