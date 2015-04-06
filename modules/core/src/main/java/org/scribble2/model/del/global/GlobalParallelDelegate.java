package org.scribble2.model.del.global;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble2.model.ModelNode;
import org.scribble2.model.del.CompoundInteractionNodeDelegate;
import org.scribble2.model.global.GlobalParallel;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.util.ScribbleException;

public class GlobalParallelDelegate extends CompoundGlobalInteractionNodeDelegate
{
	@Override
	//public GlobalParallel leave(Parallel<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> par,
	//public GlobalParallel leave(GlobalParallel par, WellFormedChoiceChecker checker)
	public GlobalParallel leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		GlobalParallel par = (GlobalParallel) visited;
		List<WellFormedChoiceEnv> benvs =
				par.blocks.stream().map((b) -> (WellFormedChoiceEnv) b.del().getEnv()).collect(Collectors.toList());
		checker.pushEnv(checker.popEnv().merge(benvs));
		return par;
	}
}
