package org.scribble.del.global;

import org.scribble.del.InterruptibleDel;

public class GInterruptibleDel extends InterruptibleDel implements GCompoundInteractionNodeDel
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
