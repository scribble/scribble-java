package org.scribble2.model.del.local;

import org.scribble2.model.ModelNode;
import org.scribble2.model.local.LocalRecursion;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.model.visit.env.ReachabilityEnv;
import org.scribble2.util.ScribbleException;

public class LocalRecursionDelegate extends CompoundLocalInteractionNodeDelegate
{
	@Override
	public LocalRecursion leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		LocalRecursion lr = (LocalRecursion) visited;
		ReachabilityEnv env = checker.popEnv().merge((ReachabilityEnv) lr.block.del().getEnv());  //...HERE: env is null
		env = env.removeContinueLabel(lr.recvar.toName());
		//merged.contExitable = this.contExitable;
		checker.pushEnv(env);
		return (LocalRecursion) super.leaveReachabilityCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env*/
		/*setEnv(env);
		env = checker.popEnv().merge(env);  // Overrides super method to merge results back into parent context
		checker.pushEnv(env);
		return lr;*/
	}
}
