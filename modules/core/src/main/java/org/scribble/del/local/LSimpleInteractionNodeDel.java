package org.scribble.del.local;


//public abstract class LSimpleInteractionNodeDel extends SimpleInteractionNodeDel
public interface LSimpleInteractionNodeDel extends LInteractionNodeDel
{
	/*public LSimpleInteractionNodeDel()
	{

	}*/

	/*@Override
	//public void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	default void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	{
		//pushVisitorEnv(parent, child, checker);
		ScribDelBase.pushVisitorEnv(this, checker);
	}

	@Override
	//public LSimpleInteractionNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	default LSimpleInteractionNode leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		//return (LSimpleInteractionNode) popAndSetVisitorEnv(parent, child, checker, visited);
		return (LSimpleInteractionNode) ScribDelBase.popAndSetVisitorEnv(this, checker, visited);
	}*/
}
