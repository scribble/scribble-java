package org.scribble.del.global;

import java.util.List;

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.local.LNode;
import org.scribble.del.MessageTransferDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.Projector;
import org.scribble.visit.wf.NameDisambiguator;
import org.scribble.visit.wf.WFChoiceChecker;
import org.scribble.visit.wf.env.WFChoiceEnv;

public class GMessageTransferDel extends MessageTransferDel implements GSimpleInteractionNodeDel
{
	public GMessageTransferDel()
	{
		
	}

	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		GMessageTransfer gmt = (GMessageTransfer) visited;
		Role src = gmt.src.toName();
		List<Role> dests = gmt.getDestinationRoles();
		if (dests.contains(src))
		{
			throw new ScribbleException(gmt.getSource(), "[TODO] Self connections not supported: " + gmt);  // Would currently be subsumed by unconnected check
		}
		return gmt;
	}

	@Override
	public GMessageTransfer leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		GMessageTransfer gmt = (GMessageTransfer) visited;
		
		Role src = gmt.src.toName();
		if (!checker.peekEnv().isEnabled(src))
		{
			throw new ScribbleException(gmt.src.getSource(), "Role not enabled: " + src);
		}
		Message msg = gmt.msg.toMessage();
		WFChoiceEnv env = checker.popEnv();
		for (Role dest : gmt.getDestinationRoles())
		{
			// FIXME: better to check as global model error (role stuck on uncomnected send)
			if (!env.isConnected(src, dest))
			{
				throw new ScribbleException(gmt.getSource(), "Roles not (necessarily) connected: " + src + ", " + dest);
			}

			env = env.addMessage(src, dest, msg);
			
			//System.out.println("a: " + src + ", " + dest + ", " + msg);
		}
		checker.pushEnv(env);
		return gmt;
	}

	@Override
	//public GMessageTransfer leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException //throws ScribbleException
	public ScribNode leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException //throws ScribbleException
	{
		GMessageTransfer gmt = (GMessageTransfer) visited;
		Role self = proj.peekSelf();
		LNode projection = gmt.project(self);
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GMessageTransfer) GSimpleInteractionNodeDel.super.leaveProjection(parent, child, proj, gmt);
	}
}
