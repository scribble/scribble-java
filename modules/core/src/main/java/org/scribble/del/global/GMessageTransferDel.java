package org.scribble.del.global;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.MessageNode;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LReceive;
import org.scribble.ast.local.LocalNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.visit.MessageIdCollector;
import org.scribble.ast.visit.ModelBuilder;
import org.scribble.ast.visit.Projector;
import org.scribble.ast.visit.WellFormedChoiceChecker;
import org.scribble.ast.visit.env.ModelEnv;
import org.scribble.ast.visit.env.ProjectionEnv;
import org.scribble.ast.visit.env.WellFormedChoiceEnv;
import org.scribble.main.ScribbleException;
import org.scribble.model.global.ModelAction;
import org.scribble.model.local.Receive;
import org.scribble.model.local.Send;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

// FIXME: make base MessageTransferDelegate?
public class GMessageTransferDel extends GSimpleInteractionNodeDel
{
	public GMessageTransferDel()
	{
		
	}

	@Override
	public GMessageTransfer leaveWFChoiceCheck(ScribNode parent, ScribNode child, WellFormedChoiceChecker checker, ScribNode visited) throws ScribbleException
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
	public GMessageTransfer leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException //throws ScribbleException
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
			RoleNode src = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, gmt.src.toName().toString());
			//MessageNode msg = (MessageNode) ((ProjectionEnv) gmt.msg.del().getEnv()).getProjection();
			MessageNode msg = (MessageNode) gmt.msg;  // FIXME: need namespace prefix update?
			List<RoleNode> dests =
					//destroles.stream().map((d) -> new RoleNode(d.toString())).collect(Collectors.toList());
					//destroles.stream().map((d) -> (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.ROLE, d.toString())).collect(Collectors.toList());
					destroles.stream().map((d) -> (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, d.toString())).collect(Collectors.toList());
			if (srcrole.equals(self))
			{
				projection = AstFactoryImpl.FACTORY.LSend(src, msg, dests);
			}
			if (destroles.contains(self))
			{
				if (projection == null)
				{
					projection = AstFactoryImpl.FACTORY.LReceive(src, msg, dests);
				}
				else
				{
					LReceive lr = AstFactoryImpl.FACTORY.LReceive(src, msg, dests);
					List<LInteractionNode> lis = Arrays.asList(new LInteractionNode[]{(LInteractionNode) projection, lr});
					projection = AstFactoryImpl.FACTORY.LInteractionSequence(lis);
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
	public ScribNode leaveOpCollection(ScribNode parent, ScribNode child, MessageIdCollector coll, ScribNode visited)
	{
		GMessageTransfer gmt = (GMessageTransfer) visited;
		/*if (!gmt.msg.isMessageSigNode())
		{
			throw new RuntimeException("Op collection should be run on ground types: " + gmt.msg);  // Ground means non-param -- signames are OK (from sigdecls)
		}*/
		if (gmt.msg.isMessageSigNode() || gmt.msg.isMessageSigNameNode())
		{
			coll.addMessageId(gmt.msg.toMessage().getId());
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + gmt.msg);
		}
		return visited;
	}
	
	@Override
	public GMessageTransfer leaveModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		GMessageTransfer gmt = (GMessageTransfer) visited;
		ModelEnv env = builder.popEnv();
		Set<ModelAction> actions = env.getActions();
		Map<Role, ModelAction> leaves = new HashMap<>();
		if (gmt.dests.size() > 1)
		{
			throw new RuntimeException("TODO: " + gmt);
		}
		Role src = gmt.src.toName();
		Role dest = gmt.dests.get(0).toName();
		MessageId mid = gmt.msg.toMessage().getId();
		ModelAction send = new ModelAction(src, new Send(dest, mid, Payload.EMPTY_PAYLOAD));  // FIXME: payload hack
		ModelAction receive = new ModelAction(dest, new Receive(src, mid, Payload.EMPTY_PAYLOAD));  // FIXME: payload hack
		receive.addDependency(send);
		actions.add(send);
		actions.add(receive);
		leaves.put(src, send);
		leaves.put(dest, receive);
		env = env.setActions(actions, leaves);
		builder.pushEnv(env);
		return (GMessageTransfer) super.leaveModelBuilding(parent, child, builder, visited);
	}
}
