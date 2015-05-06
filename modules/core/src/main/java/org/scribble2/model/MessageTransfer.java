package org.scribble2.model;

import java.util.LinkedList;
import java.util.List;

import org.scribble2.model.name.SimpleKindedNameNode;
import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.kind.RoleKind;
import org.scribble2.sesstype.name.KindedName;
import org.scribble2.util.ScribbleException;


public abstract class MessageTransfer extends SimpleInteractionNode
{
	//public final RoleNode src;
	public final SimpleKindedNameNode<RoleKind> src;
	public final MessageNode msg;
	//public final List<RoleNode> dests;
	public final List<SimpleKindedNameNode<RoleKind>> dests;

	/*protected MessageTransfer(CommonTree ct, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		this(ct, src, msg, dests, null, null);
	}

	public MessageTransfer(CommonTree ct, RoleNode src, MessageNode msg, List<RoleNode> dests, SimpleInteractionNodeContext sicontext)
	{
		this(ct, src, msg, dests, sicontext, null);
	}*/

	//protected MessageTransfer(RoleNode src, MessageNode msg, List<RoleNode> dests)//, SimpleInteractionNodeContext sicontext, Env env)
	protected MessageTransfer(SimpleKindedNameNode<RoleKind> src, MessageNode msg, List<SimpleKindedNameNode<RoleKind>> dests)//, SimpleInteractionNodeContext sicontext, Env env)
	{
		this.src = src;
		this.msg = msg;
		this.dests = new LinkedList<>(dests);
	}

	//protected abstract MessageTransfer reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests);//, SimpleInteractionNodeContext sicontext, Env env);
	protected abstract MessageTransfer reconstruct(SimpleKindedNameNode<RoleKind> src, MessageNode msg, List<SimpleKindedNameNode<RoleKind>> dests);//, SimpleInteractionNodeContext sicontext, Env env);

	@Override
	public MessageTransfer visitChildren(ModelVisitor nv) throws ScribbleException
	{
		//RoleNode src = (RoleNode) visitChild(this.src, nv);
		SimpleKindedNameNode<RoleKind> src = SimpleKindedNameNode.castSimpleKindedNameNode(RoleKind.KIND, visitChild(this.src, nv));
		//MessageNode msg = visitMessageNode(nv, this.msg);
		MessageNode msg = (MessageNode) visitChild(this.msg, nv);
		/*List<RoleNode> dests = new LinkedList<RoleNode>();
		for (RoleNode dest : this.dests)
		{
			dests.add((RoleNode) visitChild(dest, nv));
		}*/
		//return new MessageTransfer(this.ct, src, msg, dests, getContext(), getEnv());
		//List<RoleNode> dests = visitChildListWithClassCheck(this, this.dests, nv);
		List<SimpleKindedNameNode<RoleKind>> dests = visitChildListWithClassCheck(this, this.dests, nv);  // FIXME: use kinded name node cast
		return reconstruct(src, msg, dests);//, getContext(), getEnv());
	}
	
	//public List<Role> getDestinationRoles()
	public List<KindedName<RoleKind>> getDestinationRoles()
	{
		//List<Role> dests = new LinkedList<>();
		List<KindedName<RoleKind>> dests = new LinkedList<>();
		//for (RoleNode rn : this.dests)
		for (SimpleKindedNameNode<RoleKind> rn : this.dests)
		{
			dests.add(rn.toName());
		}
		return dests;
	}

	/*@Override
	public MessageTransfer leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		Role src = this.src.toName();
		Message msg = this.msg.toMessage();
		CompoundInteractionContext cicontext = (CompoundInteractionContext) builder.peekContext();
		for (Role dest : this.dests.stream().map((rn) -> rn.toName()).collect(Collectors.toList()))
		{
			//builder.replaceContext(((CompoundInteractionContext) builder.peekContext()).addMessage(src, dest, msg));
			cicontext = cicontext.addMessage(src, dest, msg);
		}
		builder.replaceContext(cicontext);
		return this;
	}

	@Override
	public MessageTransfer leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		Role src = this.src.toName();
		Message msg = this.msg.toMessage();
		WellFormedChoiceEnv env = checker.getEnv();
		for (Role dest : this.dests.stream().map((rn) -> rn.toName()).collect(Collectors.toList()))
		{
			//checker.setEnv(checker.getEnv().addMessageForSubprotocol(checker, src, dest, msg.toScopedMessage(checker.getScope())));
			env = env.addMessageForSubprotocol(checker, src, dest, msg.toScopedMessage(checker.getScope()));
		}
		checker.setEnv(env);
		return this;
	}
	
	/*@Override
	public MessageTransfer substitute(Substitutor subs) throws ScribbleException
	{
		RoleNode src = subs.substituteRole(this.src.toName());
		MessageNode msg;
		if (this.msg.isParameterNode())
		{
			msg = (MessageNode) subs.substituteParameter(((ParameterNode) this.msg).toName());
		}
		else
		{
			msg = (MessageNode) subs.visit((AbstractNode) this.msg);
		}
		List<RoleNode> dests = new LinkedList<>();
		for (RoleNode dest : this.dests)
		{
			dests.add(subs.substituteRole(dest.toName()));
		}
		return new MessageTransfer(this.ct, src, msg, dests);
	}

	@Override
	public MessageTransfer leave(EnvVisitor nv) throws ScribbleException
	{
		MessageTransfer gmt = (MessageTransfer) super.leave(nv);	
		Env env = nv.getEnv();
		Role src = gmt.src.toName();
		for (RoleNode rn : gmt.dests)
		{
			Role dest = rn.toName();
			Operator op = gmt.msg.getOperator();
			if (!env.roles.isRoleEnabled(dest))
			{
				
				// FIXME: move to Global checkWellFormedness
				if (nv instanceof WellFormednessChecker)
				{
					/*if (this instanceof GlobalMessageTransfer)
					{
						CommonTree ct = (CommonTree) this.ct.parent.parent.parent;
						String type = Util.getAntlrNodeType(ct);
						if (!(type.equals(AntlrConstants.GLOBALCHOICE_NODE_TYPE) || type.equals(AntlrConstants.GLOBALPROTOCOLDEF_NODE_TYPE)))
						{
							throw new ScribbleException("Enabling message not in appropriate context: " + this);
						}
					}*
				
					if (!env.roles.canEnable())
					{
						throw new ScribbleException("Enabling message in inappropriate context: " + this);
					}
				}
				
				env.roles.enableRole(src, dest, op);
			}
			env.ops.addOperator(src, dest, op);  // Recorded separately from enabling ops
		}
		return gmt;	
	}*/
	
	/*// Maybe move into NodeVisitor -- no: now redundant, as all nodes including names nodes are uniformly visited
	public static MessageNode visitMessageNode(NodeVisitor nv, MessageNode msg) throws ScribbleException
	{
		/*if (msg.isParameterNode())
		{
			return (ParameterNode) nv.visit((ParameterNode) msg);
		}
		else //if (this.msg.isMessageSignatureNode())
		{
			return (MessageSignatureNode) nv.visit((MessageSignatureNode) msg);
		}*
		return (MessageNode) nv.visit((Node) msg);
	}*/
}
