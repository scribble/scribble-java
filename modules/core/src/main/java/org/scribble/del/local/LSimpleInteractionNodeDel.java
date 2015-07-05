package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LSimpleInteractionNode;
import org.scribble.del.SimpleInteractionNodeDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ReachabilityChecker;

public abstract class LSimpleInteractionNodeDel extends SimpleInteractionNodeDel
{
	public LSimpleInteractionNodeDel()
	{

	}

	@Override
	public void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	{
		//pushVisitorEnv(parent, child, checker);
		pushVisitorEnv(this, checker);
	}

	@Override
	public LSimpleInteractionNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		//return (LSimpleInteractionNode) popAndSetVisitorEnv(parent, child, checker, visited);
		return (LSimpleInteractionNode) popAndSetVisitorEnv(this, checker, visited);
	}
}
