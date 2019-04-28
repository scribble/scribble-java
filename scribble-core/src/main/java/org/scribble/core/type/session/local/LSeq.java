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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.Seq;
import org.scribble.core.visit.STypeAgg;
import org.scribble.core.visit.STypeAggNoThrow;
import org.scribble.util.ScribException;

public class LSeq extends Seq<Local, LSeq> implements LType
{
	// GInteractionSeq or GBlock better as source?
	//protected LSeq(CommonTree source, List<? extends SType<Local, LSeq>> elems)
	protected LSeq(CommonTree source, List<LType> elems)
	{
		super(source, elems);
	}

	@Override
	public LSeq reconstruct(CommonTree source,
			List<? extends SType<Local, LSeq>> elems)
	{
		return new LSeq(source,
				castElems(elems.stream()).collect(Collectors.toList()));
	}
	
	protected static Stream<LType> castElems(
			Stream<? extends SType<Local, LSeq>> elems)
	{
		return elems.map(x -> (LType) x);
	}
	
	@Override
	public <T> T visitWith(STypeAgg<Local, LSeq, T> v) throws ScribException
	{
		return v.visitSeq(this);
	}
	
	@Override
	public <T> T visitWithNoThrow(STypeAggNoThrow<Local, LSeq, T> v)
	{
		return v.visitSeq(this);
	}

	@Override
	public List<LType> getElements()
	{
		return castElems(this.elems.stream()).collect(Collectors.toList());
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






















/*
	@Override
	public boolean isSingleConts(Set<RecVar> rvs)
	{
		return this.elems.size() == 1
				&& ((LType) this.elems.get(0)).isSingleConts(rvs);
	}

	@Override
	public ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribException
	{
		LType prev = null; 
		LType next = null;
		for (Iterator<LType> i = getElements().iterator(); i.hasNext(); )
		{
			prev = next;
			next = i.next();
			if (!env.isSeqable())
			{
				throw new ScribException(
						"Illegal sequence: " + (prev == null ? "" : prev + "\n") + next);
			}
			env = next.checkReachability(env);
		}
		return env;
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
*/