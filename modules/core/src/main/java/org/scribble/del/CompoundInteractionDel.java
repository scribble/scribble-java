package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.UnguardedChoiceDoProjectionChecker;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.WFChoiceChecker;

// For CompoundInteractionNode and ProtocolBlock
public abstract class CompoundInteractionDel extends ScribDelBase
{
	public CompoundInteractionDel()
	{

	}

	// Should only do for projections, but OK here (visitor only run on projections)
	@Override
	public void enterUnguardedChoiceDoProjectionCheck(ScribNode parent, ScribNode child, UnguardedChoiceDoProjectionChecker checker) throws ScribbleException
	{
		ScribDelBase.pushVisitorEnv(this, checker);
	}

	// Should only do for projections, but OK here (visitor only run on projections)
	@Override
	public ScribNode leaveUnguardedChoiceDoProjectionCheck(ScribNode parent, ScribNode child, UnguardedChoiceDoProjectionChecker checker, ScribNode visited) throws ScribbleException
	{
		// Overridden in CompoundInteractionNodeDel to do merging of child context into parent context
		return ScribDelBase.popAndSetVisitorEnv(this, checker, visited);
	}

	@Override
	public void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException
	{
		ScribDelBase.pushVisitorEnv(this, unf);
	}

	@Override
	public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		// Overridden in CompoundInteractionNodeDel to do merging of child context into parent context
		return ScribDelBase.popAndSetVisitorEnv(this, unf, visited);
	}

	@Override
	public void enterInlinedWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker) throws ScribbleException
	{
		ScribDelBase.pushVisitorEnv(this, checker);
	}
	
	@Override
	public ScribNode leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		return ScribDelBase.popAndSetVisitorEnv(this, checker, visited);
	}

	/*@Override
	public void enterWFChoicePathCheck(ScribNode parent, ScribNode child, WFChoicePathChecker coll) throws ScribbleException
	//public void enterPathCollection(ScribNode parent, ScribNode child, PathCollectionVisitor coll) throws ScribbleException
	//public void enterPathCollection(ScribNode parent, ScribNode child, PathCollectionVisitor<? extends PathEnv> coll) throws ScribbleException
	{
		ScribDelBase.pushVisitorEnv(this, coll);
	}

	@Override
	public ScribNode leaveWFChoicePathCheck(ScribNode parent, ScribNode child, WFChoicePathChecker coll, ScribNode visited) throws ScribbleException
	//public ScribNode leavePathCollection(ScribNode parent, ScribNode child, PathCollectionVisitor<? extends PathEnv> coll, ScribNode visited) throws ScribbleException
	//public ScribNode leavePathCollection(ScribNode parent, ScribNode child, PathCollectionVisitor coll, ScribNode visited) throws ScribbleException
	{
		return ScribDelBase.popAndSetVisitorEnv(this, coll, visited);
	}*/
}
