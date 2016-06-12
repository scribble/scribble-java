package org.scribble.del.global;

import java.util.List;

import org.scribble.ast.MessageSigNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.local.LNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.MessageTransferDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.global.GModelAction;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.GlobalModelBuilder;
import org.scribble.visit.NameDisambiguator;
import org.scribble.visit.Projector;
import org.scribble.visit.WFChoiceChecker;
import org.scribble.visit.env.WFChoiceEnv;

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
			throw new ScribbleException("TODO: " + gmt);
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
			throw new ScribbleException("Role not enabled: " + src);
		}
		Message msg = gmt.msg.toMessage();

		//... FIXME: project message/payload, i.e. delegation types

		WFChoiceEnv env = checker.popEnv();
		for (Role dest : gmt.getDestinationRoles())
		{
			// FIXME: better to check as global model error (role stuck on uncomnected send)
			if (!env.isConnected(src, dest))
			{
				throw new ScribbleException("Roles not (necessarily) connected: " + src + ", " + dest);
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
	
	// Cf. LSend/Receive enter/leaveEndpointGraphBuilding
	@Override
	public GMessageTransfer leaveModelBuilding(ScribNode parent, ScribNode child, GlobalModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		/*GMessageTransfer gmt = (GMessageTransfer) visited;
		ModelEnv env = builder.popEnv();
		Set<GModelAction> actions = env.getActions();
		Map<Role, GModelAction> leaves = new HashMap<>();
		if (gmt.getDestinations().size() > 1)
		{
			throw new RuntimeException("TODO: " + gmt);
		}
		Role src = gmt.src.toName();
		Role dest = gmt.getDestinations().get(0).toName();
		MessageId<?> mid = gmt.msg.toMessage().getId();
		GModelAction send = new GModelAction(src, new Send(dest, mid, Payload.EMPTY_PAYLOAD));  // FIXME: payload hack
		GModelAction receive = new GModelAction(dest, new Receive(src, mid, Payload.EMPTY_PAYLOAD));  // FIXME: payload hack
		receive.addDependency(send);
		actions.add(send);
		actions.add(receive);
		leaves.put(src, send);
		leaves.put(dest, receive);
		env = env.setActions(actions, leaves);
		builder.pushEnv(env);
		return (GMessageTransfer) GSimpleInteractionNodeDel.super.leaveModelBuilding(parent, child, builder, visited);*/

		GMessageTransfer ls = (GMessageTransfer) visited;
		List<RoleNode> dests = ls.getDestinations();
		if (dests.size() > 1)
		{
			throw new RuntimeException("TODO: " + ls);
		}
		Role peer = dests.get(0).toName();
		MessageId<?> mid = ls.msg.toMessage().getId();
		Payload payload = ls.msg.isMessageSigNode()  // Hacky?
					? ((MessageSigNode) ls.msg).payloads.toPayload()
					: Payload.EMPTY_PAYLOAD;
		builder.builder.addEdge(builder.builder.getEntry(), new GModelAction(ls.src.toName(), peer, mid, payload), builder.builder.getExit());
		//builder.builder.addEdge(builder.builder.getEntry(), GModelAction.get(ls.src.toName(), peer, mid, payload), builder.builder.getExit());
		return (GMessageTransfer) super.leaveModelBuilding(parent, child, builder, ls);
	}
	
	/*@Override
	public ScribNode leaveWFChoicePathCheck(ScribNode parent, ScribNode child, WFChoicePathChecker coll, ScribNode visited) throws ScribbleException
	//public ScribNode leavePathCollection(ScribNode parent, ScribNode child, PathCollectionVisitor coll, ScribNode visited) throws ScribbleException
	{
		GMessageTransfer gmt = (GMessageTransfer) visited;
		Role src = gmt.src.toName();
		Role dest = gmt.getDestinations().get(0).toName();
		MessageId<?> mid = gmt.msg.toMessage().getId();
		Payload payload = (gmt.msg.isMessageSigNode()) ? ((MessageSigNode) gmt.msg).payloads.toPayload() : Payload.EMPTY_PAYLOAD;

		//System.out.println("AAA1: " + coll.peekEnv().getPaths());
		coll.pushEnv(coll.popEnv().append(new Communication(src, dest, mid, payload)));
		//System.out.println("AAA2: " + coll.peekEnv().getPaths());
		
		return visited;
	}*/
}
