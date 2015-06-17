package org.scribble.del.global;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.ModelNode;
import org.scribble.ast.global.GParallel;
import org.scribble.ast.visit.WellFormedChoiceChecker;
import org.scribble.ast.visit.env.WellFormedChoiceEnv;
import org.scribble.util.ScribbleException;

public class GParallelDel extends GCompoundInteractionNodeDel
{
	@Override
	//public GlobalParallel leave(Parallel<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> par,
	//public GlobalParallel leave(GlobalParallel par, WellFormedChoiceChecker checker)
	public GParallel leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		GParallel par = (GParallel) visited;
		List<WellFormedChoiceEnv> benvs =
				par.blocks.stream().map((b) -> (WellFormedChoiceEnv) b.del().env()).collect(Collectors.toList());
		checker.pushEnv(checker.popEnv().mergeContexts(benvs));
		return par;
	}
}
