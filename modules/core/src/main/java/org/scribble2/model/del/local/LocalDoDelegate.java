package org.scribble2.model.del.local;

import org.scribble2.model.ModelNode;
import org.scribble2.model.context.ModuleContext;
import org.scribble2.model.local.LocalDo;
import org.scribble2.model.visit.ContextBuilder;
import org.scribble2.model.visit.JobContext;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.model.visit.env.ReachabilityEnv;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

public class LocalDoDelegate extends LocalSimpleInteractionNodeDelegate
{
	// Would like to factor out with GlobalDoDelegate, but global/local interaction node delegates extend from simple/compound base
	@Override
	public LocalDo leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException
	{
		JobContext jcontext = builder.getJobContext();
		ModuleContext mcontext = builder.getModuleContext();
		LocalDo ld = (LocalDo) visited;
		for (Role role : ld.roleinstans.getRoles())
		{
			builder.addProtocolDependency(role, ld.getTargetFullProtocolName(builder.getModuleContext()), ld.getTargetRoleParameter(jcontext, mcontext, role));
		}
		return ld;
	}

	@Override
	public LocalDo leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		ReachabilityEnv env = checker.popEnv();
		if (checker.isCycle())
		{
			env = env.leaveRecursiveDo();
		}
		checker.pushEnv(env);
		return popAndSetVisitorEnv(parent, child, checker, (LocalDo) visited);
	}
}