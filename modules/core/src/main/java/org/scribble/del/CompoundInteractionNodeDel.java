package org.scribble.del;

import org.scribble.ast.CompoundInteractionNode;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.InlinedWFChoiceChecker;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.env.InlinedWFChoiceEnv;
import org.scribble.visit.env.UnfoldingEnv;

public abstract class CompoundInteractionNodeDel extends CompoundInteractionDel implements InteractionNodeDel
{
	public CompoundInteractionNodeDel()
	{

	}

	@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl) throws ScribbleException
	{
		ScribDelBase.pushVisitorEnv(this, inl);
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		return ScribDelBase.popAndSetVisitorEnv(this, inl, visited);
	}

	@Override
	public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		// Override super routine (in CompoundInteractionDel, which just does base popAndSet) to do merging of child context into parent context
		UnfoldingEnv visited_env = unf.popEnv();  // popAndSet current
		setEnv(visited_env);
		UnfoldingEnv parent_env = unf.popEnv();  // pop-merge-push parent
		parent_env = parent_env.mergeContext(visited_env);
		unf.pushEnv(parent_env);
		return (CompoundInteractionNode<?>) visited;
	}

	@Override
	public CompoundInteractionNode<?> leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, InlinedWFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		InlinedWFChoiceEnv visited_env = checker.popEnv();  // popAndSet current
		setEnv(visited_env);
		InlinedWFChoiceEnv parent_env = checker.popEnv();  // pop-merge-push parent
		parent_env = parent_env.mergeContext(visited_env);
		checker.pushEnv(parent_env);
		return (CompoundInteractionNode<?>) visited;
	}
}
