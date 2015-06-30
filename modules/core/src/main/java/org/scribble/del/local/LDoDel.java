package org.scribble.del.local;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.local.LDo;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.ProtocolState;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.visit.ContextBuilder;
import org.scribble.visit.FsmBuilder;
import org.scribble.visit.JobContext;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.ReachabilityEnv;

public class LDoDel extends LSimpleInteractionNodeDel
{
	// Would like to factor out with GlobalDoDelegate, but global/local interaction node delegates extend from simple/compound base
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

	@Override
	public void enterFsmBuilder(ScribNode parent, ScribNode child, FsmBuilder conv)
	{
		super.enterFsmBuilder(parent, child, conv);
		if (!conv.isCycle())
		{
			SubprotocolSig subsig = conv.peekStack();  // SubprotocolVisitor has already entered subprotocol
			Set<String> labs = new HashSet<>(conv.builder.getEntry().getLabels());
			labs.add(subsig.toString());
			ProtocolState s = conv.builder.newState(labs);
			conv.builder.setEntry(s);
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
	}
}
