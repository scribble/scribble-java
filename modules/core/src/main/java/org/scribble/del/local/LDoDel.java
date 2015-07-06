package org.scribble.del.local;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.RoleArg;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.local.LContinue;
import org.scribble.ast.local.LDo;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.DoDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.PackageName;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ContextBuilder;
import org.scribble.visit.JobContext;
import org.scribble.visit.ProjectedRoleDeclFixer;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.ReachabilityEnv;

public class LDoDel extends DoDel implements LSimpleInteractionNodeDel
{
	@Override
	protected void addProtocolDependency(ContextBuilder builder, Role self, ProtocolName<?> proto, Role target)
	{
		builder.addLocalProtocolDependency(self, (LProtocolName) proto, target);
	}

	// Only called if cycle
	public LDo visitForSubprotocolInlining(ProtocolDefInliner builder, LDo child)
	{
		SubprotocolSig subsig = builder.peekStack();
		RecVarNode recvar = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, builder.getRecVar(subsig).toString());
		LContinue inlined = AstFactoryImpl.FACTORY.LContinue(recvar);
		builder.pushEnv(builder.popEnv().setTranslation(inlined));
		return child;
	}
	
	@Override
	public LDo leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		SubprotocolSig subsig = builder.peekStack();
		if (!builder.isCycle())
		{
			RecVarNode recvar = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, builder.getRecVar(subsig).toString());
			LInteractionSeq gis = (LInteractionSeq) (((InlineProtocolEnv) builder.peekEnv()).getTranslation());
			LProtocolBlock gb = AstFactoryImpl.FACTORY.LProtocolBlock(gis);
			LRecursion inlined = AstFactoryImpl.FACTORY.LRecursion(recvar, gb);
			builder.pushEnv(builder.popEnv().setTranslation(inlined));
			builder.removeRecVar(subsig);
		}	
		return (LDo) super.leaveProtocolInlining(parent, child, builder, visited);
	}

	@Override
	public ScribNode leaveProjectedRoleDeclFixing(ScribNode parent, ScribNode child, ProjectedRoleDeclFixer fixer, ScribNode visited) throws ScribbleException
	{
		LDo ld = (LDo) visited;
		LProtocolDecl lpd = ld.getTargetProtocolDecl(fixer.getJobContext(), fixer.getModuleContext());
		
		// FIXME: factor out map making with SubprotocolVisitor
		// do role args are currently as inherited from the global type -- so need to derive role map against the global protocol header
		Iterator<Role> roleargs = ld.roles.args.stream().map((ra) -> ra.val.toName()).collect(Collectors.toList()).iterator();
		
		// FIXME: "inverse projection" back to global protocol name -- maybe factor out modulename parsing part to SessionTypeFactory
		JobContext jcontext = fixer.getJobContext();
		ModuleContext mcontext = fixer.getModuleContext();
		String mname = ld.getTargetFullProtocolName(mcontext).getPrefix().toString();
		mname = mname.substring(0, mname.lastIndexOf('_'));  // FIXME: not sound
		mname = mname.substring(0, mname.lastIndexOf('_'));
		String simppn = ld.getTargetFullProtocolName(mcontext).getLastElement();
		simppn = simppn.substring(0, simppn.lastIndexOf('_'));
		ModuleName modname;
		if (mname.indexOf('.') == -1)
		{
			modname = new ModuleName(mname);
		}
		else
		{
			String[] elems = mname.split("\\.");
			PackageName packname = new PackageName(Arrays.copyOf(elems, elems.length - 1));
			modname = new ModuleName(packname, new ModuleName(elems[elems.length - 1]));
		}
		GProtocolDecl gpd = (GProtocolDecl) jcontext.getModule(modname).getProtocolDecl(new GProtocolName(simppn));
		
		Map<Role, Role> rolemap = gpd.header.roledecls.getRoles().stream().collect(Collectors.toMap((r) -> r, (r) -> roleargs.next()));
		Set<Role> occs = ((LProtocolDeclDel) lpd.del()).getProtocolDeclContext().getRoleOccurrences()
				.stream().map((r) -> rolemap.get(r)).collect(Collectors.toSet());
		List<RoleArg> ras = ld.roles.args.stream().filter((ra) -> occs.contains(ra.val.toName())).collect(Collectors.toList());
		RoleArgList roles = ld.roles.reconstruct(ras);
		return super.leaveProjectedRoleDeclFixing(parent, child, fixer, ld.reconstruct(roles, ld.args, ld.getProtocolNameNode()));
	}

	@Override
	public LDo leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		ReachabilityEnv env = checker.popEnv();
		if (checker.isCycle())
		{
			env = env.leaveRecursiveDo();
		}
		setEnv(env);
		checker.pushEnv(checker.popEnv().mergeContext(env));
		return (LDo) visited;
	}
}
