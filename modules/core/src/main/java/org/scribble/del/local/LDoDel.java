package org.scribble.del.local;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LContinue;
import org.scribble.ast.local.LDo;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.DoDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ContextBuilder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.ReachabilityEnv;

//public class LDoDel extends LSimpleInteractionNodeDel
public class LDoDel extends DoDel implements LSimpleInteractionNodeDel
{
	/*// Would like to factor out with GlobalDoDelegate, but global/local interaction node delegates extend from simple/compound base
	@Override
	public LDo leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		JobContext jcontext = builder.getJobContext();
		ModuleContext mcontext = builder.getModuleContext();
		LDo ld = (LDo) visited;
		ld.roles.getRoles().stream().forEach(
				(r) -> builder.addProtocolDependency(r,
						ld.getTargetFullProtocolName(builder.getModuleContext()), ld.getTargetRoleParameter(jcontext, mcontext, r)));
		return ld;
	}*/

	@Override
	protected void addProtocolDependency(ContextBuilder builder, Role self, ProtocolName<?> proto, Role target)
	{
		builder.addLocalProtocolDependency(self, (LProtocolName) proto, target);
	}

	/*@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder) throws ScribbleException
	{
		super.enterProtocolInlining(parent, child, builder);
		if (!builder.isCycle())
		{
			SubprotocolSig subsig = builder.peekStack();  // SubprotocolVisitor has already entered subprotocol
			builder.setRecVar(subsig);
		}
	}*/

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

	/*@Override
	public void enterFsmBuilder(ScribNode parent, ScribNode child, FsmBuilder conv)
	{
		super.enterFsmBuilder(parent, child, conv);
		if (!conv.isCycle())
		{
			SubprotocolSig subsig = conv.peekStack();  // SubprotocolVisitor has already entered subprotocol
			Set<String> labs = new HashSet<>(conv.builder.getEntry().getLabels());
			labs.add(subsig.toString());
			/*ProtocolState s = conv.builder.newState(labs);
			conv.builder.setEntry(s);* /  // No: need to relabel the existing state, not completely replace it -- cf. LRecursionDel
			ProtocolState s = conv.builder.getEntry();
			...
			conv.builder.setSubprotocolEntry(subsig);
		}
	}

	// Only called if cycle
	public LDo visitForFsmConversion(FsmBuilder conv, LDo child)
	{
		SubprotocolSig subsig = conv.peekStack();
		conv.builder.setEntry(conv.builder.getSubprotocolEntry(subsig));  // Like continue
		return child;
	}
	
	@Override
	public LDo leaveFsmBuilder(ScribNode parent, ScribNode child, FsmBuilder conv, ScribNode visited)
	{
		SubprotocolSig subsig = conv.peekStack();
		conv.builder.removeSubprotocolEntry(subsig);  // FIXME: maybe should only do if cycle (cf. GoDelDel#leaveInlineProtocolTranslation)
		return (LDo) super.leaveFsmBuilder(parent, child, conv, visited);
	}*/
}
