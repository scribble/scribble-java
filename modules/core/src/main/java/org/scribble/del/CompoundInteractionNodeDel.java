package org.scribble.del;

import org.scribble.ast.CompoundInteractionNode;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.InlinedWFChoiceChecker;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.WFChoiceChecker;
import org.scribble.visit.env.InlinedWFChoiceEnv;
import org.scribble.visit.env.UnfoldingEnv;
import org.scribble.visit.env.WFChoiceEnv;

/* Unlike global/local interaction model nodes that extend the base constructs (choice/recursion/etc) that extend simple/compound,
 * each interaction construct delegate extends base global/local delegates that extend simple/compound
 * e.g. CompoundInteractionNode -> Choice -> Global/LocalChoice
 *      CompoundInteractionNodeDelegate -> Global/LocalCompoundInteractionNodeDelegate -> [Global/Local]ChoiceDelegate
 * works better for each that way: global/local model nodes share the base node visiting pattern, while global/local delegates share Env handling based on simple/compound (i.e. Env merging) for passes according to global or local
 */
public abstract class CompoundInteractionNodeDel extends CompoundInteractionDel implements InteractionNodeDel
{
	public CompoundInteractionNodeDel()
	{

	}

	@Override
	public CompoundInteractionNode<?> leaveWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		// Override super routine (in CompoundInteractionDel, which just does base popAndSet) to do merging of child context into parent context
		WFChoiceEnv visited_env = checker.popEnv();
		WFChoiceEnv parent_env = checker.popEnv();
		setEnv(visited_env);
		parent_env = parent_env.mergeContext(visited_env);
		checker.pushEnv(parent_env);
		return (CompoundInteractionNode<?>) visited;
	}

	@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder) throws ScribbleException
	{
		pushVisitorEnv(parent, child, builder);
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		return popAndSetVisitorEnv(parent, child, builder, visited);
	}

	@Override
	public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		// Override super routine (in CompoundInteractionDel, which just does base popAndSet) to do merging of child context into parent context
		UnfoldingEnv visited_env = unf.popEnv();
		UnfoldingEnv parent_env = unf.popEnv();
		setEnv(visited_env);
		parent_env = parent_env.mergeContext(visited_env);
		unf.pushEnv(parent_env);
		return (CompoundInteractionNode<?>) visited;
	}

	@Override
	public CompoundInteractionNode<?> leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, InlinedWFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		InlinedWFChoiceEnv visited_env = checker.popEnv();
		InlinedWFChoiceEnv parent_env = checker.popEnv();
		setEnv(visited_env);
		parent_env = parent_env.mergeContext(visited_env);
		checker.pushEnv(parent_env);
		return (CompoundInteractionNode<?>) visited;
	}
}
