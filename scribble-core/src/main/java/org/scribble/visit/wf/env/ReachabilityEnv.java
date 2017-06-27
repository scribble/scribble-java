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
package org.scribble.visit.wf.env;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.env.Env;

public class ReachabilityEnv extends Env<ReachabilityEnv>
{
	private boolean seqable; 
			// For checking bad sequencing of unreachable code: false after a continue; true if choice has an exit (false inherited for all other constructs)
	private final Set<RecVar> contlabs;  // For checking "reachable code" satisfies tail recursion (in the presence of sequencing)
	
	public ReachabilityEnv()
	{
		this(true, Collections.emptySet());
	}
	
	protected ReachabilityEnv(boolean seqable, Set<RecVar> contlabs)
	{
		this.contlabs = new HashSet<RecVar>(contlabs);
		this.seqable = seqable;
	}

	@Override
	public ReachabilityEnv copy()
	{
		return new ReachabilityEnv(this.seqable, this.contlabs);
	}

	@Override
	public ReachabilityEnv enterContext()
	{
		return copy();
	}

  // Should not be used for single block choice 
	@Override
	public ReachabilityEnv mergeContext(ReachabilityEnv child)
	{
		return mergeContexts(Arrays.asList(child));
	}

	// Should not be used for Choice
	@Override
	public ReachabilityEnv mergeContexts(List<ReachabilityEnv> children)
	{
		return merge(false, children);
	}

	public ReachabilityEnv mergeForChoice(List<ReachabilityEnv> children)
	{
		return merge(true, children);
	}

	// Does merge depend on choice/par etc?
	private ReachabilityEnv merge(boolean isChoice, List<ReachabilityEnv> children)
	{
		ReachabilityEnv copy = copy();
		copy.seqable =
				(isChoice)
					? children.stream().filter((e) -> e.seqable).count() > 0
					: children.stream().filter((e) -> !e.seqable).count() == 0;
		children.stream().forEach((e) -> copy.contlabs.addAll(e.contlabs));
		return copy;
	}
	
	// i.e. control flow has the potential to exit from this context
	public boolean isSequenceable()
	{
		return this.seqable && this.contlabs.isEmpty();
	}
	
	public ReachabilityEnv addContinueLabel(RecVar recvar)
	{
		ReachabilityEnv copy = copy();
		copy.seqable = false;
		copy.contlabs.add(recvar);
		return copy;
	}
	
	public ReachabilityEnv removeContinueLabel(RecVar recvar)
	{
		ReachabilityEnv copy = copy();
		copy.contlabs.remove(recvar);
		return copy;
	}
}
