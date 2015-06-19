package org.scribble.del.global;

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GInterruptible;
import org.scribble.del.CompoundInteractionNodeDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.WellFormedChoiceChecker;
import org.scribble.visit.env.WellFormedChoiceEnv;

public class GInterruptibleDel extends GCompoundInteractionNodeDel
{
	/*@Override
	//public void leave(Interruptible<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>, ? extends Interrupt> intt, WellFormedChoiceChecker checker)
	public GlobalInterruptible leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		GlobalInterruptible intt = (GlobalInterruptible) visited;
		WellFormedChoiceEnv merged = checker.popEnv().merge((WellFormedChoiceEnv) intt.block.del().getEnv());
		merged.initial.merge(ienv.initialInterrupts);
		checker.pushEnv(merged);
		return intt;
	}*/
}
