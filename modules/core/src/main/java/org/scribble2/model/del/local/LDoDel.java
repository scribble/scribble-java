package org.scribble2.model.del.local;

import org.scribble2.model.ModelNode;
import org.scribble2.model.context.ModuleContext;
import org.scribble2.model.local.LDo;
import org.scribble2.model.visit.ContextBuilder;
import org.scribble2.model.visit.JobContext;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.model.visit.env.ReachabilityEnv;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

public class LDoDel extends LSimpleInteractionNodeDel
{
	// Would like to factor out with GlobalDoDelegate, but global/local interaction node delegates extend from simple/compound base
	@Override
	public LDo leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException
	{
		JobContext jcontext = builder.getJobContext();
		ModuleContext mcontext = builder.getModuleContext();
		LDo ld = (LDo) visited;
		for (Role role : ld.roleinstans.getRoles())
		{
			//builder.addProtocolDependency(role, ld.getTargetFullProtocolName(builder.getModuleContext()), ld.getTargetRoleParameter(jcontext, mcontext, role));
			
			
			..FIXME: generalise dependencies to support local
			
			
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
}
