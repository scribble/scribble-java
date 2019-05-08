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

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Choice;

public class LChoice extends Choice<Local, LSeq> implements LType
{
	protected LChoice(CommonTree source, Role subj,
			List<LSeq> blocks)
	{
		super(source, subj, blocks);
	}
	
	@Override
	public LChoice reconstruct(CommonTree source, Role subj,
			List<LSeq> blocks)
	{
		return new LChoice(source, subj, blocks);
	}

	/*@Override
	public List<LSeq> getBlocks()
	{
		return this.blocks;
	}*/

	@Override
	public int hashCode()
	{
		int hash = 3067;
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
		if (!(o instanceof LChoice))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LChoice;
	}
}

















/*

	@Override
	public boolean isSingleConts(Set<RecVar> rvs)
	{
		return this.blocks.stream().allMatch(x -> x.isSingleConts(rvs));
	}

	@Override
	public ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribException
	{
		List<ReachabilityEnv> blocks = new LinkedList<>();
		for (LSeq block : this.blocks)
		{
			blocks.add(block.checkReachability(env));
		}
		boolean postcont = blocks.stream().allMatch(x -> x.postcont);  // i.e., no exits
		Set<RecVar> recvars = blocks.stream().flatMap(x -> x.recvars.stream())
				.collect(Collectors.toSet());
		return new ReachabilityEnv(postcont, recvars);
	}
	
	@Override
	public void buildGraph(EGraphBuilderUtil2 b)
	{
		b.enterChoice();
		for (LSeq block : this.blocks)
		{
			List<LType> elems = block.getElements();
			LType first = elems.get(0);
			if (first instanceof LRecursion)  // CHECKME: do this here?  refactor into builderutil?
			{
				//EGraphBuilderUtil2 b1 = new EGraphBuilderUtil2(b);  
						// "Inherits" b.recvars, for continue edge building (to "outer" recs)
				//EGraph nested;
				EState entry = b.getEntry();
				EState exit = b.getExit();

				EState nestedEntry = b.newState(Collections.emptySet());
				b.setEntry(nestedEntry);
				if (elems.size() == 1)
				{
					/*b1.setExit(b.getExit());
					first.buildGraph(b1);
					nested = b1.finalise();* /
					first.buildGraph(b);
				}	
				else
				{
					/*first.buildGraph(b1);
					nested = b1.finalise();* /
					// Reuse existing b, to directly add continue-edges back to the "outer" graph
					EState nestedExit = b.newState(Collections.emptySet());
					b.setExit(nestedExit);
					first.buildGraph(b);

					b.setEntry(nestedExit);  // Must be non null
					b.setExit(exit);
					LSeq tail = new LSeq(null, elems.subList(1, elems.size()));
					tail.buildGraph(b);
				}
				//EState init = nested.init;
				EState init = nestedEntry;
				////for (EAction a : first.getEnabling())
				for (EAction a : (Iterable<EAction>) 
						init.getAllActions().stream().distinct()::iterator)
						// Enabling actions
				{
					for (EState s : init.getSuccessors(a))
					{
						b.addEdge(entry, a, s);
					}
				}
				
				b.setEntry(entry);
				b.setExit(exit);
			}
			else if (first instanceof LContinue)
			{
				// Cannot treat choice-unguarded-continue in "a single pass" because may not have built all recursion enacting edges yet 
				// (Single-pass building would be sensitive to order of choice block visiting)
				LContinue cont = (LContinue) first;  // First and only element
				b.addContinueEdge(b.getEntry(), cont.recvar); 
			}
			else
			{
				b.pushChoiceBlock();  // CHECKME: still needed?  LContinue doesn't check isUnguardedInChoice any more
				block.buildGraph(b);
				b.popChoiceBlock();
			}
		}
		b.leaveChoice();
	}
	*/
