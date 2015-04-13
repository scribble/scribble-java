package org.scribble2.model.del.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble2.model.ModelNode;
import org.scribble2.model.local.LocalChoice;
import org.scribble2.model.visit.ReachabilityChecker;
import org.scribble2.model.visit.env.ReachabilityEnv;
import org.scribble2.util.ScribbleException;

public class LocalChoiceDelegate extends LocalCompoundInteractionNodeDelegate
{
	@Override
	public LocalChoice leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		LocalChoice cho = (LocalChoice) visited;
		List<ReachabilityEnv> benvs =
				cho.blocks.stream().map((b) -> (ReachabilityEnv) b.del().env()).collect(Collectors.toList());
		ReachabilityEnv merged = checker.popEnv().mergeForChoice(benvs);
		checker.pushEnv(merged);
		return (LocalChoice) super.leaveReachabilityCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
