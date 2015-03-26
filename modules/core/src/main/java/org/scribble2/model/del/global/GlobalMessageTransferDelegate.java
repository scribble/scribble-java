package org.scribble2.model.del.global;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble2.model.MessageNode;
import org.scribble2.model.ModelNode;
import org.scribble2.model.del.SimpleInteractionNodeDelegate;
import org.scribble2.model.global.GlobalMessageTransfer;
import org.scribble2.model.local.LocalInteraction;
import org.scribble2.model.local.LocalInteractionSequence;
import org.scribble2.model.local.LocalNode;
import org.scribble2.model.local.LocalReceive;
import org.scribble2.model.local.LocalSend;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

public class GlobalMessageTransferDelegate extends SimpleInteractionNodeDelegate
{
	@Override
	public GlobalMessageTransfer leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		GlobalMessageTransfer msgtrans = (GlobalMessageTransfer) visited;
		
		Role src = msgtrans.src.toName();
		Message msg = msgtrans.msg.toMessage();
		WellFormedChoiceEnv env = checker.popEnv();
		for (Role dest : msgtrans.dests.stream().map((rn) -> rn.toName()).collect(Collectors.toList()))
		{
			//checker.setEnv(checker.getEnv().addMessageForSubprotocol(checker, src, dest, msg.toScopedMessage(checker.getScope())));
			env = env.addMessageForSubprotocol(checker, src, dest, msg.toScopedMessage(checker.getScope()));
			
			System.out.println("1: " + src + ", " + dest + ", " + msg);
		}
		checker.pushEnv(env);
		
		if (!checker.peekEnv().isEnabled(src))
		{
			throw new ScribbleException("Role not enabled: " + src);
		}
		return msgtrans;
	}

	@Override
	public GlobalMessageTransfer leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) //throws ScribbleException
	{
		GlobalMessageTransfer gmt = (GlobalMessageTransfer) visited;

		Role self = proj.peekSelf();
		Role srcrole = gmt.src.toName();
		List<Role> destroles = gmt.getDestinationRoles();
		LocalNode projection = null;
		if (srcrole.equals(self) || destroles.contains(self))
		{
			RoleNode src = new RoleNode(gmt.src.toName().toString());  // FIXME: project by visiting -- or maybe not: projection visiting only for GlobalNode
			//MessageNode msg = (MessageNode) ((ProjectionEnv) gmt.msg.del().getEnv()).getProjection();
			MessageNode msg = (MessageNode) gmt.msg;  // FIXME: need namespace prefix update?
			List<RoleNode> dests =
					destroles.stream().map((d) -> new RoleNode(d.toString())).collect(Collectors.toList());
			if (srcrole.equals(self))
			{
				projection = new LocalSend(src, msg, dests);
			}
			if (destroles.contains(self))
			{
				if (projection == null)
				{
					projection = new LocalReceive(src, msg, dests);
				}
				else
				{
					List<LocalInteraction> lis = Arrays.asList(new LocalInteraction[]{(LocalInteraction) projection, new LocalReceive(src, msg, dests)});
					projection = new LocalInteractionSequence(lis);
				}
			}
		}

		//this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		ProjectionEnv env = proj.popEnv();
		proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));

		return gmt;
	}
}
