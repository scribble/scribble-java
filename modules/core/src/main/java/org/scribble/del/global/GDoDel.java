package org.scribble.del.global;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GDo;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.local.LDo;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ContextBuilder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.JobContext;
import org.scribble.visit.Projector;
import org.scribble.visit.WFChoiceChecker;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.WFChoiceEnv;

public class GDoDel extends GSimpleInteractionNodeDel
{
	// Would like to factor out with LocalDoDelegate, but global/local interaction node delegates extend from simple/compound base
	@Override
	public GDo leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		JobContext jcontext = builder.getJobContext();
		ModuleContext mcontext = builder.getModuleContext();
		GDo gd = (GDo) visited;
		gd.roles.getRoles().stream().forEach(
				(r) -> builder.addProtocolDependency(r, 
						gd.getTargetFullProtocolName(builder.getModuleContext()), gd.getTargetRoleParameter(jcontext, mcontext, r)));
		return gd;
	}

	@Override
	public void enterWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker) throws ScribbleException
	{
		checker.pushEnv(checker.peekEnv().enterDoContext(checker));
	}

	@Override
	public GDo leaveWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		WFChoiceEnv env = checker.popEnv();
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
		Role self = proj.peekSelf();
		if (gd.roles.getRoles().contains(self))
		{
			// For correct name mangling, need to use the parameter corresponding to the self argument
			// N.B. -- this depends on Projector not following the Subprotocol pattern, otherwise self is wrong
			Role param = gd.getTargetRoleParameter(proj.getJobContext(), proj.getModuleContext(), self);
			proj.pushSelf(param);
		}
		else
		{
			proj.pushSelf(self);  // Dummy: just to make pop in leave work
		}
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
			RoleArgList roleinstans = gd.roles.project(self);
			NonRoleArgList arginstans = gd.args.project(self);
			LProtocolNameNode target = Projector.makeProjectedFullNameNode(gd.getTargetFullProtocolName(proj.getModuleContext()), popped);
			projection = AstFactoryImpl.FACTORY.LDo(roleinstans, arginstans, target);
			
			// FIXME: do guarded recursive subprotocol checking (i.e. role is used during chain) in reachability checking?
		}
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GDo) super.leaveProjection(parent, child, proj, gd);
	}

	@Override
	public void enterInlineProtocolTranslation(ScribNode parent, ScribNode child, ProtocolDefInliner builder) throws ScribbleException
	{
		super.enterInlineProtocolTranslation(parent, child, builder);
		if (!builder.isCycle())
		{
			SubprotocolSig subsig = builder.peekStack();  // SubprotocolVisitor has already entered subprotocol
			builder.setRecVar(subsig);
		}
	}

	// Only called if cycle
	public GDo visitForSubprotocolInlining(ProtocolDefInliner builder, GDo child)
	{
		SubprotocolSig subsig = builder.peekStack();
		RecVarNode recvar = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, builder.getRecVar(subsig).toString());
		GContinue inlined = AstFactoryImpl.FACTORY.GContinue(recvar);
		builder.pushEnv(builder.popEnv().setTranslation(inlined));
		return child;
	}
	
	@Override
	public ScribNode leaveInlineProtocolTranslation(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		SubprotocolSig subsig = builder.peekStack();
		if (!builder.isCycle())
		{
			RecVarNode recvar = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, builder.getRecVar(subsig).toString());
			GInteractionSeq gis = (GInteractionSeq) (((InlineProtocolEnv) builder.peekEnv()).getTranslation());
			GProtocolBlock gb = AstFactoryImpl.FACTORY.GProtocolBlock(gis);
			GRecursion inlined = AstFactoryImpl.FACTORY.GRecursion(recvar, gb);
			builder.pushEnv(builder.popEnv().setTranslation(inlined));
			builder.removeRecVar(subsig);
		}	
		return (GDo) super.leaveInlineProtocolTranslation(parent, child, builder, visited);
	}
}
