package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LSimpleInteractionNode;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ReachabilityChecker;

//public abstract class LSimpleInteractionNodeDel extends SimpleInteractionNodeDel
public interface LSimpleInteractionNodeDel extends LInteractionNodeDel
{
	/*public LSimpleInteractionNodeDel()
	{

	}*/

	//@Override
	//public void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	default void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	{
		//pushVisitorEnv(parent, child, checker);
		ScribDelBase.pushVisitorEnv(this, checker);
	}

	//@Override
	//public LSimpleInteractionNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	default LSimpleInteractionNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		//return (LSimpleInteractionNode) popAndSetVisitorEnv(parent, child, checker, visited);
		return (LSimpleInteractionNode) ScribDelBase.popAndSetVisitorEnv(this, checker, visited);
	}
}
