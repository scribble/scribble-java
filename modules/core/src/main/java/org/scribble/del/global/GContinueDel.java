package org.scribble.del.global;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.local.LContinue;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.visit.Projector;
import org.scribble.visit.env.ProjectionEnv;

public class GContinueDel extends GSimpleInteractionNodeDel
{
	@Override
	public GContinue leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException //throws ScribbleException
	{
		GContinue gc = (GContinue) visited;
		RecVarNode recvar = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, gc.recvar.toName().toString());
		LContinue projection = AstFactoryImpl.FACTORY.LContinue(recvar);
		ProjectionEnv env = proj.popEnv();
		proj.pushEnv(env.setProjection(projection));
		return (GContinue) super.leaveProjection(parent, child, proj, gc);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
