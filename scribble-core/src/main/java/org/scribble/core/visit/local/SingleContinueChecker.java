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
package org.scribble.core.visit.local;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.ProtoName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.session.Continue;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.visit.STypeAggNoThrow;

// Return true iff this LType is "equivalent" to a single "continue X", where X is in rvs
// Would be an "InlinedAgg"
public class SingleContinueChecker extends STypeAggNoThrow<Local, LSeq, Boolean>
{
	private Set<RecVar> rvs;

	public SingleContinueChecker(Set<RecVar> rvs)
	{
		this.rvs = Collections.unmodifiableSet(rvs);
	}

	@Override
	public Boolean unit(SType<Local, LSeq> n)
	{
		return false;
	}

	@Override
	public Boolean agg(SType<Local, LSeq> n, Stream<Boolean> bs)
	{
		return bs.allMatch(x -> x);
	}
	
	@Override
	public Boolean visitContinue(Continue<Local, LSeq> n)
	{
		return rvs.contains(n.recvar);  // Single continue for an "outer" recvar caught later on
	}

	@Override
	public <N extends ProtoName<Local>> Boolean visitDo(Do<Local, LSeq, N> n)
	{
		//return (Boolean) InlinedVisitor1.super.visitDo(n);
		//return (Boolean) super.visitDo(n);  // CHECKME: resolves to the extends super, even with a default i/f implementation ?
		throw new RuntimeException(this.getClass() + " unsupported for Do: " + n);
	}

	@Override
	public Boolean visitRecursion(Recursion<Local, LSeq> n)
	{
		Set<RecVar> tmp = new HashSet<>(this.rvs);
		tmp.add(n.recvar);
		//return n.body.visitWithNoThrow(new SingleContinueChecker(tmp));
		return new SingleContinueChecker(tmp).visitSeq(n.body);
	}

	@Override
	public Boolean visitSeq(LSeq n)
	{
		return n.elems.size() == 1
				&& (n.elems.get(0)).visitWithNoThrow(this);  // No: doesn't support "empty" choices ahead of single continue ? 
		//return n.elems.stream().allMatch(x -> x.visitWithNoThrow(this));
				// elems except the last must be "empty" (e.g., empty choice), not actually "single continues",  but bad sequenced "single continues" are caught by reachability
				// i.e., "single-continue" check on every elem a bit overkill here, but OK
	}
}
