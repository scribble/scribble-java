package org.scribble.del.global;

import org.scribble.ast.ModelFactoryImpl;
import org.scribble.ast.ModelNode;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.local.LContinue;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.visit.Projector;
import org.scribble.ast.visit.env.ProjectionEnv;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.util.ScribbleException;

// FIXME: make base MessageTransferDelegate?
public class GContinueDel extends GSimpleInteractionNodeDel
{
	@Override
	public GContinue leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException //throws ScribbleException
	{
		GContinue gc = (GContinue) visited;
		//RecursionVarNode recvar = new RecursionVarNode(gc.recvar.toName().toString());
		//RecursionVarNode recvar = (RecursionVarNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.RECURSIONVAR, gc.recvar.toName().toString());
		RecVarNode recvar = (RecVarNode) ModelFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, gc.recvar.toName().toString());
		LContinue projection = ModelFactoryImpl.FACTORY.LContinue(recvar);
		ProjectionEnv env = proj.popEnv();
		//proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		proj.pushEnv(new ProjectionEnv(projection));
		return (GContinue) super.leaveProjection(parent, child, proj, gc);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
