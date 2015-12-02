package org.scribble.del;

import org.scribble.ast.CompoundInteractionNode;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.WFChoiceChecker;
import org.scribble.visit.WFChoicePathChecker;
import org.scribble.visit.env.WFChoicePathEnv;
import org.scribble.visit.env.UnfoldingEnv;
import org.scribble.visit.env.WFChoiceEnv;

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
	public CompoundInteractionNode<?> leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		WFChoiceEnv visited_env = checker.popEnv();  // popAndSet current
		setEnv(visited_env);
		WFChoiceEnv parent_env = checker.popEnv();  // pop-merge-push parent
		parent_env = parent_env.mergeContext(visited_env);
		checker.pushEnv(parent_env);
		return (CompoundInteractionNode<?>) visited;
	}

	/*@Override
	public void enterWFChoicePathCheck(ScribNode parent, ScribNode child, WFChoicePathChecker coll) throws ScribbleException
	{
		WFChoicePathEnv env = coll.peekEnv().enterContext();
		env = env.clear();
		coll.pushEnv(env);
	}*/

	@Override
	public CompoundInteractionNode<?> leaveWFChoicePathCheck(ScribNode parent, ScribNode child, WFChoicePathChecker coll, ScribNode visited) throws ScribbleException
	//public CompoundInteractionNode<?> leavePathCollection(ScribNode parent, ScribNode child, PathCollectionVisitor coll, ScribNode visited) throws ScribbleException
	{
		WFChoicePathEnv visited_env = coll.popEnv();  // popAndSet current
		setEnv(visited_env);
		WFChoicePathEnv parent_env = coll.popEnv();  // pop-merge-push parent
		parent_env = parent_env.mergeContext(visited_env);
		coll.pushEnv(parent_env);
		
		/*System.out.println("3: " + parent_env.getPaths().size());
		System.out.println("4: " + parent_env.getPaths() + "");
		System.out.println("4: " + visited + "\n");*/
		
		return (CompoundInteractionNode<?>) visited;
	}
}
