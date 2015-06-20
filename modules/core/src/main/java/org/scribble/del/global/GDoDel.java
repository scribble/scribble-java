package org.scribble.del.global;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.global.GDo;
import org.scribble.ast.local.LDo;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ContextBuilder;
import org.scribble.visit.JobContext;
import org.scribble.visit.Projector;
import org.scribble.visit.WellFormedChoiceChecker;
import org.scribble.visit.env.ProjectionEnv;
import org.scribble.visit.env.WellFormedChoiceEnv;

// FIXME: simple or compound?
public class GDoDel extends GSimpleInteractionNodeDel
{
	// Would like to factor out with LocalDoDelegate, but global/local interaction node delegates extend from simple/compound base
	@Override
	public GDo leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException
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
	public void enterWFChoiceCheck(ScribNode parent, ScribNode child, WellFormedChoiceChecker checker) throws ScribbleException
	{
		checker.pushEnv(checker.peekEnv().enterDoContext(checker));
	}

	@Override
	public GDo leaveWFChoiceCheck(ScribNode parent, ScribNode child, WellFormedChoiceChecker checker, ScribNode visited) throws ScribbleException
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
	public void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
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
	public GDo leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException //throws ScribbleException
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
			NonRoleArgList arginstans = gd.args.project(self);
			LProtocolNameNode target = Projector.makeProjectedFullNameNode(gd.getTargetFullProtocolName(proj.getModuleContext()), popped);
			projection = AstFactoryImpl.FACTORY.LDo(roleinstans, arginstans, target);
			
			// FIXME: do guarded recursive subprotocol checking (i.e. role is used during chain) in reachability checking?
		}
		/*this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;*/
		ProjectionEnv env = proj.popEnv();
		proj.pushEnv(env.setProjection(projection));
		return (GDo) super.leaveProjection(parent, child, proj, gd);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
