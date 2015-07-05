package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.InlinedWFChoiceChecker;
import org.scribble.visit.WFChoiceChecker;

// For CompoundInteractionNode and ProtocolBlock
public abstract class CompoundInteractionDel extends ScribDelBase
{
	public CompoundInteractionDel()
	{

	}

	@Override
	public void enterWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker) throws ScribbleException
	{
		//pushVisitorEnv(parent, child, checker);
		pushVisitorEnv(this, checker);
	}
	
	@Override
	public ScribNode leaveWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		//return popAndSetVisitorEnv(parent, child, checker, visited);
		return popAndSetVisitorEnv(this, checker, visited);
	}

	@Override
	public void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException
	{
		//pushVisitorEnv(parent, child, unf);
		pushVisitorEnv(this, unf);
	}

	@Override
	public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		//return popAndSetVisitorEnv(parent, child, unf, visited);
		return popAndSetVisitorEnv(this, unf, visited);
	}

	@Override
	public void enterInlinedWFChoiceCheck(ScribNode parent, ScribNode child, InlinedWFChoiceChecker checker) throws ScribbleException
	{
		//pushVisitorEnv(parent, child, checker);
		pushVisitorEnv(this, checker);
	}
	
	@Override
	public ScribNode leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, InlinedWFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		//return popAndSetVisitorEnv(parent, child, checker, visited);
		return popAndSetVisitorEnv(this, checker, visited);
	}
}
