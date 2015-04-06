package org.scribble2.model.del.global;

import org.scribble2.model.ModelFactory;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.global.GlobalContinue;
import org.scribble2.model.local.LocalContinue;
import org.scribble2.model.name.simple.RecursionVarNode;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.util.ScribbleException;

// FIXME: make base MessageTransferDelegate?
public class GlobalContinueDelegate extends GlobalSimpleInteractionNodeDelegate
{
	@Override
	public GlobalContinue leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException //throws ScribbleException
	{
		GlobalContinue gc = (GlobalContinue) visited;
		//RecursionVarNode recvar = new RecursionVarNode(gc.recvar.toName().toString());
		RecursionVarNode recvar = (RecursionVarNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.RECURSIONVAR, gc.recvar.toName().toString());
		LocalContinue projection = ModelFactoryImpl.FACTORY.LocalContinue(recvar);
		ProjectionEnv env = proj.popEnv();
		proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		return (GlobalContinue) super.leaveProjection(parent, child, proj, gc);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
