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
package org.scribble.core.type.session.local;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.session.Continue;

public class LContinue extends Continue<Local, LSeq> implements LType
{
	// N.B. source changes from do to continue after inlining
	protected LContinue(CommonTree source, RecVar recvar)
	{
		super(source, recvar);
	}
	
	@Override
	public LContinue reconstruct(CommonTree source,
			RecVar recvar)
	{
		return new LContinue(source, recvar);
	}
 
	@Override
	public int hashCode()
	{
		int hash = 3457;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof LContinue))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LContinue;
	}
	
	
	
	
	
	
	
	
	
	
	
	

	/*@Override
	public LRecursion unfoldAllOnce(STypeUnfolder<Local> u)
	{
		return new LRecursion(getSource(), this.recvar,  // CHECKME: Continue (not Recursion) as the source of the unfolding
				(LSeq) u.getRec(this.recvar));  
	}

	@Override
	public boolean isSingleConts(Set<RecVar> rvs)
	{
		return rvs.contains(this.recvar);
	}

	@Override
	public ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribException
	{
		Set<RecVar> tmp = new HashSet<>(env.recvars);
		tmp.add(this.recvar);
		return new ReachabilityEnv(true, tmp);
	}
	
	@Override
	public void buildGraph(EGraphBuilderUtil2 b)
	{
		// CHECKME: identical edges, i.e. same pred/prev/succ (e.g. rec X { choice at A { A->B:1 } or { A->B:1 } continue X; })  
		// Choice-guarded continue -- choice-unguarded continue detected and handled in LChoice
		EState curr = b.getEntry();
		for (EState pred : b.getAllPredecessors(curr))  // Does getAllSuccessors
		{
			for (EAction a : new LinkedList<>(pred.getAllActions()))
			{
				// Following is because pred.getSuccessor doesn't support non-det edges
				// FIXME: refactor actions/successor Lists in MState to list of edges?
				for (EState succ : pred.getSuccessors(a))
				{
					if (succ.equals(curr))
					{
						try
						{
							b.removeEdge(pred, a, curr);  //b.removeEdgeFromPredecessor(pred, prev);
							//b.addEdge(pred, a, entry);   //b.addRecursionEdge(pred, prev, b.getRecursionEntry(this.recvar));
							b.addRecursionEdge(pred, a, this.recvar);
						}
						catch (ScribException e)  // CHECKME: necessary for removeEdge to have throws?
						{
							throw new RuntimeException(e);
						}
					}
				}
			}
		}
	}
	*/
}

