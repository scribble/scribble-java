package org.scribble.del.global;

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GConnect;
import org.scribble.ast.local.LNode;
import org.scribble.del.ConnectionActionDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.GlobalModelBuilder;
import org.scribble.visit.NameDisambiguator;
import org.scribble.visit.Projector;
import org.scribble.visit.WFChoiceChecker;
import org.scribble.visit.env.WFChoiceEnv;

public class GConnectDel extends ConnectionActionDel implements GSimpleInteractionNodeDel
{
	public GConnectDel()
	{
		
	}

	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		GConnect gc = (GConnect) visited;
		Role src = gc.src.toName();
		Role dest = gc.dest.toName();
		return gc;
	}

	@Override
	public GConnect leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		GConnect gc = (GConnect) visited;
		
		Role src = gc.src.toName();
		if (!checker.peekEnv().isEnabled(src))
		{
			throw new ScribbleException("Role not enabled: " + src);
		}
		Message msg = gc.msg.toMessage();
		WFChoiceEnv env = checker.popEnv();
		//for (Role dest : gc.getDestinationRoles())
		Role dest = gc.dest.toName();
		{
			if (src.equals(dest))
			{
				throw new ScribbleException("(TODO) Self connections not supported: " + gc);
			}
			if (env.isConnected(src, dest))
			{
				throw new ScribbleException("Roles already connected: " + src + ", " + dest);
			}

			env = env.connect(src, dest).addMessage(src, dest, msg);
			/*env = env
					.connect(src, dest)
					.addMessage(src, dest, new MessageSig(Op.EMPTY_OPERATOR, Payload.EMPTY_PAYLOAD));*/
		}
		checker.pushEnv(env);
		return gc;
	}

	@Override
	public ScribNode leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException //throws ScribbleException
	{
		GConnect gc = (GConnect) visited;
		Role self = proj.peekSelf();
		LNode projection = gc.project(self);
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GConnect) GSimpleInteractionNodeDel.super.leaveProjection(parent, child, proj, gc);
	}
	
	@Override
	public GConnect leaveModelBuilding(ScribNode parent, ScribNode child, GlobalModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		//return (GConnect) super.leaveModelBuilding(parent, child, builder, ls);
		throw new RuntimeException("Shouldn't get in here: " + visited);
	}
}
