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
import org.scribble.core.type.session.Recursion;

public class LRecursion extends Recursion<Local, LSeq> implements LType
{
	protected LRecursion(//org.scribble.ast.Recursion<Local> source, 
			CommonTree source,  // Due to inlining, protocoldecl -> rec
			RecVar recvar, LSeq body)
	{
		super(source, recvar, body);
	}
	
	@Override
	public LRecursion reconstruct(CommonTree source,
			RecVar recvar, LSeq block)
	{
		return new LRecursion(source, recvar, block);
	}

	@Override
	public int hashCode()
	{
		int hash = 2309;
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
		if (!(o instanceof LRecursion))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LRecursion;
	}
}
















/*
	@Override
	public boolean isSingleConts(Set<RecVar> rvs)
	{
		Set<RecVar> tmp = new HashSet<>(rvs);
		tmp.add(this.recvar);
		return this.body.isSingleConts(tmp);
	}

	@Override
	public ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribException
	{
		env = this.body.checkReachability(env);
		if (env.recvars.contains(this.recvar))
		{
			Set<RecVar> tmp = new HashSet<>(env.recvars);
			tmp.remove(this.recvar);
			env = new ReachabilityEnv(env.postcont, tmp);
		}
		return env;
	}
	
	@Override
	public void buildGraph(EGraphBuilderUtil2 b)
	{
		b.addEntryLabel(this.recvar);
		b.pushRecursionEntry(this.recvar, b.getEntry());
		this.body.buildGraph(b);
		b.popRecursionEntry(this.recvar);
	}
	*/
