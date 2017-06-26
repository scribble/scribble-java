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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LContinue;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ContinueDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.EGraphBuilder;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.wf.ReachabilityChecker;
import org.scribble.visit.wf.env.ReachabilityEnv;

public class LContinueDel extends ContinueDel implements LSimpleInteractionNodeDel
{
	@Override
	public LContinue leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		LContinue lc = (LContinue) visited;
		RecVarNode recvar = (RecVarNode) ((InlineProtocolEnv) lc.recvar.del().env()).getTranslation();	
		LContinue inlined = inl.job.af.LContinue(lc.getSource(), recvar);
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (LContinue) super.leaveProtocolInlining(parent, child, inl, lc);
	}

	@Override
	public LContinue leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		// "Entering" the continue here in leave, where we can merge the new state into the parent Env
		// Generally: if side effecting Env state to be merged into the parent (not just popped and discarded), leave must be overridden to do so
		LContinue lc = (LContinue) visited;
		ReachabilityEnv env = checker.popEnv().addContinueLabel(lc.recvar.toName());
		setEnv(env);  // Env recording probably not needed for all LocalInteractionNodes, just the compound ones, like for WF-choice checking
		checker.pushEnv(checker.popEnv().mergeContext(env));
		return lc;
	}

	@Override
	public LContinue leaveEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder graph, ScribNode visited) throws ScribbleException
	{
		LContinue lr = (LContinue) visited;
		RecVar rv = lr.recvar.toName();
		if (graph.util.isUnguardedInChoice())
		{
			graph.util.addContinueEdge(graph.util.getEntry(), rv);
		}
		else
		{
			// ** "Overwrites" previous edge built by send/receive(s) leading to this continue
			Iterator<EState> preds = graph.util.getPredecessors().iterator();
			Iterator<EAction> prevs = graph.util.getPreviousActions().iterator();
			EState entry = graph.util.getEntry();

			Set<List<Object>> removed = new HashSet<>();  
					// HACK: for identical edges, i.e. same pred/prev/succ (e.g. rec X { choice at A { A->B:1 } or { A->B:1 } continue X; })  // FIXME: do here, or refactor into GraphBuilder?
					// Because duplicate edges preemptively pruned by ModelState.addEdge, but corresponding predecessors not pruned  // FIXME: make uniform
			while (preds.hasNext())
			{
				EState pred = preds.next();
				EAction prev = prevs.next();
				List<Object> tmp = Arrays.asList(pred, prev, entry);
				if (!removed.contains(tmp))
				{
					removed.add(tmp);
					graph.util.removeEdgeFromPredecessor(pred, prev);  // Assumes pred is a predecessor, and removes pred from current predecessors..
				}
				graph.util.addRecursionEdge(pred, prev, graph.util.getRecursionEntry(rv));  // May be repeated for non-det, but OK  // Combine with removeEdgeFromPredecessor?
			}
		}
		return (LContinue) super.leaveEGraphBuilding(parent, child, graph, lr);
	}
}
