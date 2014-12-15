package org.scribble2.model.del.global;

import org.scribble2.model.ModelNode;
import org.scribble2.model.del.CompoundInteractionNodeDelegate;
import org.scribble2.model.global.GlobalRecursion;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.util.ScribbleException;

public class GlobalRecursionDelegate extends CompoundInteractionNodeDelegate
{
	@Override
	//public void leave(Recursion<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> rec, WellFormedChoiceChecker checker)
	public GlobalRecursion leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		GlobalRecursion rec = (GlobalRecursion) visited;
		//WellFormedChoiceEnv parent = checker.getEnv().getParent();
		WellFormedChoiceEnv merged = checker.popEnv().merge((WellFormedChoiceEnv) rec.block.del().getEnv());
		checker.pushEnv(merged);
		return rec;
	}
}
