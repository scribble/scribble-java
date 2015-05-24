package org.scribble2.model.del.global;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble2.model.MessageNode;
import org.scribble2.model.MessageSigNode;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.global.GMessageTransfer;
import org.scribble2.model.local.LInteractionNode;
import org.scribble2.model.local.LReceive;
import org.scribble2.model.local.LocalNode;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.model.visit.OpCollector;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.kind.RoleKind;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

// FIXME: make base MessageTransferDelegate?
public class GMessageTransferDel extends GSimpleInteractionNodeDel
{
	public GMessageTransferDel()
	{
		
	}

	@Override
	public GMessageTransfer leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		GMessageTransfer msgtrans = (GMessageTransfer) visited;
		
		Role src = msgtrans.src.toName();
		//Message msg = msgtrans.msg.toMessage(checker.getScope());
		Message msg = msgtrans.msg.toMessage();
		WellFormedChoiceEnv env = checker.popEnv();
		for (Role dest : msgtrans.dests.stream().map((rn) -> rn.toName()).collect(Collectors.toList()))
		{
			//checker.setEnv(checker.getEnv().addMessageForSubprotocol(checker, src, dest, msg.toScopedMessage(checker.getScope())));
			//env = env.addMessageForSubprotocol(checker, src, dest, msg.toScopedMessage(checker.getScope()));
			env = env.addMessageForSubprotocol(checker, src, dest, msg);
			
			//System.out.println("1: " + src + ", " + dest + ", " + msg);
		}
		checker.pushEnv(env);
		
		if (!checker.peekEnv().isEnabled(src))
		{
			throw new ScribbleException("Role not enabled: " + src);
		}
		return msgtrans;
	}

	@Override
	public GMessageTransfer leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException //throws ScribbleException
	{
		GMessageTransfer gmt = (GMessageTransfer) visited;

		Role self = proj.peekSelf();
		Role srcrole = gmt.src.toName();
		List<Role> destroles = gmt.getDestinationRoles();
		LocalNode projection = null;
		if (srcrole.equals(self) || destroles.contains(self))
		{
			//RoleNode src = new RoleNode(gmt.src.toName().toString());  // FIXME: project by visiting -- or maybe not: projection visiting only for GlobalNode
			//RoleNode src = (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.ROLE, gmt.src.toName().toString());
			RoleNode src = (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, gmt.src.toName().toString());
			//MessageNode msg = (MessageNode) ((ProjectionEnv) gmt.msg.del().getEnv()).getProjection();
			MessageNode msg = (MessageNode) gmt.msg;  // FIXME: need namespace prefix update?
			List<RoleNode> dests =
					//destroles.stream().map((d) -> new RoleNode(d.toString())).collect(Collectors.toList());
					//destroles.stream().map((d) -> (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.ROLE, d.toString())).collect(Collectors.toList());
					destroles.stream().map((d) -> (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, d.toString())).collect(Collectors.toList());
			if (srcrole.equals(self))
			{
				projection = ModelFactoryImpl.FACTORY.LSend(src, msg, dests);
			}
			if (destroles.contains(self))
			{
				if (projection == null)
				{
					projection = ModelFactoryImpl.FACTORY.LReceive(src, msg, dests);
				}
				else
				{
					LReceive lr = ModelFactoryImpl.FACTORY.LReceive(src, msg, dests);
					List<LInteractionNode> lis = Arrays.asList(new LInteractionNode[]{(LInteractionNode) projection, lr});
					projection = ModelFactoryImpl.FACTORY.LInteractionSequence(lis);
				}
			}
		}

		//this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		ProjectionEnv env = proj.popEnv();
		//proj.pushEnv(new ProjectionEnv(env.getJobContext(), env.getModuleDelegate(), projection));
		proj.pushEnv(new ProjectionEnv(projection));
		//return gmt;
		return (GMessageTransfer) super.leaveProjection(parent, child, proj, gmt);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}

	@Override
	public ModelNode leaveOpCollection(ModelNode parent, ModelNode child, OpCollector coll, ModelNode visited)
	{
		GMessageTransfer gmt = (GMessageTransfer) visited;
		if (!gmt.msg.isMessageSigNode())
		{
			throw new RuntimeException("Op collection should be run on ground types: " + gmt.msg);
		}
		coll.addOp(((MessageSigNode) gmt.msg).op.toName());
		return visited;
	}
}
