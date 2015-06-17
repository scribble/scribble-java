package org.scribble.del.global;

import org.scribble.ast.ModelNode;
import org.scribble.ast.global.GInterruptible;
import org.scribble.ast.visit.WellFormedChoiceChecker;
import org.scribble.ast.visit.env.WellFormedChoiceEnv;
import org.scribble.del.CompoundInteractionNodeDel;
import org.scribble.util.ScribbleException;

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
