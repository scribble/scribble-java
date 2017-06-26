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
package org.scribble.del.local;

import org.scribble.ast.Recursion;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.RecursionDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.EGraphBuilder;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;
import org.scribble.visit.context.UnguardedChoiceDoProjectionChecker;
import org.scribble.visit.context.env.UnguardedChoiceDoEnv;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.wf.ReachabilityChecker;
import org.scribble.visit.wf.env.ReachabilityEnv;

public class LRecursionDel extends RecursionDel implements LCompoundInteractionNodeDel
{
	@Override
	public ScribNode leaveUnguardedChoiceDoProjectionCheck(ScribNode parent, ScribNode child, UnguardedChoiceDoProjectionChecker checker, ScribNode visited) throws ScribbleException
	{
		Recursion<?> rec = (Recursion<?>) visited;
		UnguardedChoiceDoEnv merged = checker.popEnv().mergeContext((UnguardedChoiceDoEnv) rec.block.del().env());
		checker.pushEnv(merged);
		return (Recursion<?>) super.leaveUnguardedChoiceDoProjectionCheck(parent, child, checker, rec);
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		LRecursion lr = (LRecursion) visited;
		//RecVarNode recvar = lr.recvar.clone();
		RecVarNode recvar = (RecVarNode) ((InlineProtocolEnv) lr.recvar.del().env()).getTranslation();	
		LProtocolBlock block = (LProtocolBlock) ((InlineProtocolEnv) lr.block.del().env()).getTranslation();	
		LRecursion inlined = inl.job.af.LRecursion(lr.getSource(), recvar, block);
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (LRecursion) super.leaveProtocolInlining(parent, child, inl, lr);
	}

	@Override
	public LRecursion leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		LRecursion lr = (LRecursion) visited;
		ReachabilityEnv env = checker.popEnv().mergeContext((ReachabilityEnv) lr.block.del().env());
		env = env.removeContinueLabel(lr.recvar.toName());
		checker.pushEnv(env);
		return (LRecursion) LCompoundInteractionNodeDel.super.leaveReachabilityCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env*/
	}
	
	@Override
	public void enterEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder graph)
	{
		super.enterEGraphBuilding(parent, child, graph);
		LRecursion lr = (LRecursion) child;
		RecVar rv = lr.recvar.toName();
		// Update existing state, not replace it -- cf. LDoDel
		/*if (graph.builder.isUnguardedInChoice())  // Actually, not needed since unfoldings are enough to make graph building work (and this makes combined unguarded choice-rec and continue protocols work)
		{
			// Using "previous" entry for this rec lab works because unguarded recs already unfolded (including nested recvar shadowing -- if unguarded choice-rec, it will be unfolded and rec entry recorded for guarded unfolding)
			graph.builder.pushRecursionEntry(rv, graph.builder.getRecursionEntry(rv));
		}
		else*/
		{
			graph.util.addEntryLabel(rv);
			graph.util.pushRecursionEntry(rv, graph.util.getEntry());
		}
	}

	@Override
	public LRecursion leaveEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder graph, ScribNode visited) throws ScribbleException
	{
		LRecursion lr = (LRecursion) visited;
		RecVar rv = lr.recvar.toName();
		graph.util.popRecursionEntry(rv);
		return (LRecursion) super.leaveEGraphBuilding(parent, child, graph, lr);
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.pushRec(((LRecursion) child).recvar.toName());
	}
	
	@Override
	public ScribNode leaveProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer, ScribNode visited)
	{
		fixer.popRec(((LRecursion) child).recvar.toName());
		return visited;
	}
}
