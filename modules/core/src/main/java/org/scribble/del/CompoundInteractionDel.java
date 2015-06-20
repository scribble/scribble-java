package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.WellFormedChoiceChecker;

// For CompoundInteractionNode and ProtocolBlock
public abstract class CompoundInteractionDel extends ScribDelBase
{
	public CompoundInteractionDel()
	{

	}

	@Override
	public void enterWFChoiceCheck(ScribNode parent, ScribNode child, WellFormedChoiceChecker checker) throws ScribbleException
	{
		pushVisitorEnv(parent, child, checker);
	}
	
	@Override
	public ScribNode leaveWFChoiceCheck(ScribNode parent, ScribNode child, WellFormedChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		return popAndSetVisitorEnv(parent, child, checker, visited);
	}
}
