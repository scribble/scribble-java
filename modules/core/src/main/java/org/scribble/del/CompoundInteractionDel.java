package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.context.UnguardedChoiceDoProjectionChecker;
import org.scribble.visit.wf.ExplicitCorrelationChecker;
import org.scribble.visit.wf.WFChoiceChecker;

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
		// Overridden in CompoundInteractionNodeDel to do merging of child context into parent context
		return ScribDelBase.popAndSetVisitorEnv(this, checker, visited);
	}

	@Override
	public void enterExplicitCorrelationCheck(ScribNode parent, ScribNode child, ExplicitCorrelationChecker checker) throws ScribbleException
	{
		ScribDelBase.pushVisitorEnv(this, checker);
	}
	
	@Override
	public ScribNode leaveExplicitCorrelationCheck(ScribNode parent, ScribNode child, ExplicitCorrelationChecker checker, ScribNode visited) throws ScribbleException
	{
		// Overridden in CompoundInteractionNodeDel to do merging of child context into parent context
		return ScribDelBase.popAndSetVisitorEnv(this, checker, visited);
	}
}
