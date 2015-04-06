package org.scribble2.model.del.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble2.model.ModelNode;
import org.scribble2.model.local.LocalChoice;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.model.visit.env.ReachabilityEnv;
import org.scribble2.util.ScribbleException;

public class LocalChoiceDelegate extends CompoundLocalInteractionNodeDelegate
{
	@Override
	public LocalChoice leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		/*WellFormedChoiceEnv env = checker.peekEnv().push();
		env = env.clear();
		env = env.enableChoiceSubject(((GlobalChoice) child).subj.toName());
		checker.pushEnv(env);
		return checker;*/
		LocalChoice cho = (LocalChoice) visited;
		List<ReachabilityEnv> benvs =
				cho.blocks.stream().map((b) -> (ReachabilityEnv) b.del().getEnv()).collect(Collectors.toList());
		ReachabilityEnv merged = checker.popEnv().mergeForChoice(benvs);
		checker.pushEnv(merged);
		return (LocalChoice) super.leaveReachabilityCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env
		
		//...HERE: fix ReachabilityEnv merge; do enter/leave reachability check for recursion/continue/parallel/etc; test reachability pass for projected modules
		//... check delegates for local nodes; check reachability visiting for (local) interaction sequence (and delegate)
	}
}
