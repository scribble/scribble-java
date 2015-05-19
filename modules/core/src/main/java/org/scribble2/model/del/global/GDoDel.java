package org.scribble2.model.del.global;

import org.scribble2.model.ArgList;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.RoleArgList;
import org.scribble2.model.context.ModuleContext;
import org.scribble2.model.global.GDo;
import org.scribble2.model.local.LDo;
import org.scribble2.model.name.qualified.LProtocolNameNode;
import org.scribble2.model.visit.ContextBuilder;
import org.scribble2.model.visit.JobContext;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

// FIXME: simple or compound?
public class GDoDel extends GSimpleInteractionNodeDel
{
	// Would like to factor out with LocalDoDelegate, but global/local interaction node delegates extend from simple/compound base
	@Override
	public GDo leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException
	{
		JobContext jcontext = builder.getJobContext();
		ModuleContext mcontext = builder.getModuleContext();
		GDo gd = (GDo) visited;
		
		// FIXME: use lambda (also in LocalDoDelegate -- factor out)
		
		for (Role role : gd.roles.getRoles())
		{
			builder.addProtocolDependency(role, gd.getTargetFullProtocolName(builder.getModuleContext()), gd.getTargetRoleParameter(jcontext, mcontext, role));
		}
		return gd;
	}

	@Override
	public void enterWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker) throws ScribbleException
	{
		checker.pushEnv(checker.peekEnv().enterDoContext(checker));
	}

	@Override
	public GDo leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		/*checker.pushEnv(checker.popEnv().leaveWFChoiceCheck(checker));
		return popAndSetVisitorEnv(parent, child, checker, (GDo) visited);*/
		WellFormedChoiceEnv env = checker.popEnv();
		//if (checker.isCycle())  // Cf. LDoDel, isCycle done inside env.leaveWFChoiceCheck
		{
			env = env.leaveWFChoiceCheck(checker);
		}
		setEnv(env);
		checker.pushEnv(checker.popEnv().mergeContext(env));
		return (GDo) visited;
	}

	@Override
	public void enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	{
		super.enterProjection(parent, child, proj);

		GDo gd = (GDo) child;
		//ProtocolName gpn = gd.getTargetFullProtocolName(proj.getModuleContext());
		Role self = proj.peekSelf();
		if (gd.roles.getRoles().contains(self))
		{
			// For correct name mangling, need to use the parameter corresponding to the self argument
			// N.B. -- this depends on Projector not following the Subprotocol pattern, otherwise self is wrong
			Role param = gd.getTargetRoleParameter(proj.getJobContext(), proj.getModuleContext(), self);
			proj.pushSelf(param);
			//proj.addProtocolDependency(gpn, param);
		}
		else
		{
			proj.pushSelf(self);  // Dummy: just to make pop in leave work
		}
		//return proj;
	}
	
	@Override
	public GDo leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException //throws ScribbleException
	{
		GDo gd = (GDo) visited;
		Role popped = proj.popSelf();
		Role self = proj.peekSelf();
		LDo projection = null;
		if (gd.roles.getRoles().contains(self))
		{
			/*ModuleContext mcontext = proj.getModuleContext();
			ProtocolDecl<? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
					pd = getTargetProtocolDecl(proj.job.getContext(), mcontext);
			
			SimpleProtocolNameNode pn = proj.makeProjectedLocalName(pd.name.toName());
			RoleDeclList roledecls = pd.roledecls.project(proj.peekSelf());
			ParameterDeclList paramdecls = pd.paramdecls.project(proj.peekSelf());
			//LocalProtocolDefinition def = (LocalProtocolDefinition) ((ProjectionEnv) gpd.def.getEnv()).getProjection();
			//LocalProtocolDecl lpd = new LocalProtocolDecl(null, pn, roledecls, paramdecls, def);*/
			
			//ScopeNode scope = (this.scope == null) ? null : new ScopeNode(null, this.scope.toName().toString());
			/*RoleInstantiationList roleinstans = gd.roleinstans.project(self);
			ArgumentInstantiationList arginstans = gd.arginstans.project(self);
			ProtocolNameNode target = Projector.makeProjectedProtocolNameNodes(gd.getTargetFullProtocolName(proj.getModuleContext()), popped);
			//projection = new LocalDo(null, scope, roleinstans, arginstans, target);
			projection = ModelFactoryImpl.FACTORY.LocalDo(null, scope, roleinstans, arginstans, target);*/
			/*RoleInstantiationList roleinstans = (RoleInstantiationList) ((ProjectionEnv) gd.roleinstans.del().env()).getProjection();
			ArgumentInstantiationList arginstans = (ArgumentInstantiationList) ((ProjectionEnv) gd.arginstans.del().env()).getProjection();*/
			RoleArgList roleinstans = gd.roles.project(self);
			ArgList arginstans = gd.args.project(self);
			LProtocolNameNode target = Projector.makeProjectedProtocolNameNode(gd.getTargetFullProtocolName(proj.getModuleContext()), popped);
			projection = ModelFactoryImpl.FACTORY.LocalDo(roleinstans, arginstans, target);
			
			// FIXME: do guarded recursive subprotocol checking (i.e. role is used during chain) in reachability checking?
		}
		/*this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;*/
		proj.popEnv();
		proj.pushEnv(new ProjectionEnv(projection));
		return (GDo) super.leaveProjection(parent, child, proj, gd);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
