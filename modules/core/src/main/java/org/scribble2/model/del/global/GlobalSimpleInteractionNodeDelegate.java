package org.scribble2.model.del.global;

import org.scribble2.model.ModelNode;
import org.scribble2.model.del.SimpleInteractionNodeDelegate;
import org.scribble2.model.visit.Projector;
import org.scribble2.util.ScribbleException;


public abstract class GlobalSimpleInteractionNodeDelegate extends SimpleInteractionNodeDelegate
{
	//public CompoundInteractionNodeDelegate(Env env)
	public GlobalSimpleInteractionNodeDelegate()
	{
		//super(env);
	}

	@Override
	public Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	{
		/*ProjectionEnv env = proj.peekEnv().push();  // By default: copy
		proj.pushEnv(env);
		return proj;*/
		return (Projector) pushEnv(parent, child, proj);
	}

	@Override
	public ModelNode leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException
	{
		/*ProjectionEnv env = proj.popEnv();
		//env = checker.popEnv().merge(env);  // No merge here: merging of child blocks is handled "manually" by the compound interaction nodes
		//checker.pushEnv(env);
		setEnv(env);
		return visited;*/
		return popAndSetEnv(parent, child, proj, visited);
	}
}
