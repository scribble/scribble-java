/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.del;

import org.scribble.ast.CompoundInteractionNode;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.UnguardedChoiceDoProjectionChecker;
import org.scribble.visit.context.env.UnguardedChoiceDoEnv;
import org.scribble.visit.env.UnfoldingEnv;
import org.scribble.visit.wf.ExplicitCorrelationChecker;
import org.scribble.visit.wf.WFChoiceChecker;
import org.scribble.visit.wf.env.ExplicitCorrelationEnv;
import org.scribble.visit.wf.env.WFChoiceEnv;

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

	// In the following only leaves are overridden to do additional merging -- enters remain as for super by default
	// Specialised enters will be overridden per node (e.g., GChoiceDel.enterInlinedWFChoiceCheck) -- corresponding leaves will use their super (i.e., the below) to do merging
	
	// Should only do for projections, but OK here (visitor only run on projections)
	@Override
	public ScribNode leaveUnguardedChoiceDoProjectionCheck(ScribNode parent, ScribNode child, UnguardedChoiceDoProjectionChecker checker, ScribNode visited) throws ScribbleException
	{
		// Override super routine (in CompoundInteractionDel, which just does base popAndSet) to do merging of child context into parent context
		UnguardedChoiceDoEnv visited_env = checker.popEnv();  // popAndSet current
		setEnv(visited_env);
		UnguardedChoiceDoEnv parent_env = checker.popEnv();  // pop-merge-push parent
		parent_env = parent_env.mergeContext(visited_env);
		checker.pushEnv(parent_env);
		return (CompoundInteractionNode<?>) visited;
	}

	@Override
	public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		// Override super routine (in CompoundInteractionDel, which just does base popAndSet) to do merging of child context into parent context
		// Need to further override for "compound" nodes with block children, e.g., Choice/RecursionDel, to merge children together and then into current (then call here via super to set and merge current into parent)
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

	@Override
	public ScribNode leaveExplicitCorrelationCheck(ScribNode parent, ScribNode child, ExplicitCorrelationChecker checker, ScribNode visited) throws ScribbleException
	{
		// Override super routine (in CompoundInteractionDel, which just does base popAndSet) to do merging of child context into parent context
		// Need to further override for "multi-compound" nodes, e.g., ChoiceDel
		ExplicitCorrelationEnv visited_env = checker.popEnv();  // popAndSet current
		setEnv(visited_env);
		ExplicitCorrelationEnv parent_env = checker.popEnv();  // pop-merge-push parent
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

	/*@Override
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
		System.out.println("4: " + visited + "\n");* /
		
		return (CompoundInteractionNode<?>) visited;
	}*/
}
