package org.scribble.del.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.ModelNode;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.visit.ReachabilityChecker;
import org.scribble.ast.visit.env.ReachabilityEnv;
import org.scribble.util.ScribbleException;

public class LChoiceDel extends LCompoundInteractionNodeDel
{
	@Override
	public LChoice leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		LChoice cho = (LChoice) visited;
		List<ReachabilityEnv> benvs =
				cho.blocks.stream().map((b) -> (ReachabilityEnv) b.del().env()).collect(Collectors.toList());
		ReachabilityEnv merged = checker.popEnv().mergeForChoice(benvs);
		checker.pushEnv(merged);
		return (LChoice) super.leaveReachabilityCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}

	/*@Override
	public LChoice leaveFsmConversion(ModelNode parent, ModelNode child, FsmConverter conv, ModelNode visited)
	{
		LChoice lc = (LChoice) visited;
		List<ScribbleFsm> fs = lc.blocks.stream().map((block) -> ((FsmBuildingEnv) block.del().env()).getFsm()).collect(Collectors.toList());
		ScribbleFsm f = ScribbleFsm.merge(fs);
		FsmBuildingEnv env = conv.popEnv();
		conv.pushEnv(env.setFsm(f));
		return (LChoice) super.leaveFsmConversion(parent, child, conv, lc);
	}*/
}
