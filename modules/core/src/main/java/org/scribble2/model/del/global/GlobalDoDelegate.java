package org.scribble2.model.del.global;

import org.scribble2.model.ArgumentInstantiationList;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.RoleInstantiationList;
import org.scribble2.model.context.ModuleContext;
import org.scribble2.model.global.GlobalDo;
import org.scribble2.model.local.LocalDo;
import org.scribble2.model.name.qualified.ProtocolNameNode;
import org.scribble2.model.visit.ContextBuilder;
import org.scribble2.model.visit.JobContext;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

// FIXME: simple or compound?
public class GlobalDoDelegate extends GlobalSimpleInteractionNodeDelegate
{
	// Would like to factor out with LocalDoDelegate, but global/local interaction node delegates extend from simple/compound base
	@Override
	public GlobalDo leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException
	{
		JobContext jcontext = builder.getJobContext();
		ModuleContext mcontext = builder.getModuleContext();
		GlobalDo gd = (GlobalDo) visited;
		for (Role role : gd.roleinstans.getRoles())
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
	public GlobalDo leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		checker.pushEnv(checker.popEnv().leaveWFChoiceCheck(checker));
		return popAndSetVisitorEnv(parent, child, checker, (GlobalDo) visited);
	}

	@Override
	public void enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	{
		GlobalDo gd = (GlobalDo) child;
		//ProtocolName gpn = gd.getTargetFullProtocolName(proj.getModuleContext());
		Role self = proj.peekSelf();
		if (gd.roleinstans.getRoles().contains(self))
		{
			Role param = gd.getTargetRoleParameter(proj.getJobContext(), proj.getModuleContext(), self);
			proj.pushSelf(param);
			//proj.addProtocolDependency(gpn, param);
		}
		else
		{
			proj.pushSelf(self);
		}
		//return proj;
	}
	
	@Override
	public GlobalDo leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) throws ScribbleException //throws ScribbleException
	{
		GlobalDo gd = (GlobalDo) visited;
		Role popped = proj.popSelf();
		Role self = proj.peekSelf();
		LocalDo projection = null;
		if (gd.roleinstans.getRoles().contains(self))
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
			RoleInstantiationList roleinstans = (RoleInstantiationList) ((ProjectionEnv) gd.roleinstans.del().env()).getProjection();
			ArgumentInstantiationList arginstans = (ArgumentInstantiationList) ((ProjectionEnv) gd.arginstans.del().env()).getProjection();
			ProtocolNameNode target = Projector.makeProjectedProtocolNameNode(gd.getTargetFullProtocolName(proj.getModuleContext()), popped);
			projection = ModelFactoryImpl.FACTORY.LocalDo(roleinstans, arginstans, target);
			
			// FIXME: do guarded recursive subprotocol checking (i.e. role is used during chain) in reachability checking?
		}
		/*this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;*/
		proj.popEnv();
		proj.pushEnv(new ProjectionEnv(projection));
		return (GlobalDo) super.leaveProjection(parent, child, proj, gd);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
