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
package org.scribble.type.session.local;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.job.ScribbleException;
import org.scribble.model.endpoint.EGraphBuilderUtil2;
import org.scribble.model.endpoint.EState;
import org.scribble.type.kind.Local;
import org.scribble.type.name.RecVar;
import org.scribble.type.session.SType;
import org.scribble.type.session.Seq;
import org.scribble.visit.local.ReachabilityEnv;

public class LSeq extends Seq<Local, LSeq> implements LType
{
	// GInteractionSeq or GBlock better as source?
	public LSeq(CommonTree source, List<? extends SType<Local, LSeq>> elems)
	{
		super(source, elems);
	}

	@Override
	public LSeq reconstruct(CommonTree source,
			List<? extends SType<Local, LSeq>> elems)
	{
		return new LSeq(source, elems);
	}

	@Override
	public boolean isSingleConts(Set<RecVar> rvs)
	{
		return this.elems.size() == 1
				&& ((LType) this.elems.get(0)).isSingleConts(rvs);
	}

	@Override
	public void buildGraph(EGraphBuilderUtil2 b)
	{
		EState entry = b.getEntry();
		EState exit = b.getExit();
		List<LType> elems = getElements();
		if (elems.isEmpty())
		{
			throw new RuntimeException("Shouldn't get here: " + this);
		}
		for (Iterator<LType> i = getElements().iterator(); i.hasNext(); )
		{
			LType next = i.next();
			if (!i.hasNext())
			{
				b.setExit(exit);
				next.buildGraph(b);
			}
			else
			{
				EState tmp = b.newState(Collections.emptySet());
				b.setExit(tmp);
				next.buildGraph(b);
				b.setEntry(b.getExit());
						// CHECKME: exit may not be tmp, entry/exit can be modified, e.g. continue
			}
		}
		b.setEntry(entry);
	}

	@Override
	public ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribbleException
	{
		LType prev = null; 
		LType next = null;
		for (Iterator<LType> i = getElements().iterator(); i.hasNext(); )
		{
			prev = next;
			next = i.next();
			if (!env.isSeqable())
			{
				throw new ScribbleException(
						"Illegal sequence: " + (prev == null ? "" : prev + "\n") + next);
			}
			env = next.checkReachability(env);
		}
		return env;
	}

	@Override
	public List<LType> getElements()
	{
		return this.elems.stream().map(x -> (LType) x).collect(Collectors.toList());
	}
	
	@Override
	public int hashCode()
	{
		int hash = 29;
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
		if (!(o instanceof LSeq))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LSeq;
	}
}
