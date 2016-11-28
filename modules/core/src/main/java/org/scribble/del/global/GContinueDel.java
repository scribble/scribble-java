package org.scribble.del.global;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GNode;
import org.scribble.ast.local.LContinue;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ContinueDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.Projector;
import org.scribble.visit.env.InlineProtocolEnv;

public class GContinueDel extends ContinueDel implements GSimpleInteractionNodeDel
{
	@Override
	public GContinue leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		GContinue gc = (GContinue) visited;
		RecVarNode recvar = (RecVarNode) ((InlineProtocolEnv) gc.recvar.del().env()).getTranslation();	
		GContinue inlined = AstFactoryImpl.FACTORY.GContinue(recvar);
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (GContinue) super.leaveProtocolInlining(parent, child, inl, gc);
	}

	public LContinue project(GNode n, Role self)
	{
		GContinue gc = (GContinue) n;
		LContinue projection = gc.project(self);
		return projection;
	}

	@Override
	public GContinue leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		GContinue gc = (GContinue) visited;
		LContinue projection = project(gc, proj.peekSelf());
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GContinue) GSimpleInteractionNodeDel.super.leaveProjection(parent, child, proj, gc);
	}
}
