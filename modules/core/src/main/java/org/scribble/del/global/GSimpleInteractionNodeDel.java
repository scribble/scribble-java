package org.scribble.del.global;

import org.scribble.ast.ScribNode;
import org.scribble.del.SimpleInteractionNodeDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ModelBuilder;
import org.scribble.visit.Projector;


public abstract class GSimpleInteractionNodeDel extends SimpleInteractionNodeDel
{
	//public CompoundInteractionNodeDelegate(Env env)
	public GSimpleInteractionNodeDel()
	{
		//super(env);
	}

	@Override
	//public Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	public void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	{
		/*ProjectionEnv env = proj.peekEnv().push();  // By default: copy
		proj.pushEnv(env);
		return proj;*/
		//return (Projector) pushEnv(parent, child, proj);
		pushVisitorEnv(parent, child, proj);
	}

	@Override
	public ScribNode leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		/*ProjectionEnv env = proj.popEnv();
		//env = checker.popEnv().merge(env);  // No merge here: merging of child blocks is handled "manually" by the compound interaction nodes
		//checker.pushEnv(env);
		setEnv(env);
		return visited;*/
		return popAndSetVisitorEnv(parent, child, proj, visited);
	}

	@Override
	public void enterModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder) throws ScribbleException
	{
		pushVisitorEnv(parent, child, builder);
	}

	@Override
	public ScribNode leaveModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		return popAndSetVisitorEnv(parent, child, builder, visited);
	}
}