package org.scribble2.model.del.global;

import org.scribble2.model.ModelFactory;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.global.GContinue;
import org.scribble2.model.local.LContinue;
import org.scribble2.model.name.simple.RecursionVarNode;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.util.ScribbleException;

// FIXME: make base MessageTransferDelegate?
public class GContinueDel extends GSimpleInteractionNodeDel
{
	@Override
	public GContinue leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException //throws ScribbleException
	{
		GContinue gc = (GContinue) visited;
		//RecursionVarNode recvar = new RecursionVarNode(gc.recvar.toName().toString());
		RecursionVarNode recvar = (RecursionVarNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.RECURSIONVAR, gc.recvar.toName().toString());
		LContinue projection = ModelFactoryImpl.FACTORY.LocalContinue(recvar);
		ProjectionEnv env = proj.popEnv();
		//proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		proj.pushEnv(new ProjectionEnv(projection));
		return (GContinue) super.leaveProjection(parent, child, proj, gc);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
