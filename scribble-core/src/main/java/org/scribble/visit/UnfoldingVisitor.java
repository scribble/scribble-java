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
package org.scribble.visit;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.Continue;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.Recursion;
import org.scribble.ast.ScribNode;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.type.name.RecVar;
import org.scribble.visit.env.Env;

// FIXME: unfolding/unrolling algorithm would be easier if we would use an "affine rec var" normal form: a single rec can have multiple labels, each label used at most once in a continue

// "Lazily unfolds" each recursion once (by reentering the original rec ast) on reaching a continue
// FIXME: would be better to only unfold "as needed" (unguarded choice-recs)
// N.B. so subclass should manually keep track of when to cut off visiting, as visiting the "unfolding" will eventually reach the same continue (e.g. an unguarded choice-continue) -- currently using pointer equality in e.g. InlinedWFChoice to cut off traversal on reaching the "same" choice again
// Cf. InlinedProtocolUnfolder, statically unfolds unguarded recursions and continues "directly under" choices
public abstract class UnfoldingVisitor<E extends Env<?>> extends InlinedProtocolVisitor<E>
{
	private Map<RecVar, Deque<ProtocolBlock<?>>> recs = new HashMap<>();  
			// Stack needed to handle bad reachability cases (e.g. ... continue X; continue Y; -- if rec Y inside unfolding of X, need to push again before popping so Y still in scope for continue) -- since reachability isn't checked until after projection
			// Also FIXME: recvar shadowing: though this stack should be enough
	private Set<RecVar> unfolded = new HashSet<>();
	
	public UnfoldingVisitor(Job job)
	{
		super(job);
	}

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		enter(parent, child);
		ScribNode visited = visitForUnfolding(parent, child);
		return leave(parent, child, visited);
	}

	protected ScribNode visitForUnfolding(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof Continue)
		{
			Continue<?> cont = (Continue<?>) child;
			RecVar rv = cont.recvar.toName();
			if (!this.unfolded.contains(rv))
			{
				this.unfolded.add(rv);
				// Visiting the children of the seq of the block so as to visit *under the existing env contexts* (i.e. the top Visitor Env)
				// N.B. not visiting a clone because subclasses currently using pointer equality to cut off traversal inside the unfolding (e.g. InlinedWFChoiceChecker)
				// Also visitChildren, not accept (so not doing enter/exit for the seq)
				// Also not returning the seq, just the original continue (cf. do visiting)
				//this.recs.get(rv).peek().seq.clone().visitChildren(this);  // No: e.g. InlinedWFChoiceChecker uses pointer equality to check if Choice already visited
				this.recs.get(rv).peek().seq.visitChildren(this);  // FIXME: ok to visit the same AST? any problems with dels/envs? -- maybe do proper equals/hashCode for AST classes
				this.unfolded.remove(rv);
				return cont;
			}
		}
		return super.visitInlinedProtocol(parent, child);  // Not super.visit because that does enter/exit
	}
	
	@Override
	protected final void inlinedEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.inlinedEnter(parent, child);
		if (child instanceof Recursion)
		{
			Recursion<?> rec = (Recursion<?>) child;
			RecVar rv = rec.recvar.toName();
			if (!this.recs.containsKey(rv))
			{
				this.recs.put(rv, new LinkedList<>());
			}
			Deque<ProtocolBlock<?>> blocks = this.recs.get(rv);
			blocks.push(rec.block);
		}
		unfoldingEnter(parent, child);
	}
	
	@Override
	protected final ScribNode inlinedLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		ScribNode n = unfoldingLeave(parent, child, visited);
		if (child instanceof Recursion)
		{
			Recursion<?> rec = (Recursion<?>) child;
			RecVar rv = rec.recvar.toName();
			Deque<ProtocolBlock<?>> blocks = this.recs.get(rv);
			blocks.pop();
			/*if (blocks.isEmpty())  // Unnecessary? But tidier?
			{
				this.recs.remove(rv);
			}*/
		}
		return super.inlinedLeave(parent, child, n);
	}

	protected void unfoldingEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		
	}

	protected ScribNode unfoldingLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
}
