package org.scribble.del.global;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GParallel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.WFChoiceChecker;
import org.scribble.visit.env.WFChoiceEnv;

public class GParallelDel extends GCompoundInteractionNodeDel
{
	@Override
	public GParallel leaveWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		GParallel par = (GParallel) visited;
		List<WFChoiceEnv> benvs =
				par.blocks.stream().map((b) -> (WFChoiceEnv) b.del().env()).collect(Collectors.toList());
		checker.pushEnv(checker.popEnv().mergeContexts(benvs));
		return par;
	}
}
