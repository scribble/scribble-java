package org.scribble.ast.del.local;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ast.ModelNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.local.LDo;
import org.scribble.ast.visit.ContextBuilder;
import org.scribble.ast.visit.FsmConstructor;
import org.scribble.ast.visit.JobContext;
import org.scribble.ast.visit.ReachabilityChecker;
import org.scribble.ast.visit.env.ReachabilityEnv;
import org.scribble.fsm.ProtocolState;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribbleException;

public class LDoDel extends LSimpleInteractionNodeDel
{
	// Would like to factor out with GlobalDoDelegate, but global/local interaction node delegates extend from simple/compound base
	@Override
	public LDo leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException
	{
		JobContext jcontext = builder.getJobContext();
		ModuleContext mcontext = builder.getModuleContext();
		LDo ld = (LDo) visited;
		for (Role role : ld.roles.getRoles())
		{
			builder.addProtocolDependency(role, ld.getTargetFullProtocolName(builder.getModuleContext()), ld.getTargetRoleParameter(jcontext, mcontext, role));
		}
		return ld;
	}

	@Override
	public LDo leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		ReachabilityEnv env = checker.popEnv();
		if (checker.isCycle())
		{
			env = env.leaveRecursiveDo();
		}
		/*checker.pushEnv(env);
		//return popAndSetVisitorEnv(parent, child, checker, (LocalDo) visited);  // no: modified exitable env state, need to merge back to parent (interaction seq) -- following LocalContinueDelegate
		setEnv(env);
		env = checker.popEnv().mergeContext(env);
		checker.pushEnv(env);
		return (LocalDo) visited;*/
		setEnv(env);
		checker.pushEnv(checker.popEnv().mergeContext(env));
		return (LDo) visited;
	}

	@Override
	public void enterFsmConstruction(ModelNode parent, ModelNode child, FsmConstructor conv)
	{
		super.enterFsmConstruction(parent, child, conv);
		if (!conv.isCycle())
		{
			SubprotocolSig subsig = conv.peekStack();  // SubprotocolVisitor has already entered subprotocol
			//SubprotocolSig noscope = new SubprotocolSig(subsig.fmn, subsig.roles, subsig.args);  // FIXME: factor better
			Set<String> labs = new HashSet<>(conv.builder.getEntry().getLabels());
			labs.add(subsig.toString());
			ProtocolState s = conv.builder.newState(labs);
			conv.builder.setEntry(s);
			conv.builder.setSubprotocolEntry(subsig);
		}
	}

	public LDo visitForFsmConversion(FsmConstructor conv, LDo child) //throws ScribbleException
	{
		//if (isEmptyScope())
		{
			/*if (conv.isCycle())
			{*/
				//ScopedSubprotocolSig subsig = builder.getSubprotocolSig(this);
				SubprotocolSig subsig = conv.peekStack();
				//SubprotocolSig noscope = new SubprotocolSig(subsig.fmn, subsig.roles, subsig.args);  // FIXME: factor better
				conv.builder.setEntry(conv.builder.getSubprotocolEntry(subsig));  // Like continue
			/*
			else
			{
				super.visitForGraphBuilding(conv);
			}*/
			return child;
		}
		/*else
		{
			throw new RuntimeException("TODO: ");  // Need protocol ref state
		}*/
	}
	
	@Override
	public LDo leaveFsmConstruction(ModelNode parent, ModelNode child, FsmConstructor conv, ModelNode visited)
	{
		SubprotocolSig subsig = conv.peekStack();
		//SubprotocolSig noscope = new SubprotocolSig(subsig.fmn, subsig.roles, subsig.args);  // FIXME: factor better
		conv.builder.removeSubprotocolEntry(subsig);
		return (LDo) super.leaveFsmConstruction(parent, child, conv, visited);
	}

}
