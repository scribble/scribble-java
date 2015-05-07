package org.scribble2.model.del.local;

import org.scribble2.model.ModelNode;
import org.scribble2.model.local.LRecursion;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.model.visit.env.ReachabilityEnv;
import org.scribble2.util.ScribbleException;

public class LRecursionDel extends LCompoundInteractionNodeDel
{
	@Override
	public LRecursion leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		LRecursion lr = (LRecursion) visited;
		ReachabilityEnv env = checker.popEnv().mergeContext((ReachabilityEnv) lr.block.del().env());  //...HERE: env is null
		env = env.removeContinueLabel(lr.recvar.toName());
		//merged.contExitable = this.contExitable;
		checker.pushEnv(env);
		return (LRecursion) super.leaveReachabilityCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env*/
	}
}
