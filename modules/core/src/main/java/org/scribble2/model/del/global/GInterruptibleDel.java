package org.scribble2.model.del.global;

import org.scribble2.model.ModelNode;
import org.scribble2.model.del.CompoundInteractionNodeDel;
import org.scribble2.model.global.GInterruptible;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.util.ScribbleException;

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
