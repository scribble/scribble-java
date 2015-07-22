package org.scribble.del.local;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.RoleArg;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.ScribNode;
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
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.JobContext;
import org.scribble.visit.ProjectedRoleDeclFixer;
import org.scribble.visit.ProtocolDeclContextBuilder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.env.InlineProtocolEnv;

public class LDoDel extends DoDel implements LSimpleInteractionNodeDel
{
	// Part of context building
	@Override
	protected void addProtocolDependency(ProtocolDeclContextBuilder builder, Role self, ProtocolName<?> proto, Role target)
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
	public LDo
			leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		SubprotocolSig subsig = inl.peekStack();
		if (!inl.isCycle())
		{
			RecVarNode recvar = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, inl.getRecVar(subsig).toString());
			LInteractionSeq gis = (LInteractionSeq) (((InlineProtocolEnv) inl.peekEnv()).getTranslation());
			LProtocolBlock gb = AstFactoryImpl.FACTORY.LProtocolBlock(gis);
			LRecursion inlined = AstFactoryImpl.FACTORY.LRecursion(recvar, gb);
			inl.pushEnv(inl.popEnv().setTranslation(inlined));
			inl.removeRecVar(subsig);
		}	
		return (LDo) super.leaveProtocolInlining(parent, child, inl, visited);
	}

	// Pre: this pass is only run on projections (LProjectionDeclDel has source global protocol info)
	@Override
	public ScribNode
			leaveProjectedRoleDeclFixing(ScribNode parent, ScribNode child, ProjectedRoleDeclFixer fixer, ScribNode visited) throws ScribbleException
	{
		LDo ld = (LDo) visited;
		LProtocolDecl lpd = ld.getTargetProtocolDecl(fixer.getJobContext(), fixer.getModuleContext());
		
		// do role args are currently as inherited from the global type -- so need to derive role map against the global protocol header
		// Doing it off the global roldecls allows this to be done in one pass, but would probably be easier to split into two (e.g. 1st cache the proposed changes, 2nd write all changes -- the problem with a single pass is e.g. looking up the localdecl info while localdecls are being rewritten during the pass)
		// Could possibly factor out rolemap making with SubprotocolVisitor a bit, but there it maps to RoleNode and works off a root map
		JobContext jcontext = fixer.getJobContext();
		GProtocolName source = ((LProjectionDeclDel) lpd.del()).getSourceProtocol();
		GProtocolDecl gpd = (GProtocolDecl) jcontext.getModule(source.getPrefix()).getProtocolDecl(source.getSimpleName());
		Iterator<RoleArg> roleargs = ld.roles.getDoArgs().iterator();
		Map<Role, Role> rolemap = gpd.header.roledecls.getRoles().stream().collect(
				Collectors.toMap((r) -> r, (r) -> roleargs.next().val.toName()));
		Set<Role> occs = ((LProtocolDeclDel) lpd.del()).getProtocolDeclContext().getRoleOccurrences().stream().map(
				(r) -> rolemap.get(r)).collect(Collectors.toSet());

		List<RoleArg> ras = ld.roles.getDoArgs().stream().filter((ra) -> occs.contains(ra.val.toName())).collect(Collectors.toList());
		RoleArgList roles = ld.roles.reconstruct(ras);
		return super.leaveProjectedRoleDeclFixing(parent, child, fixer, ld.reconstruct(roles, ld.args, ld.getProtocolNameNode()));
	}
}
