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
package org.scribble.core.model.visit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.core.model.MAction;
import org.scribble.core.model.MState;
import org.scribble.core.type.kind.ProtoKind;

public abstract class EdgeVisitor
<
		L,
		A extends MAction<K>,
		S extends MState<L, A, S, K>,
		K extends ProtoKind
>
{
	// "One-time" traveral (visitor no for reuse)
	private final Map<S, Map<A, Set<S>>> seen = new HashMap<>();
	
	public boolean hasSeen(S s, A a, S succ)
	{
		if (!this.seen.containsKey(s))
		{
			return false;
		}
		Map<A, Set<S>> tmp = this.seen.get(s);
		return tmp.containsKey(a) && tmp.get(a).contains(succ);
	}
	
	protected void setSeen(S s, A a, S succ)
	{
		Map<A, Set<S>> tmp1 = this.seen.get(s);
		if (tmp1 == null)
		{
			tmp1 = new HashMap<>();
			this.seen.put(s, tmp1);
		}
		Set<S> tmp2 = tmp1.get(a);
		if (tmp2 == null)
		{
			tmp2 = new HashSet<>();
			tmp1.put(a, tmp2);
		}
		if (!tmp2.contains(succ))  // Worth?  Or just do add
		{
			tmp2.add(succ);
		}
	}
}
	