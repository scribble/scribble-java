package org.scribble2.model.global;

import java.util.List;

import org.scribble2.model.Constants;
import org.scribble2.model.MessageNode;
import org.scribble2.model.MessageTransfer;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.SimpleKindedNameNode;
import org.scribble2.sesstype.kind.RoleKind;

public class GlobalMessageTransfer extends MessageTransfer implements SimpleGlobalInteractionNode
{
	//public GlobalMessageTransfer(RoleNode src, MessageNode msg, List<RoleNode> dests)
	public GlobalMessageTransfer(SimpleKindedNameNode<RoleKind> src, MessageNode msg, List<SimpleKindedNameNode<RoleKind>> dests)
	{
		//this(t, src, msg, dests, null, null);
		super(src, msg, dests);
	}

	/*protected GlobalMessageTransfer(CommonTree ct, RoleNode src, MessageNode msg, List<RoleNode> dests, SimpleInteractionNodeContext sicontext)
	{
		super(ct, src, msg, dests, sicontext);
	}*/

	/*protected GlobalMessageTransfer(CommonTree ct, RoleNode src, MessageNode msg, List<RoleNode> dests, SimpleInteractionNodeContext sicontext, Env env)
	{
		super(ct, src, msg, dests, sicontext, env);
	}*/

	@Override
	//protected GlobalMessageTransfer reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)//, SimpleInteractionNodeContext sicontext, Env env)
	protected GlobalMessageTransfer reconstruct(SimpleKindedNameNode<RoleKind> src, MessageNode msg, List<SimpleKindedNameNode<RoleKind>> dests)//, SimpleInteractionNodeContext sicontext, Env env)
	{
		ModelDelegate del = del();
		GlobalMessageTransfer gmt = new GlobalMessageTransfer(src, msg, dests);//, sicontext, env);
		gmt = (GlobalMessageTransfer) gmt.del(del);
		return gmt;
	}

	/*@Override
	public GlobalMessageTransfer leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		MessageTransfer msgtrans = super.leaveContextBuilding(builder);
		return new GlobalMessageTransfer(msgtrans.ct, msgtrans.src, msgtrans.msg, msgtrans.dests, msgtrans.getContext());
	}* /

	@Override
	public GlobalMessageTransfer leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		Role src = this.src.toName();
		if (!checker.getEnv().isEnabled(src))
		{
			throw new ScribbleException("Role not enabled: " + src);
		}
		/*MessageTransfer msgtrans = super.leaveWFChoiceCheck(checker);
		return new GlobalMessageTransfer(msgtrans.ct, msgtrans.src, msgtrans.msg, msgtrans.dests, msgtrans.getContext(), msgtrans.getEnv());* /
		return (GlobalMessageTransfer) super.leaveWFChoiceCheck(checker);
	}
	
	@Override
	public GlobalMessageTransfer leaveProjection(Projector proj) //throws ScribbleException
	{
		Role self = proj.peekSelf();
		Role srcrole = this.src.toName();
		List<Role> destroles = getDestinationRoles();
		LocalNode projection = null;
		if (srcrole.equals(self) || destroles.contains(self))
		{
			RoleNode src = new RoleNode(null, this.src.toName().toString());  // FIXME: project by visiting
			MessageNode msg = (MessageNode) ((ProjectionEnv) this.msg.getEnv()).getProjection();
			List<RoleNode> dests =
					destroles.stream().map((d) -> new RoleNode(null, d.toString())).collect(Collectors.toList());
			if (srcrole.equals(self))
			{
				projection = new LocalSend(null, src, msg, dests);
			}
			if (destroles.contains(self))
			{
				if (projection == null)
				{
					projection = new LocalReceive(null, src, msg, dests);
				}
				else
				{
					List<LocalInteraction> lis = Arrays.asList(new LocalInteraction[]{(LocalInteraction) projection, new LocalReceive(null, src, msg, dests)});
					projection = new LocalInteractionSequence(null, lis);
				}
			}
		}
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}
	
	/*@Override
	public GlobalMessageTransfer substitute(Substitutor subs) throws ScribbleException
	{

		MessageTransfer mt = super.substitute(subs);
		return new GlobalMessageTransfer(mt.ct, mt.src, mt.msg, mt.dests);
	}

	@Override
	public GlobalMessageTransfer checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{
		GlobalMessageTransfer gmt = (GlobalMessageTransfer) super.checkWellFormedness(wfc);
		Env env = wfc.getEnv();
		Role src = gmt.src.toName();
		if (!env.roles.isRoleEnabled(src))
		{
			throw new ScribbleException("Source role not enabled: " + src);
		}
		Set<Name> dests = new HashSet<>();
		for (RoleNode rn : gmt.dests)
		{
			Role dest = rn.toName();
			if (dests.contains(dest))
			{
				throw new ScribbleException("Duplicate destination role: " + rn);
			}
			if (!env.roles.isRoleDeclared(dest))
			{
				throw new ScribbleException("Destination role not declared: " + dest);
			}
			dests.add(dest);
		}
		if (gmt.msg.isParameterNode())
		{
		  // Maybe visit parameter AST nodes directly, but role/arg instantiation needs special treatment
			Parameter param = ((ParameterNode) gmt.msg).toName();
			if (!env.params.isParameterDeclared(param))
			{
				throw new ScribbleException("Bad parameter: " + param);
			}
			ParameterDecl.Kind pdkind = env.params.getParameterKind(param);
			if (!pdkind.equals(ParameterDecl.Kind.SIG))
			{
				throw new ScribbleException("GlobalMessageTransfer type parameter should be of SIG kind, not: " + pdkind);
			}
		}
		return gmt;	
	}*
	
	@Override
	public LocalInteractionSequence project(Projector proj)
	{
		Role role = proj.getRole();
		List<LocalInteraction> lis = new LinkedList<LocalInteraction>();
		if (this.src.toName().equals(role))
		{
			lis.add(new LocalSend(this.ct, this.src, this.msg, this.dests));
		}
		for (RoleNode rn : this.dests)
		{
			if (rn.toName().equals(role))
			{
				//lis.add(new LocalReceive(this.ct, this.src, this.msg, this.dests));
				List<RoleNode> dest = new LinkedList<>();
				dest.add(rn);
				lis.add(new LocalReceive(this.ct, this.src, this.msg, dest));
				break;
			}
		}
		return new LocalInteractionSequence(null, lis);
	}

	@Override
	public GlobalMessageTransfer collectRoles(RoleCollector rc) throws ScribbleException
	{
		rc.addRole(this.src.toName());
		for (RoleNode dest : this.dests)
		{
			rc.addRole(dest.toName());
		}
		return (GlobalMessageTransfer) super.collectRoles(rc);
	}*/

	/*@Override
	public GlobalMessageTransfer visitChildren(NodeVisitor nv) throws ScribbleException
	{
		MessageTransfer mt = super.visitChildren(nv);
		return new GlobalMessageTransfer(mt.ct, mt.src, mt.msg, mt.dests, mt.getContext(), mt.getEnv());
	}*/

	@Override
	public String toString()
	{
		String s = this.msg + " " + Constants.FROM_KW + " " + this.src + " " + Constants.TO_KW + " " + this.dests.get(0);
		//for (RoleNode dest : this.dests.subList(1, this.dests.size()))
		for (SimpleKindedNameNode<RoleKind> dest : this.dests.subList(1, this.dests.size()))
		{
			s += ", " + dest;
		}
		return s + ";";
	}

	@Override
	protected GlobalMessageTransfer copy()
	{
		return new GlobalMessageTransfer(this.src, this.msg, this.dests);
	}
}
