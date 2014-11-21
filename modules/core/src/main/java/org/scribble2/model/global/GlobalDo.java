package org.scribble2.model.global;

import org.scribble2.model.ArgumentInstantiationList;
import org.scribble2.model.Do;
import org.scribble2.model.ModelNodeBase;
import org.scribble2.model.RoleInstantiationList;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.qualified.ProtocolNameNode;
import org.scribble2.model.name.simple.ScopeNode;

public class GlobalDo extends Do implements GlobalInteraction
{
	/*public GlobalDo(RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	{
		this(null, roleinstans, arginstans, proto);
		//super(null, roleinstans, arginstans, proto);
	}*/

	public GlobalDo(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	{
		super(scope, roleinstans, arginstans, proto);
	}

	@Override
	protected Do reconstruct(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	{
		ModelDelegate del = del();
		GlobalDo gd = new GlobalDo(scope, roleinstans, arginstans, proto);
		gd = (GlobalDo) gd.del(del);
		return gd;
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new GlobalDo(this.scope, this.roleinstans, this.arginstans, this.proto);
	}

	/*protected GlobalDo(CommonTree ct, ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNodes proto, SimpleInteractionNodeContext sicontext)
	{
		super(ct, scope, roleinstans, arginstans, proto, sicontext);
	}* /

	protected GlobalDo(CommonTree ct, ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto, SimpleInteractionNodeContext sicontext, Env env)
	{
		super(ct, scope, roleinstans, arginstans, proto, sicontext, env);
	}

	/*class ProjectionSkipRole extends Role
	{
		private static final long serialVersionUID = 1L;
		
		public ProjectionSkipRole(String text)
		{
			super(text);
		}
	}* /

	@Override
	public Projector enterProjection(Projector proj)
	{
		ProtocolName gpn = getTargetFullProtocolName(proj.getModuleContext());
		Role self = proj.peekSelf();
		if (this.roleinstans.getRoles().contains(self))
		{
			Role param = getTargetRoleParameter(proj.getJobContext(), proj.getModuleContext(), self);
			proj.pushSelf(param);
			proj.addProtocolDependency(gpn, param);
		}
		else
		{
			proj.pushSelf(self);
		}
		return proj;
	}
	
	@Override
	public GlobalDo leaveProjection(Projector proj) //throws ScribbleException
	{
		Role popped = proj.popSelf();
		Role self = proj.peekSelf();
		LocalDo projection = null;
		if (this.roleinstans.getRoles().contains(self))
		{
			/*ModuleContext mcontext = proj.getModuleContext();
			ProtocolDecl<? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
					pd = getTargetProtocolDecl(proj.job.getContext(), mcontext);
			
			SimpleProtocolNameNode pn = proj.makeProjectedLocalName(pd.name.toName());
			RoleDeclList roledecls = pd.roledecls.project(proj.peekSelf());
			ParameterDeclList paramdecls = pd.paramdecls.project(proj.peekSelf());
			//LocalProtocolDefinition def = (LocalProtocolDefinition) ((ProjectionEnv) gpd.def.getEnv()).getProjection();
			//LocalProtocolDecl lpd = new LocalProtocolDecl(null, pn, roledecls, paramdecls, def);* /
			
			ScopeNode scope = (this.scope == null) ? null : new ScopeNode(null, this.scope.toName().toString());
			RoleInstantiationList roleinstans = this.roleinstans.project(self);
			ArgumentInstantiationList arginstans = this.arginstans.project(self);
			ProtocolNameNode target = Projector.makeProjectedProtocolNameNodes(getTargetFullProtocolName(proj.getModuleContext()), popped);
			projection = new LocalDo(null, scope, roleinstans, arginstans, target);
			
			// FIXME: do guarded recursive subprotocol checking (i.e. role is used during chain) in reachability checking?
		}
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}
	
	public Role getTargetRoleParameter(JobContext jcontext, ModuleContext mcontext, Role role)
	{
		Iterator<Role> args = this.roleinstans.getRoles().iterator();
		Iterator<Role> params = getTargetProtocolDecl(jcontext, mcontext).roledecls.getRoles().iterator();
		while (args.hasNext())
		{
			Role arg = args.next();
			Role param = params.next();
			if (arg.equals(role))
			{
				return param;
			}
		}
		throw new RuntimeException("Not an argument role: " + role);
	}

	@Override
	protected GlobalDo reconstruct(CommonTree ct, ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto, SimpleInteractionNodeContext sicontext, Env env)
	{
		return new GlobalDo(ct, scope, roleinstans, arginstans, proto, sicontext, env);
	}

	/*@Override
	public GlobalDo leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		Do doo = (Do) super.leaveWFChoiceCheck(checker);
		return new GlobalDo(doo.ct, doo.scope, doo.roleinstans, doo.arginstans, doo.proto, doo.getContext(), doo.getEnv());
	}
	
	/*@Override 
	public Do insertScopes(ScopeInserter si) throws ScribbleException
	{
		Do doo = (Do) super.insertScopes(si);
		return new GlobalDo(doo.ct, doo.scope, doo.ril, doo.ail, doo.cn);
	}

	@Override
	public GlobalDo checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{
		GlobalDo gd = (GlobalDo) super.checkWellFormedness(wfc);
		
		Env env = wfc.getEnv();
		ProtocolName fmn = getFullTargetProtocolName(env);
		ProtocolSignature sig = getTargetProtocolDecl(env).getProtocolSignature(fmn);
		SubprotocolSignature sub = sig.toSubprotocolSignature(env, this.ril, this.ail);
		if (!env.stack.isEnabled(sub))
		{
			throw new ScribbleException("Bad recursive do: " + this);
		}
			
		//RolesEnv before = new RolesEnv(env, env.roles);
		GlobalDo visited = (GlobalDo) gd.visitTarget(wfc);
		//RolesEnv after = new RolesEnv(wfc.getEnv(), wfc.getEnv().roles);
		
		/* // Duplicated form MessageTransfer
		CommonTree ct = (CommonTree) this.ct.parent.parent.parent;
		String type = Util.getAntlrNodeType(ct);
		if (!before.getEnabled().equals(after.getEnabled()))
		{
			if (!(type.equals(AntlrConstants.GLOBALCHOICE_NODE_TYPE) || type.equals(AntlrConstants.GLOBALPROTOCOLDEF_NODE_TYPE)))
			{
				throw new ScribbleException("Enabling message not in appropriate context: " + this + ", " + type);
			}
		}*
		
		return visited;
	}

	@Override
	public GlobalDo substitute(Substitutor subs) throws ScribbleException
	{
		Do doo = super.substitute(subs);
		return new GlobalDo(doo.ct, doo.scope, doo.ril, doo.ail, doo.cn);
	}
	
	@Override
	public LocalDo project(Projector proj) throws ScribbleException
	{
		Role role = proj.getRole();
		int i = 0;
		for (RoleInstantiation ri : this.ril.is)
		{
			if (ri.arg.toName().equals(role)) // FIXME: check target role involvement? -- it's affecting e.g. choice blocks
			{
				RoleCollector rc = new RoleCollector(proj.job, proj.getEnv());  // env only used for subprotocol stack
				//rc.visit(this);  // No: would push subprotocol again
				collectRoles(rc);

				if (rc.getRoles().contains(proj.getRole()))
				{
					ProtocolName pn = getFullTargetProtocolName(proj.getEnv()); // FIXME: module name needs to be mangled too
					//ProtocolName fpn = proj.getProjectedSimpleProtocolName(pn); // FIXME: need target parameter role, not projection role
					//CompoundName cn = new CompoundName(fpn.text); // FIXME: project name -- use full projected name (also sort out import and payload type names)
					
					GlobalProtocolDecl target = getTargetProtocolDecl(proj.getEnv());
					Role foo = target.rdl.rds.get(i).name.toName();
					
					ProtocolName fpn = proj.getProjectedFullProtocolName(pn, foo);
					
					// FIXME: record inter module dependencies for import projections -- could be part of proper env pushing (globalprotocoldecl)
					proj.addModuleImport(fpn);
					CompoundNameNodes cn = new CompoundNameNodes(fpn.text);
					
					return new LocalDo(null, this.scope, this.ril, this.ail, cn);
				}
			}
			i++;
		}
		return null;
	}

	@Override
	public GlobalDo collectRoles(RoleCollector rc) throws ScribbleException
	{
		GlobalDo gd = (GlobalDo) super.collectRoles(rc);
		return (GlobalDo) gd.visitTarget(rc);
	}*/

	/*@Override
	public GlobalDo visitChildren(NodeVisitor nv) throws ScribbleException
	{
		Do doo = super.visitChildren(nv);
		return new GlobalDo(doo.ct, doo.scope, doo.roleinstans, doo.arginstans, doo.proto, doo.getContext(), doo.getEnv());
	}*/

	/*@Override
	public ProtocolName getFullTargetProtocolName(Env env) throws ScribbleException
	{
		return env.getFullGlobalProtocolName(new ProtocolName(this.cn.compileName()));
	}
	
	@Override
	public GlobalProtocolDecl getTargetProtocolDecl(Env env) throws ScribbleException
	{
		return env.getGlobalProtocolDecl(getFullTargetProtocolName(env));
	}*/
}
