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
package org.scribble.core.type.session.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.session.Recursion;

public class GRecursion extends Recursion<Global, GSeq> implements GType
{
	protected GRecursion(CommonTree source,  // Due to inlining, protocoldecl -> rec
			RecVar recvar, GSeq body)
	{
		super(source, recvar, body);
	}
	
	@Override
	public GRecursion reconstruct(CommonTree source, RecVar recvar, GSeq block)
	{
		return new GRecursion(source, recvar, block);
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
		if (!(o instanceof GRecursion))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GRecursion;
	}
}
















/*
	@Override
	public Set<Role> checkRoleEnabling(Set<Role> enabled) throws ScribException
	{
		return this.body.checkRoleEnabling(enabled);
	}

	@Override
	public Map<Role, Role> checkExtChoiceConsistency(Map<Role, Role> enablers)
			throws ScribException
	{
		return this.body.checkExtChoiceConsistency(enablers);
	}

	@Override
	public LType projectInlined(Role self)
	{
		LSeq body = this.body.projectInlined(self);
		return projectAux(body);
	}

	private LType projectAux(LSeq body)
	{
		if (body.isEmpty())  // N.B. projection is doing empty-rec pruning
		{
			return LSkip.SKIP;
		}
		/*RecVar rv = body.isSingleCont();  // CHECKME: should do more "compressing" of "single continue" (subset) choice cases?   or should not do "single continue" checking for choice at all?
		if (rv != null)
		{
			return this.recvar.equals(rv) 
					? LSkip.SKIP 
					: new LContinue(null, rv);  // CHECKME: source
		}* /
		Set<RecVar> rvs = new HashSet<>();
		rvs.add(this.recvar);
		if (body.aggregateNoEx(new SingleContinueChecker(rvs)))
		{
			return LSkip.SKIP;
		}
		return new LRecursion(null, this.recvar, body);
	}

	@Override
	public LType project(ProjEnv v)
	{
		LSeq body = this.body.project(v);
		return projectAux(body);
	}
*/