package org.scribble.del.global;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GParallel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.WellFormedChoiceChecker;
import org.scribble.visit.env.WellFormedChoiceEnv;

public class GParallelDel extends GCompoundInteractionNodeDel
{
	@Override
	//public GlobalParallel leave(Parallel<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> par,
	//public GlobalParallel leave(GlobalParallel par, WellFormedChoiceChecker checker)
	public GParallel leaveWFChoiceCheck(ScribNode parent, ScribNode child, WellFormedChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		GParallel par = (GParallel) visited;
		List<WellFormedChoiceEnv> benvs =
				par.blocks.stream().map((b) -> (WellFormedChoiceEnv) b.del().env()).collect(Collectors.toList());
		checker.pushEnv(checker.popEnv().mergeContexts(benvs));
		return par;
	}
}
